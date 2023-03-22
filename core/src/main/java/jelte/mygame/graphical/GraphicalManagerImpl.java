package jelte.mygame.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import box2dLight.PointLight;
import box2dLight.RayHandler;
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
	protected OrthogonalTiledMapRenderer mapRenderer;
	protected ExtendViewport gameViewPort;
	protected Stage stage;
	protected Skin skin;
	private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	Array<Body> bodies = new Array<>();
	private World worldToRender;
	private RayHandler rayHandler;
	private MapManager mapManager;
	private CameraManager cameraManager;
	private AnimationManager animationManager;

	public GraphicalManagerImpl(MessageListener messageListener) {
		this.messageListener = messageListener;
		AssetManagerUtility.loadTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		batch = new SpriteBatch();
		mapManager = new MapManager(batch);
		animationManager = new AnimationManager();
		messageListener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_MAP_DIMENSIONS, new Vector2(mapManager.getCurrentMapWidth(), mapManager.getCurrentMapHeight())));
		skin = AssetManagerUtility.getSkin(Constants.SKIN_FILE_PATH);
		gameViewPort = new ExtendViewport(Constants.VISIBLE_WIDTH, Constants.VISIBLE_HEIGHT);
		cameraManager = new CameraManager(gameViewPort.getCamera());
		stage = new Stage(gameViewPort, batch);
		MusicManager.getInstance().sendCommand(AudioCommand.MUSIC_PLAY_LOOP, AudioEnum.MAIN_THEME);
	}

	@Override
	public void update(float delta) {
		Gdx.gl.glClearColor(.15f, .15f, .15f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameViewPort.apply();
		cameraManager.update(mapManager.getCurrentMapWidth(), mapManager.getCurrentMapHeight(), gameViewPort.getWorldWidth(), gameViewPort.getWorldHeight());

		batch.setProjectionMatrix(cameraManager.getCamera().combined);
		mapManager.renderCurrentMap(cameraManager.getCamera());

		batch.begin();
		renderBodies();
		batch.end();

		debugRenderer.render(worldToRender, cameraManager.getCamera().combined);

		if (rayHandler != null) {
			rayHandler.setCombinedMatrix(cameraManager.getCamera());
			rayHandler.updateAndRender();
		}

	}

	private void renderBodies() {
		worldToRender.getBodies(bodies);
		for (Body body : bodies) {
			Character character = (Character) body.getUserData();
			if (character != null) {
				Sprite sprite = animationManager.getSprite(character);
				float height = sprite.getHeight();
				float width = sprite.getWidth();
				sprite.setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
				sprite.setRotation(MathUtils.radiansToDegrees * body.getAngle());
				sprite.draw(batch);
			}
		}

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
		case RENDER_WORLD:
			worldToRender = (World) message.getValue();
			if (rayHandler != null) {
				rayHandler = new RayHandler(worldToRender);
				rayHandler.setAmbientLight(1, 1, 1, 1);
				rayHandler.setBlurNum(3);
				new PointLight(rayHandler, 4, new Color(1, 1, 1, 1), 0, 0, 0);
			}
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
		gameViewPort.update(width, height);
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		mapManager.dispose();
	}

}
