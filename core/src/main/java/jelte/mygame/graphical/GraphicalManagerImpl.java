package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
	private SpriteBatch batch;
	private MessageListener listener;
	protected OrthogonalTiledMapRenderer mapRenderer;
	protected ExtendViewport gameViewPort;
	protected Stage stage;
	protected Skin skin;
	private TiledMap map;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private World worldToRender;

	public GraphicalManagerImpl(MessageListener listener) {
		this.listener = listener;
		this.batch = new SpriteBatch();
		skin = AssetManagerUtility.getSkin(Constants.SKIN_FILE_PATH);
		gameViewPort = new ExtendViewport(Constants.VISIBLE_WIDTH, Constants.VISIBLE_HEIGHT);
		camera = (OrthographicCamera) gameViewPort.getCamera();
		stage = new Stage(gameViewPort, batch);
		AssetManagerUtility.loadMapAsset(Constants.MAP_PATH);
		mapRenderer = new OrthogonalTiledMapRenderer(AssetManagerUtility.getMapAsset(Constants.MAP_PATH), batch);
	}

	@Override
	public void update(float delta) {
		gameViewPort.apply();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		renderPlayer();
		batch.end();
		
		mapRenderer.setView(camera);// TODO optimize, only if camera changes
		mapRenderer.render();
		
		debugRenderer.render(worldToRender, camera.combined);

		renderUI();
	}

	private void renderUI() {
		// TODO Auto-generated method stub

	}

	private void renderPlayer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case CAMERA_LEFT:
			camera.translate(Constants.CAMERA_MOVE_SPEED, 0, 0);
			camera.update();
			break;
		case CAMERA_RIGHT:
			camera.translate(-Constants.CAMERA_MOVE_SPEED, 0,0);
			camera.update();
			break;
		case CAMERA_ZOOM:
			camera.zoom += (float) message.getValue();
			camera.update();
			break;
		case RENDER_WORLD:
			worldToRender = (World) message.getValue();
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
		mapRenderer.dispose();
	}

}
