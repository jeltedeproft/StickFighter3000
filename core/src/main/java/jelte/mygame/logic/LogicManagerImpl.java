package jelte.mygame.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.graphical.map.EnemySpawnData;
import jelte.mygame.logic.ai.AiManager;
import jelte.mygame.logic.character.CharacterManager;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.logic.collisions.CollisionDetectionSystem;
import jelte.mygame.logic.collisions.CollisionDetectionSystemImpl;
import jelte.mygame.logic.collisions.CollisionHandlingSystem;
import jelte.mygame.logic.collisions.CollisionPair;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlock;
import jelte.mygame.logic.spells.SpellManager;
import jelte.mygame.logic.spells.modifier.ModifierManager;

public class LogicManagerImpl implements LogicManager {
	private static final String TAG = LogicManagerImpl.class.getSimpleName();
	private MessageListener listener;
	private CollisionDetectionSystem collisionDetectionSystem;
	private CollisionHandlingSystem collisionhandlingSystem;
	private CharacterManager characterManager;
	private AiManager aiManager;
	private SpellManager spellManager;
	private ModifierManager modifierManager;
	private Set<Collidable> blockingObjects;
	private Set<Collidable> allCollidables;
	private Vector2 mousePosition = new Vector2();

	public LogicManagerImpl(MessageListener listener) {
		this.listener = listener;
		allCollidables = new HashSet<>();
		collisionDetectionSystem = new CollisionDetectionSystemImpl();
		collisionhandlingSystem = new CollisionHandlingSystem();
		spellManager = new SpellManager();
		modifierManager = new ModifierManager();
		characterManager = new CharacterManager(new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID()));
		aiManager = new AiManager();
	}

	@Override
	public void update(float delta) {
		characterManager.update(delta);
		aiManager.update(delta, characterManager.getPlayer(), collisionDetectionSystem.getCollidables());
		spellManager.update(delta, mousePosition, characterManager.getAllCharacters());// TODO this order is important because graphicalImpl sets the dimensions of the sprites needed for adding them to the spatial mesh
		modifierManager.update(delta, characterManager.getAllCharacters());

		allCollidables.clear();
		allCollidables.addAll(characterManager.getAllCharacterbodies());
		allCollidables.addAll(characterManager.getVisions());
		allCollidables.addAll(spellManager.getAllSpellBodies());
		collisionDetectionSystem.updateSpatialMesh(allCollidables);
		Set<CollisionPair> collisionPairs = collisionDetectionSystem.getCollidingpairs();
		collisionhandlingSystem.handleCollisions(collisionPairs, characterManager.getAllCharacters(), spellManager.getAllSpells());

		characterManager.getEnemies().forEach(enemy -> listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_ENEMY, enemy)));
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_PLAYER, characterManager.getPlayer()));
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_SPELLS, spellManager.getAllSpells()));
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.UPDATE_CAMERA_POS, characterManager.getPlayer().getPhysicsComponent().getPosition()));
	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case DOWN_PRESSED, DOWN_UNPRESSED, LEFT_PRESSED, LEFT_UNPRESSED, RIGHT_PRESSED, RIGHT_UNPRESSED, UP_PRESSED, ATTACK_PRESSED, DASH_PRESSED, ROLL_PRESSED, TELEPORT_PRESSED, SPRINT_PRESSED, SPRINT_UNPRESSED, BLOCK_PRESSED, BLOCK_UNPRESSED, CAST_PRESSED -> characterManager.getPlayer().receiveMessage(message);
		case SEND_MOUSE_COORDINATES -> {
			Vector3 mouseVector = (Vector3) message.getValue();
			mousePosition.x = mouseVector.x;
			mousePosition.y = mouseVector.y;
		}
		case SEND_BLOCKING_OBJECTS -> blockingObjects = new HashSet<>((Set<StaticBlock>) message.getValue());
		case SPAWN_ENEMIES -> {
			characterManager.spawnEnemies((Collection<EnemySpawnData>) message.getValue());
			aiManager.addEnemies(characterManager.getEnemies());
		}
		case SEND_MAP_DIMENSIONS -> {
			collisionDetectionSystem.initSpatialMesh((Vector2) message.getValue());
			collisionDetectionSystem.initializeStatickCollidables(blockingObjects);
		}
		default -> {}

		}
	}

	public Vector2 getMousePosition() {
		return mousePosition;
	}

	@Override
	public void dispose() {

	}

}
