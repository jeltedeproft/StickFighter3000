package jelte.mygame.logic;

import java.util.Set;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.logic.character.CharacterFileReader;
import jelte.mygame.logic.character.CharacterManager;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.collisions.CollisionDetectionSystem;
import jelte.mygame.logic.collisions.CollisionDetectionSystemImpl;
import jelte.mygame.logic.collisions.CollisionHandlingSystem;
import jelte.mygame.logic.collisions.CollisionPair;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlock;
import jelte.mygame.logic.spells.SpellManager;

public class LogicManagerImpl implements LogicManager {
	private static final String TAG = LogicManagerImpl.class.getSimpleName();
	private MessageListener listener;
	private CollisionDetectionSystem collisionDetectionSystem;
	private CollisionHandlingSystem collisionhandlingSystem;
	private CharacterManager characterManager;
	private SpellManager spellManager;
	private Array<Collidable> blockingObjects;
	private Vector2 mousePosition = new Vector2();

	public LogicManagerImpl(MessageListener listener) {
		this.listener = listener;
		collisionDetectionSystem = new CollisionDetectionSystemImpl();
		collisionhandlingSystem = new CollisionHandlingSystem();
		spellManager = new SpellManager();
		characterManager = new CharacterManager(new PlayerCharacter(CharacterFileReader.getUnitData().get(4), UUID.randomUUID()));
		// characterManager.addEnemy(new NpcCharacter(CharacterFileReader.getUnitData().get(3), UUID.randomUUID()));
	}

	@Override
	public void update(float delta) {
		Set<CollisionPair> collisionPairs = collisionDetectionSystem.getCollidingpairs();
		collisionhandlingSystem.handleCollisions(collisionPairs, characterManager.getAllCharacters(), spellManager.getAllSpells());
		characterManager.update(delta);
		spellManager.update(delta, mousePosition, characterManager.getAllCharacters());

		collisionDetectionSystem.updateSpatialMesh(characterManager.getAllCharacterbodies());
		collisionDetectionSystem.updateSpatialMesh(spellManager.getAllSpellBodies());

		characterManager.getEnemies().forEach(enemy -> listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_ENEMY, enemy)));
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_PLAYER, characterManager.getPlayer()));
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_SPELLS, spellManager.getAllSpells()));
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.UPDATE_CAMERA_POS, characterManager.getPlayer().getPhysicsComponent().getPosition()));
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
			characterManager.getPlayer().receiveMessage(message);
			break;
		case SEND_MOUSE_COORDINATES:
			Vector3 mouseVector = (Vector3) message.getValue();
			mousePosition.x = mouseVector.x;
			mousePosition.y = mouseVector.y;
			break;
		case CAST_PRESSED:
			characterManager.getPlayer().receiveMessage(message);
			break;
		case SEND_BLOCKING_OBJECTS:
			blockingObjects = new Array<>((Array<StaticBlock>) message.getValue());
			break;
		case SEND_MAP_DIMENSIONS:
			collisionDetectionSystem.initSpatialMesh((Vector2) message.getValue());
			collisionDetectionSystem.addToSpatialMesh(characterManager.getAllCharacterbodies());
			collisionDetectionSystem.addToSpatialMesh(blockingObjects);
			break;
		default:
			break;

		}
	}

	@Override
	public void dispose() {

	}

}
