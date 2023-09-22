package jelte.mygame.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import jelte.mygame.Message;
import jelte.mygame.MessageListener;
import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.hud.MainMenuHudManager;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;

public class MainMenuGraphicalManager implements GraphicalManager {
	private static final String TAG = MainMenuGraphicalManager.class.getSimpleName();
	private MessageListener messageListener;
	private SpriteBatch batch;
	private FitViewport gameViewport;
	private Stage stage;
	private MapManager mapManager;
	private CameraManager cameraManager;
	private BitmapFont font = new BitmapFont();
	private MainMenuHudManager mainMenuHudManager;

	public MainMenuGraphicalManager(MessageListener messageListener) {
		this.messageListener = messageListener;

		AssetManagerUtility.loadTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		AssetManagerUtility.loadSkin(Constants.SKIN_FILE_PATH);

		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/thedark.TTF"));
		FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
		fontParameter.size = 24;
		font = fontGenerator.generateFont(fontParameter);
		fontGenerator.dispose();

		batch = new SpriteBatch();
		mapManager = new MapManager(batch, Constants.MAIN_MENU_MAP_PATH);
		mainMenuHudManager = new MainMenuHudManager(messageListener, batch);

		gameViewport = new FitViewport(Constants.MAIN_MENU_VISIBLE_WIDTH, Constants.MAIN_MENU_VISIBLE_HEIGHT);
		cameraManager = new CameraManager(gameViewport.getCamera());
		cameraManager.getCamera().position.set(mapManager.getCurrentMapWidth() * 0.5f, mapManager.getCurrentMapHeight() * 0.5f, 0);
		cameraManager.getCamera().update();
		stage = new Stage(gameViewport, batch);
		stage.setDebugAll(true);

		MusicManager.getInstance().sendCommand(AudioCommand.MUSIC_PLAY, AudioEnum.PLAYLIST_MAIN_MENU);
	}

	@Override
	public void update(float delta) {
		Gdx.gl.glClearColor(.15f, .15f, .15f, 1); // gray
		// Gdx.gl.glClearColor(0.043f, 0.043f, 0.043f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameViewport.apply();

		batch.setProjectionMatrix(cameraManager.getCamera().combined);
		mapManager.renderCurrentMap(cameraManager.getCamera());

		mainMenuHudManager.renderUI();
	}

	@Override
	public void resize(int width, int height) {
		gameViewport.update(width, height);
		mainMenuHudManager.resize(width, height);
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		mapManager.dispose();
		font.dispose();
	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case CAMERA_ZOOM:
			cameraManager.zoomCamera((float) message.getValue());
			break;
		case SEND_BUTTONS_MAP:
			// TODO???
			break;
		case EXIT_GAME:
			Gdx.app.exit();
			break;
		}
	}

}
