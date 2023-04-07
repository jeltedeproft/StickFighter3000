package jelte.mygame.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManager.AudioCommand;
import jelte.mygame.graphical.audio.MusicManager.AudioEnum;
import jelte.mygame.logic.character.Character;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;

public class GraphicalManagerImpl implements GraphicalManager {

	private static final String TAG = GraphicalManagerImpl.class.getSimpleName();

	private MessageListener messageListener;
	private SpriteBatch batch;
	private OrthogonalTiledMapRenderer mapRenderer;
	private ExtendViewport gameViewport;
	private ScreenViewport uiViewport;
	private Stage stage;
	private Stage uiStage;
	private Skin skin;
	private MapManager mapManager;
	private CameraManager cameraManager;
	private AnimationManager animationManager;
	private Character player;

	private Table root = new Table();
	private Table topBar = new Table();
	private Table middleBar = new Table();
	private Table bottomBar = new Table();

	public GraphicalManagerImpl(MessageListener messageListener) {
		this.messageListener = messageListener;

		AssetManagerUtility.loadTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		AssetManagerUtility.loadSkin(Constants.SKIN_FILE_PATH);

		batch = new SpriteBatch();
		mapManager = new MapManager(batch);
		animationManager = new AnimationManager();

		skin = AssetManagerUtility.getSkin(Constants.SKIN_FILE_PATH);
		gameViewport = new ExtendViewport(Constants.VISIBLE_WIDTH, Constants.VISIBLE_HEIGHT);
		cameraManager = new CameraManager(gameViewport.getCamera());
		stage = new Stage(gameViewport, batch);

		uiViewport = new ScreenViewport();
		uiViewport.getCamera().position.set(uiViewport.getWorldWidth() / 2, uiViewport.getWorldHeight() / 2, 0);
		uiViewport.getCamera().viewportWidth = uiViewport.getWorldWidth();
		uiViewport.getCamera().viewportHeight = uiViewport.getWorldHeight();
		uiViewport.getCamera().update();

		uiStage = new Stage(uiViewport, batch);

		messageListener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_BLOCKING_OBJECTS, mapManager.getBlockingRectangles()));
		messageListener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_MAP_DIMENSIONS, new Vector2(mapManager.getCurrentMapWidth(), mapManager.getCurrentMapHeight())));
		messageListener.receiveMessage(new Message(RECIPIENT.INPUT, ACTION.SEND_STAGE, uiStage));

		MusicManager.getInstance().sendCommand(AudioCommand.MUSIC_PLAY_LOOP, AudioEnum.MAIN_THEME);

		createHud();
	}

	protected void createHud() {
		Table table = new Table();
		table.setFillParent(true); // Makes the table fill the entire screen
		table.setPosition(0, 0);

		ProgressBar hp = new ProgressBar(0, 10, 1, false, skin, "hp");
		table.add(hp).expand().left().top(); // Positions the button in the center of the table

		uiStage.addActor(table); // Adds the table to the stage

	}

	@Override
	public void update(float delta) {
		Gdx.gl.glClearColor(.15f, .15f, .15f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameViewport.apply();
		cameraManager.update(mapManager.getCurrentMapWidth(), mapManager.getCurrentMapHeight(), gameViewport.getWorldWidth(), gameViewport.getWorldHeight());

		batch.setProjectionMatrix(cameraManager.getCamera().combined);
		mapManager.renderCurrentMap(cameraManager.getCamera());

		batch.begin();
		renderBodies();
		batch.end();

		renderUI();

	}

	private void renderBodies() {
		if (player != null) {
			Sprite sprite = animationManager.getSprite(player);
			sprite.setPosition(player.getPositionVector().x, player.getPositionVector().y);
			sprite.draw(batch);
		}
	}

	private void renderUI() {
		uiStage.getViewport().apply();
		uiStage.act();
		uiStage.draw();
	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case CAMERA_LEFT:
			cameraManager.moveCamera(-Constants.CAMERA_MOVE_SPEED, 0);
			break;
		case CAMERA_RIGHT:
			cameraManager.moveCamera(Constants.CAMERA_MOVE_SPEED, 0);
			break;
		case CAMERA_ZOOM:
			cameraManager.zoomCamera((float) message.getValue());
			break;
		case RENDER_PLAYER:
			player = (Character) message.getValue();
			break;
		case UPDATE_CAMERA_POS:
			cameraManager.updateCameraPos((Vector2) message.getValue());
			break;
		default:
			break;

		}
	}

	@Override
	public void resize(int width, int height) {
		gameViewport.update(width, height);
		uiViewport.update(width, height);
		uiViewport.getCamera().position.set(uiViewport.getWorldWidth() / 2, uiViewport.getWorldHeight() / 2, 0);
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		mapManager.dispose();
	}

}