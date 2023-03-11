package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;

import jelte.mygame.Message;
import jelte.mygame.MessageListener;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Variables;

public class GraphicalManagerImpl implements GraphicalManager {
	private SpriteBatch batch;
	private MessageListener listener;
	protected OrthogonalTiledMapRenderer mapRenderer;
	protected FillViewport gameViewPort;
	protected Stage stage;
	protected Skin skin;
	private TiledMap map;

	public GraphicalManagerImpl(MessageListener listener) {
		this.listener = listener;
		this.batch = new SpriteBatch();
		skin = AssetManagerUtility.getSkin(Variables.SKIN_FILE_PATH);
		gameViewPort = new FillViewport(Variables.VISIBLE_WIDTH, Variables.VISIBLE_HEIGHT);
		stage = new Stage(gameViewPort, batch);
		AssetManagerUtility.loadMapAsset("map/dark/dark.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(AssetManagerUtility.getMapAsset("map/dark/dark.tmx"), batch);
	}

	@Override
	public void update(float delta) {
		OrthographicCamera oCam = (OrthographicCamera) gameViewPort.getCamera();
		mapRenderer.setView(oCam);// TODO optimize, only if camera changes
		mapRenderer.render();
	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case CAMERA_MOVE_X:
			gameViewPort.getCamera().translate(message.getValue(), 0, 0);
			gameViewPort.getCamera().update();
			break;
		case CAMERA_MOVE_Y:
			gameViewPort.getCamera().translate(0, message.getValue(), 0);
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
