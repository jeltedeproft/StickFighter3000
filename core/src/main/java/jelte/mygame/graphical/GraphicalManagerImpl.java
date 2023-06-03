package jelte.mygame.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.graphical.animations.AnimationManager;
import jelte.mygame.graphical.animations.NamedSprite;
import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.specialEffects.SpecialEffect;
import jelte.mygame.graphical.specialEffects.SpecialEffectsManager;
import jelte.mygame.graphical.specialEffects.SpecialEffectsManagerImpl;
import jelte.mygame.logic.character.NpcCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.collisions.collidable.StaticBlock;
import jelte.mygame.logic.spells.AbstractSpell;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.GraphicalUtility;

public class GraphicalManagerImpl implements GraphicalManager {

	private static final String TAG = GraphicalManagerImpl.class.getSimpleName();

	private MessageListener messageListener;
	private SpriteBatch batch;
	private ExtendViewport gameViewport;
	private ScreenViewport uiViewport;
	private Stage stage;
	private Stage uiStage;
	private Skin skin;
	private MapManager mapManager;
	private CameraManager cameraManager;
	private AnimationManager animationManager;
	private SpecialEffectsManager specialEffectsManager;
	private PlayerCharacter player;
	private NpcCharacter enemy;
	private ProgressBar hp;
	private BitmapFont font;
	private Array<AbstractSpell> spellsToRender;

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
		font = new BitmapFont();
		spellsToRender = new Array<>();
		specialEffectsManager = new SpecialEffectsManagerImpl();

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

		MusicManager.getInstance().sendCommand(AudioCommand.MUSIC_PLAY, AudioEnum.PLAYLIST_MAIN);

		createHud();
	}

	protected void createHud() {
		Table table = new Table();
		table.setFillParent(true); // Makes the table fill the entire screen
		table.setPosition(0, 0);

		hp = new ProgressBar(0, Constants.PLAYER_MAX_HP, 1, false, skin, "hp");
		table.add(hp).expand().left().top(); // Positions the button in the center of the table

		uiStage.addActor(table); // Adds the table to the stage

	}

	@Override
	public void update(float delta) {
		Gdx.gl.glClearColor(.15f, .15f, .15f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		animationManager.update(delta);
		gameViewport.apply();
		cameraManager.update(mapManager.getCurrentMapWidth(), mapManager.getCurrentMapHeight(), gameViewport.getWorldWidth(), gameViewport.getWorldHeight());

		batch.setProjectionMatrix(cameraManager.getCamera().combined);
		mapManager.renderCurrentMap(cameraManager.getCamera());

		if (player.getCharacterStateManager().isStateChanged() && animationManager.getSpecialEffect(player) != null) {
			specialEffectsManager.addSpecialEffect(player, animationManager.getSpecialEffect(player));
		}

		specialEffectsManager.update(delta, player);// TODO for all characters

		MusicManager.getInstance().update(delta, cameraManager.getCamera().position.x, cameraManager.getCamera().position.y);

		batch.begin();
		renderCharacters();
		renderSpells();
		renderSpecialEffects();
		batch.end();

		renderUI();

//		debugRectangles();
//		debugStaticObjects();

		// debug info player
		batch.begin();
		font.draw(batch, String.format("player position: %s", player.getPhysicsComponent().getPosition()), 0, Gdx.graphics.getHeight() - 10);
		font.draw(batch, String.format("player rectangle: %s", player.getPhysicsComponent().getRectangle()), 0, Gdx.graphics.getHeight() - 25);
		font.draw(batch, String.format("player velocity: %s", player.getPhysicsComponent().getVelocity()), 0, Gdx.graphics.getHeight() - 40);
		font.draw(batch, String.format("player acceleration: %s", player.getPhysicsComponent().getAcceleration()), 0, Gdx.graphics.getHeight() - 70);
		font.draw(batch, String.format("collided: %s", player.getPhysicsComponent().isCollided()), 0, Gdx.graphics.getHeight() - 100);
		font.draw(batch, String.format("falltrough: %s", player.getPhysicsComponent().isFallTrough()), 0, Gdx.graphics.getHeight() - 130);
		font.draw(batch, String.format("state: %s", player.getCurrentCharacterState().getState()), 0, Gdx.graphics.getHeight() - 160);
		font.draw(batch, String.format("dimensions: %.4f,%.4f", player.getPhysicsComponent().getWidth(), player.getPhysicsComponent().getHeight()), 0, Gdx.graphics.getHeight() - 190);
		font.draw(batch, String.format("animation Name: %s,%.4f", animationManager.getSprite(player).getName(), player.getPhysicsComponent().getHeight()), 0, Gdx.graphics.getHeight() - 220);
		batch.end();

		messageListener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_MOUSE_COORDINATES, getMousePosition()));
	}

	private void renderSpecialEffects() {
		for (SpecialEffect effect : specialEffectsManager.getAllEffects()) {
			effect.draw(batch);
		}
	}

	private void renderCharacters() {
		if (player != null) {
			NamedSprite sprite = animationManager.getSprite(player);
			player.getPhysicsComponent().setDimensions(sprite.getWidth(), sprite.getHeight());
			sprite.setPosition(player.getPhysicsComponent().getRectangle().x, player.getPhysicsComponent().getRectangle().y);
			sprite.draw(batch);
		}
		if (enemy != null) {
			NamedSprite sprite = animationManager.getSprite(enemy);
			enemy.getPhysicsComponent().setDimensions(sprite.getWidth(), sprite.getHeight());
			sprite.setPosition(enemy.getPhysicsComponent().getRectangle().x, enemy.getPhysicsComponent().getRectangle().y);
			sprite.draw(batch);
		}
	}

	private void renderSpells() {
		for (AbstractSpell spell : spellsToRender) {
			NamedSprite sprite = animationManager.getSprite(spell);
			spell.getPhysicsComponent().setDimensions(sprite.getWidth(), sprite.getHeight());
			sprite.setPosition(spell.getPhysicsComponent().getRectangle().x, spell.getPhysicsComponent().getRectangle().y);
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
		case CAMERA_ZOOM:
			cameraManager.zoomCamera((float) message.getValue());
			break;
		case RENDER_PLAYER:
			player = (PlayerCharacter) message.getValue();
			hp.setValue(player.getCurrentHp());
			break;
		case RENDER_ENEMY:
			enemy = (NpcCharacter) message.getValue();
			break;
		case RENDER_SPELLS:
			spellsToRender = (Array<AbstractSpell>) message.getValue();
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

	public Vector3 getMousePosition() {
		return gameViewport.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		mapManager.dispose();
		font.dispose();
	}

	private void debugRectangles() {
		batch.begin();
		GraphicalUtility.drawDebugRectangle(player.getPhysicsComponent().getRectangle(), 3, Color.RED, gameViewport.getCamera().combined);
		// GraphicalUtility.drawDebugRectangle(enemy.getPhysicsComponent().getRectangle(), 3, Color.RED, gameViewport.getCamera().combined);
		batch.end();
	}

	private void debugStaticObjects() {
		batch.begin();
		for (StaticBlock rect : mapManager.getBlockingRectangles()) {
			GraphicalUtility.drawDebugRectangle(rect.getRectangle(), 3, Color.BLUE, gameViewport.getCamera().combined);
		}

		batch.end();
	}

}