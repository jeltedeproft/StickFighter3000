package jelte.mygame.logic;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterFileReader;
import jelte.mygame.logic.character.CharacterManager;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.physics.PhysicsComponent;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.collisions.Collidable;
import jelte.mygame.logic.collisions.CollisionSystem;
import jelte.mygame.logic.collisions.CollisionSystemImpl;
import jelte.mygame.logic.collisions.StaticBlock;
import jelte.mygame.logic.spells.SpellManager;

public class LogicManagerImpl implements LogicManager {
	private static final String TAG = LogicManagerImpl.class.getSimpleName();
	private MessageListener listener;
	private CollisionSystem collisionSystem;
	private CharacterManager characterManager;
	private SpellManager spellManager;
	private Array<Collidable> blockingObjects;

	public LogicManagerImpl(MessageListener listener) {
		this.listener = listener;
		collisionSystem = new CollisionSystemImpl();
		spellManager = new SpellManager();
		characterManager = new CharacterManager(new PlayerCharacter(CharacterFileReader.getUnitData().get(4), UUID.randomUUID()));
		// characterManager.addEnemy(new NpcCharacter(CharacterFileReader.getUnitData().get(3), UUID.randomUUID()));
	}

	@Override
	public void update(float delta) {
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_PLAYER, characterManager.getPlayer()));
		characterManager.getEnemies().forEach(enemy -> listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_ENEMY, enemy)));
		characterManager.update(delta);
		characterManager.getBodies().forEach(this::checkCollision);
		characterManager.getAllCharacters().forEach(this::createQueuedSpells);
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_SPELLS, spellManager.getAllSpells()));
		spellManager.update(delta);
		Array<Collidable> collidables = new Array<>(characterManager.getAllCharacterbodies());
		collidables.addAll(spellManager.getAllSpells());
		collisionSystem.updateSpatialMesh(collidables);
		collisionSystem.executeCollisions();

		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.UPDATE_CAMERA_POS, characterManager.getPlayer().getPhysicsComponent().getPosition()));
	}

	private void createQueuedSpells(Character character) {
		while (character.getSpellsToCast().notEmpty()) {
			spellManager.addSpell(character.getSpellsToCast().removeFirst());
		}
	}

	private void checkCollision(PhysicsComponent body) {
		if (body.isCollided()) {
			body.setCollided(false);
		} else {
			characterManager.getCharacterById(body.getPlayerReference()).getCurrentCharacterState().handleEvent(EVENT.NO_COLLISION);
		}
	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case DOWN_PRESSED:
		case DOWN_UNPRESSED:
		case LEFT_PRESSED:
		case LEFT_UNPRESSED:
		case RIGHT_PRESSED:
		case RIGHT_UNPRESSED:
		case UP_PRESSED:
		case ATTACK_PRESSED:
		case DASH_PRESSED:
		case ROLL_PRESSED:
		case TELEPORT_PRESSED:
		case SPRINT_PRESSED:
		case SPRINT_UNPRESSED:
		case BLOCK_PRESSED:
		case BLOCK_UNPRESSED:
		case CAST_PRESSED:
		case SEND_MOUSE_COORDINATES:
			characterManager.getPlayer().receiveMessage(message);
			break;
		case SEND_BLOCKING_OBJECTS:
			blockingObjects = new Array<>((Array<StaticBlock>) message.getValue());
			break;
		case SEND_MAP_DIMENSIONS:
			collisionSystem.initSpatialMesh((Vector2) message.getValue());
			Array<Collidable> characterBodies = new Array<>(characterManager.getAllCharacterbodies());
			collisionSystem.addToSpatialMesh(characterBodies);
			collisionSystem.addToSpatialMesh(blockingObjects);
			break;
		default:
			break;

		}
	}

	@Override
	public void dispose() {

	}

}
