package jelte.mygame.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

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
	private FrameBuffer framebuffer;
	private int frameBufferWidth;
	private int frameBufferHeight;
	protected OrthogonalTiledMapRenderer mapRenderer;
	protected ExtendViewport gameViewPort;
	protected Stage stage;
	protected Stage uiStage;
	protected Skin skin;
	private MapManager mapManager;
	private CameraManager cameraManager;
	private AnimationManager animationManager;
	private Character player;

	// UI
	Table root = new Table();
	Table topBar = new Table();
	Table middleBar = new Table();
	Table bottomBar = new Table();

	public GraphicalManagerImpl(MessageListener messageListener) {
		this.messageListener = messageListener;
		AssetManagerUtility.loadTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		AssetManagerUtility.loadSkin(Constants.SKIN_FILE_PATH);
		batch = new SpriteBatch();

		// calculate the nearest power of 2 size for the framebuffer
		// playerDoubleWidth = MathUtils.nextPowerOfTwo(Constants.PLAYER_WIDTH);
		// playerDoubleHeight = MathUtils.nextPowerOfTwo(Constants.PLAYER_HEIGHT);
		frameBufferWidth = MathUtils.nextPowerOfTwo(Gdx.app.getGraphics().getWidth());
		frameBufferHeight = MathUtils.nextPowerOfTwo(Gdx.app.getGraphics().getHeight());

		// create the framebuffer once at startup
		framebuffer = new FrameBuffer(Pixmap.Format.RGBA8888, frameBufferWidth, frameBufferHeight, false);

		mapManager = new MapManager(batch);
		messageListener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_BLOCKING_OBJECTS, mapManager.getBlockingRectangles()));
		animationManager = new AnimationManager();
		messageListener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_MAP_DIMENSIONS, new Vector2(mapManager.getCurrentMapWidth(), mapManager.getCurrentMapHeight())));
		skin = AssetManagerUtility.getSkin(Constants.SKIN_FILE_PATH);
		gameViewPort = new ExtendViewport(framebuffer.getWidth(), framebuffer.getHeight());
		cameraManager = new CameraManager(gameViewPort.getCamera());
		stage = new Stage(gameViewPort, batch);
		uiStage = new Stage(new ExtendViewport(Constants.UI_WIDTH, Constants.UI_HEIGHT), batch);
		messageListener.receiveMessage(new Message(RECIPIENT.INPUT, ACTION.SEND_STAGE, uiStage));
		MusicManager.getInstance().sendCommand(AudioCommand.MUSIC_PLAY_LOOP, AudioEnum.MAIN_THEME);
		createHud();
	}

	protected void createHud() {
		root.setFillParent(true);
		uiStage.addActor(root);

		topBar.left();
		topBar.setTouchable(Touchable.disabled);

		ProgressBar hp = new ProgressBar(0, 10, 1, false, skin, "hp");
		topBar.add(hp).padLeft(20);

		root.add(topBar).size(Constants.HUD_BARS_WIDTH, Constants.HUD_TOP_BAR_HEIGHT).top().left().expand();
		root.row();
		root.add(middleBar).size(Constants.HUD_BARS_WIDTH, Constants.HUD_MIDDLE_BAR_HEIGHT).expand().left();
		root.row();
		root.add(bottomBar).size(Constants.HUD_BARS_WIDTH, Constants.HUD_BOTTOM_BAR_HEIGHT).bottom().center().expand();
	}

	@Override
	public void update(float delta) {
		Gdx.gl.glClearColor(.15f, .15f, .15f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameViewPort.apply();
		cameraManager.update(mapManager.getCurrentMapWidth(), mapManager.getCurrentMapHeight(), gameViewPort.getWorldWidth(), gameViewPort.getWorldHeight());

		batch.setProjectionMatrix(cameraManager.getCamera().combined);
		mapManager.renderCurrentMap(cameraManager.getCamera());

		renderPlayer();

		renderUI();

	}

	private void renderPlayer() {
		if (player != null) {
			batch.setProjectionMatrix(gameViewPort.getCamera().combined);

			framebuffer.begin();
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			batch.begin();
			Sprite sprite = animationManager.getSprite(player);
			// sprite.setPosition(player.getPositionVector().x, player.getPositionVector().y);
			batch.draw(sprite.getTexture(), 0, 0, 16, 16, 0, 0, 1, 1);
			batch.end();

			framebuffer.end();
			TextureRegion framebufferRegion = new TextureRegion(framebuffer.getColorBufferTexture());
			framebufferRegion.flip(false, true);

			float u = framebufferRegion.getU();
			float v = framebufferRegion.getV();

			framebufferRegion.setRegion(u, v, 1 - u, 1 - v);

			// render the texture region to the screen using linear interpolation
			batch.setProjectionMatrix(gameViewPort.getCamera().combined);
			batch.begin();
			batch.draw(framebufferRegion, player.getPositionVector().x, player.getPositionVector().y, 32, 32);
			batch.end();
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
		// Update the framebuffer and viewport dimensions
		framebuffer.dispose();
		framebuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
		gameViewPort.update(width, height, true);
		batch.setProjectionMatrix(gameViewPort.getCamera().combined);
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		mapManager.dispose();
	}

}
