package jelte.mygame.logic;

import java.util.Map;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterFileReader;
import jelte.mygame.logic.character.NpcCharacter;

public class LogicManagerImpl implements LogicManager {
	private static final String TAG = LogicManagerImpl.class.getSimpleName();
	private MessageListener listener;
	private Character player;
	private NpcCharacter enemy;
	private Array<Character> allCharacters;
	private MovementSystem movementSystem;
	private CollisionSystem collisionSystem;

	public LogicManagerImpl(MessageListener listener) {
		this.listener = listener;
		movementSystem = new MovementSystem();
		collisionSystem = new CollisionSystem();
		allCharacters = new Array<>();
		player = new Character(CharacterFileReader.getUnitData().get(4), UUID.randomUUID());
		enemy = new NpcCharacter(CharacterFileReader.getUnitData().get(3), UUID.randomUUID());
	}

	@Override
	public void update(float delta) {
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_PLAYER, player));
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_ENEMY, enemy));
		allCharacters.clear();
		allCharacters.add(player);
		allCharacters.add(enemy);
		Map<Character, Vector2> futurePositions = movementSystem.update(delta, allCharacters);
		collisionSystem.updateCollisions(futurePositions, allCharacters);
		player.update(delta);
		// enemy.update(delta, player);
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.UPDATE_CAMERA_POS, player.getPositionVector()));
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
			player.receiveMessage(message);
			break;
		case SEND_BLOCKING_OBJECTS:
			collisionSystem.setBlockingRectangles((Array<TypedRectangle>) message.getValue());
			break;
		default:
			break;

		}
	}

	@Override
	public void dispose() {

	}

}
