package jelte.mygame.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.graphical.animations.AnimationManager;
import jelte.mygame.graphical.animations.NamedSprite;
import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.hud.HudManager;
import jelte.mygame.graphical.particles.ParticleMaker;
import jelte.mygame.graphical.particles.ParticleType;
import jelte.mygame.graphical.specialEffects.SpecialEffect;
import jelte.mygame.graphical.specialEffects.SpecialEffectsManager;
import jelte.mygame.graphical.specialEffects.SpecialEffectsManagerImpl;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.collisions.collidable.Item;
import jelte.mygame.logic.collisions.collidable.StaticBlock;
import jelte.mygame.logic.spells.SpellsEnum;
import jelte.mygame.logic.spells.spells.AbstractSpell;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.GraphicalUtility;
import lombok.Getter;

@Getter
public class GraphicalManagerImpl implements GraphicalManager {

	private static final String TAG = GraphicalManagerImpl.class.getSimpleName();

	private MessageListener messageListener;
	private SpriteBatch batch;
	private ExtendViewport gameViewport;
	private Stage stage;
	private MapManager mapManager;
	private CameraManager cameraManager;
	private AnimationManager animationManager;
	private SpecialEffectsManager specialEffectsManager;
	private PlayerCharacter player;
	private AiCharacter enemy;
	private Array<AbstractSpell> spellsToRender;
	private ParticleMaker particleMaker;
	private BitmapFont font = new BitmapFont();
	private HudManager hudManager;

	public GraphicalManagerImpl(MessageListener messageListener) {
		this.messageListener = messageListener;

		AssetManagerUtility.loadTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		AssetManagerUtility.loadSkin(Constants.SKIN_FILE_PATH);

		batch = new SpriteBatch();
		hudManager = new HudManager(messageListener, batch);
		mapManager = new MapManager(batch);
		animationManager = new AnimationManager();
//		font = new BitmapFont();
//		font.getData().setScale(1.0f);

		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/thedark.TTF"));
		FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
		fontParameter.size = 24;
		font = fontGenerator.generateFont(fontParameter);
		fontGenerator.dispose();

		spellsToRender = new Array<>();

		specialEffectsManager = new SpecialEffectsManagerImpl();
		particleMaker = new ParticleMaker();
		particleMaker.addParticle(ParticleType.DUST, new Vector2(0, 0));

		gameViewport = new ExtendViewport(Constants.VISIBLE_WIDTH, Constants.VISIBLE_HEIGHT);
		cameraManager = new CameraManager(gameViewport.getCamera());
		stage = new Stage(gameViewport, batch);
		hudManager.setMinimap(mapManager.createMinimaptexture(cameraManager.getCamera()));

		messageListener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_BLOCKING_OBJECTS, mapManager.getBlockingRectangles()));
		messageListener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_ITEMS, mapManager.getItems()));
		messageListener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SPAWN_ENEMIES, mapManager.getEnemySpawnData()));
		messageListener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_MAP_DIMENSIONS, new Vector2(mapManager.getCurrentMapWidth(), mapManager.getCurrentMapHeight())));

		MusicManager.getInstance().sendCommand(AudioCommand.MUSIC_PLAY, AudioEnum.PLAYLIST_MAIN);
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_AMBIENCE_CAVE);

	}

	@Override
	public void update(float delta) {
		// Gdx.gl.glClearColor(.15f, .15f, .15f, 1); gray
		Gdx.gl.glClearColor(0.043f, 0.043f, 0.043f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		animationManager.update(delta);
		gameViewport.apply();
		cameraManager.update(mapManager.getCurrentMapWidth(), mapManager.getCurrentMapHeight(), gameViewport.getWorldWidth(), gameViewport.getWorldHeight());

		batch.setProjectionMatrix(cameraManager.getCamera().combined);
		mapManager.renderCurrentMap(cameraManager.getCamera(), player.getPhysicsComponent().getPosition());

		if (player.getCharacterStateManager().isStateChanged() && animationManager.getSpecialEffect(player) != null) {
			specialEffectsManager.addSpecialEffect(player, animationManager.getSpecialEffect(player));
		}

		specialEffectsManager.update(delta, player);// TODO for all characters

		MusicManager.getInstance().update(delta, cameraManager.getCamera().position.x, cameraManager.getCamera().position.y);

		batch.begin();
		renderCharacters();
		renderSpells();
		renderSpecialEffects();
		particleMaker.drawAllActiveParticles(batch, delta);
		font.draw(batch, player.getCharacterStateManager().getCurrentCharacterState().getState().toString(), player.getPhysicsComponent().getRectangle().x, player.getPhysicsComponent().getRectangle().y + Constants.OFFSET_Y_HP_BAR);
		font.draw(batch, enemy.getState().toString(), enemy.getPhysicsComponent().getRectangle().x, enemy.getPhysicsComponent().getRectangle().y + Constants.OFFSET_Y_HP_BAR * 2f);
		batch.end();

		hudManager.renderUI();
		// hudManager.renderMinimapDot(mapManager.getRelativePlayerPositionMinimap(player.getPhysicsComponent().getPosition()));

//		debugPlayer();
//		debugEnemy();
//		debugStaticObjects();
//		debugSpells();
//		debugVisions();

		// debug info player
		batch.begin();
		font.draw(batch, String.format("player inpuy: %s", player.getCharacterInputHandler().getInputBox()), 40, 420);
		font.draw(batch, String.format("player position: %s", player.getPhysicsComponent().getPosition()), 40, 400);
		font.draw(batch, String.format("player direction: %s", player.getPhysicsComponent().getDirection()), 40, 380);
		font.draw(batch, String.format("player rectangle: %s", player.getPhysicsComponent().getRectangle()), 40, 360);
		font.draw(batch, String.format("player velocity: %s", player.getPhysicsComponent().getVelocity()), 40, 340);
		Gdx.app.debug(TAG, "player velocity: " + player.getPhysicsComponent().getVelocity());
		font.draw(batch, String.format("player acceleration: %s", player.getPhysicsComponent().getAcceleration()), 40, 320);
		font.draw(batch, String.format("collided: %s", player.getPhysicsComponent().isCollided()), 40, 300);
		font.draw(batch, String.format("falltrough: %s", player.getPhysicsComponent().isFallTrough()), 40, 280);
		font.draw(batch, String.format("state: %s", player.getCurrentCharacterState().getState()), 40, 260);
		font.draw(batch, String.format("dimensions: %.4f,%.4f", player.getPhysicsComponent().getWidth(), player.getPhysicsComponent().getHeight()), 40, 240);
		font.draw(batch, String.format("animation Name: %s,%.4f", animationManager.getSprite(player).getName(), player.getPhysicsComponent().getHeight()), 40, 220);
		font.draw(batch, String.format("FPS: %s", Gdx.graphics.getFramesPerSecond()), 40, 200);
//		font.draw(batch, String.format("enemy position: %s", enemy.getPhysicsComponent().getPosition()), 40, 180);
//		font.draw(batch, String.format("enemy rectangle: %s", enemy.getPhysicsComponent().getRectangle()), 40, 160);
//		font.draw(batch, String.format("enemy velocity: %s", enemy.getPhysicsComponent().getVelocity()), 40, 140);
//		font.draw(batch, String.format("enemy acceleration: %s", enemy.getPhysicsComponent().getAcceleration()), 40, 120);
//		font.draw(batch, String.format("ai state: %s", enemy.getState()), 40, 100);
//		font.draw(batch, String.format("active patrol point index: %s", enemy.getActivePatrolPointIndex()), 40, 80);
//		font.draw(batch, String.format("player seen: %s", enemy.getVisionCollidable().isPlayerSeen()), 40, 60);
//		font.draw(batch, String.format("character state enemy ai : %s", enemy.getCurrentCharacterState().getState()), 40, 40);
//		font.draw(batch, String.format("FPS: %s", Gdx.graphics.getFramesPerSecond()), 40, 20);
//		font.draw(batch, String.format("enemy direction: %s", enemy.getPhysicsComponent().getDirection()), 40, 0);
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
			player.getPhysicsComponent().setSize(sprite.getWidth(), sprite.getHeight());
			sprite.setPosition(player.getPhysicsComponent().getRectangle().x, player.getPhysicsComponent().getRectangle().y);
			sprite.draw(batch);
		}
		if (enemy != null) {
			hudManager.renderhealthBar(enemy, batch, font);
			NamedSprite sprite = animationManager.getSprite(enemy);
			enemy.getPhysicsComponent().setSize(sprite.getWidth(), sprite.getHeight());
			sprite.setPosition(enemy.getPhysicsComponent().getRectangle().x, enemy.getPhysicsComponent().getRectangle().y);
			sprite.draw(batch);
		}
	}

	private void renderSpells() {
		for (AbstractSpell spell : spellsToRender) {
			NamedSprite sprite = animationManager.getSprite(spell);
			spell.getPhysicsComponent().setSize(sprite.getWidth(), sprite.getHeight());
			sprite.setPosition(spell.getPhysicsComponent().getRectangle().x, spell.getPhysicsComponent().getRectangle().y);
			sprite.draw(batch);
		}

	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case CAMERA_ZOOM:
			cameraManager.zoomCamera((float) message.getValue());
			break;
		case RENDER_PLAYER:
			player = (PlayerCharacter) message.getValue();
			hudManager.updatePlayerStats(player);
			break;
		case RENDER_ENEMY:
			enemy = (AiCharacter) message.getValue();
			break;
		case RENDER_SPELLS:
			spellsToRender = (Array<AbstractSpell>) message.getValue();
			break;
		case UPDATE_CAMERA_POS:
			cameraManager.updateCameraPos((Vector2) message.getValue());
			break;
		case ACTIVATE_SPELL:
			SpellsEnum spellsEnum = (SpellsEnum) message.getValue();
			hudManager.activateNextSpell(spellsEnum);
			break;
		case REMOVE_ITEM:
			mapManager.removeitem((Item) message.getValue());
			break;
		case EXIT_GAME:
			// dispose();
			Gdx.app.exit();
			break;
		default:
			break;

		}
	}

	@Override
	public void resize(int width, int height) {
		gameViewport.update(width, height);
		hudManager.resize(width, height);
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

	private void debugPlayer() {
		batch.begin();
		GraphicalUtility.drawDebugRectangle(player.getPhysicsComponent().getRectangle(), 3, Color.GREEN, gameViewport.getCamera().combined);
		batch.end();
	}

	private void debugEnemy() {
		batch.begin();
		GraphicalUtility.drawDebugRectangle(enemy.getPhysicsComponent().getRectangle(), 3, Color.RED, gameViewport.getCamera().combined);
		batch.end();
	}

	private void debugVisions() {
		batch.begin();
		GraphicalUtility.drawDebugRectangle(enemy.getVisionCollidable().getRectangle(), 3, Color.PINK, gameViewport.getCamera().combined);
		batch.end();
	}

	private void debugSpells() {
		batch.begin();
		for (AbstractSpell spell : spellsToRender) {
			GraphicalUtility.drawDebugRectangle(spell.getPhysicsComponent().getRectangle(), 3, Color.YELLOW, gameViewport.getCamera().combined);
		}

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