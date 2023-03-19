package jelte.mygame.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import jelte.mygame.Message;
import jelte.mygame.MessageListener;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;

public class GraphicalManagerImpl implements GraphicalManager {
	private static final String TAG = GraphicalManagerImpl.class.getSimpleName();
	private SpriteBatch batch;
	protected OrthogonalTiledMapRenderer mapRenderer;
	protected ExtendViewport gameViewPort;
	protected Stage stage;
	protected Skin skin;
	private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private World worldToRender;
	private MapManager mapManager;
	private CameraManager cameraManager;

	public GraphicalManagerImpl() {
		batch = new SpriteBatch();
		mapManager = new MapManager(batch);
		cameraManager = new CameraManager(gameViewPort.getCamera(),mapManager.getCurrentMapWidth(),mapManager.getCurrentMapHeight());
		skin = AssetManagerUtility.getSkin(Constants.SKIN_FILE_PATH);
		gameViewPort = new ExtendViewport(Constants.VISIBLE_WIDTH, Constants.VISIBLE_HEIGHT);
		stage = new Stage(gameViewPort, batch);
	}


	@Override
	public void update(float delta) {
		Gdx.gl.glClearColor(.15f, .15f, .15f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameViewPort.apply();
		cameraManager.update();

		batch.setProjectionMatrix(cameraManager.getCamera().combined);
		mapManager.renderCurrentMap(cameraManager.getCamera());
		debugRenderer.render(worldToRender, cameraManager.getCamera().combined);
	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case CAMERA_LEFT:
			camera.translate(Constants.CAMERA_MOVE_SPEED, 0, 0);
			camera.update();
			break;
		case CAMERA_RIGHT:
			camera.translate(-Constants.CAMERA_MOVE_SPEED, 0, 0);
			camera.update();
			break;
		case CAMERA_ZOOM:
			camera.zoom += (float) message.getValue();
			camera.update();
			break;
		case RENDER_WORLD:
			worldToRender = (World) message.getValue();
			break;
		case UPDATE_CAMERA:
			cameraPosition = (Vector2) message.getValue();
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
