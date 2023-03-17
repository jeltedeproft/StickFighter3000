package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import jelte.mygame.Message;
import jelte.mygame.MessageListener;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Variables;

public class GraphicalManagerImpl implements GraphicalManager {
	private SpriteBatch batch;
	private MessageListener listener;
	protected OrthogonalTiledMapRenderer mapRenderer;
	protected ExtendViewport gameViewPort;
	protected Stage stage;
	protected Skin skin;
	private TiledMap map;

	public GraphicalManagerImpl(MessageListener listener) {
		this.listener = listener;
		this.batch = new SpriteBatch();
		skin = AssetManagerUtility.getSkin(Variables.SKIN_FILE_PATH);
		gameViewPort = new ExtendViewport(Variables.VISIBLE_WIDTH, Variables.VISIBLE_HEIGHT);
		stage = new Stage(gameViewPort, batch);
		AssetManagerUtility.loadMapAsset(Variables.MAP_PATH);
		mapRenderer = new OrthogonalTiledMapRenderer(AssetManagerUtility.getMapAsset(Variables.MAP_PATH), batch);
	}

	@Override
	public void update(float delta) {
		OrthographicCamera oCam = (OrthographicCamera) gameViewPort.getCamera();
		mapRenderer.setView(oCam);// TODO optimize, only if camera changes
		mapRenderer.render();

		gameViewPort.apply();

		batch.setProjectionMatrix(gameViewPort.getCamera().combined);
		batch.begin();
		renderPlayer();
		batch.end();

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
		case CAMERA_MOVE_RIGHT:
			gameViewPort.getCamera().translate(Variables.CAMERA_MOVE_SPEED, 0, 0);
			gameViewPort.getCamera().update();
			break;
		case CAMERA_MOVE_LEFT:
			gameViewPort.getCamera().translate(0, Variables.CAMERA_MOVE_SPEED, 0);
			gameViewPort.getCamera().update();
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
