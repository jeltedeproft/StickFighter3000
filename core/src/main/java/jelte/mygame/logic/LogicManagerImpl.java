package jelte.mygame.logic;

import java.util.UUID;

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
	private MovementSystem movementSystem;

	public LogicManagerImpl(MessageListener listener) {
		this.listener = listener;
		movementSystem = new MovementSystem();
		player = new Character(CharacterFileReader.getUnitData().get(2), UUID.randomUUID());
		enemy = new NpcCharacter(CharacterFileReader.getUnitData().get(3), UUID.randomUUID());
	}

	@Override
	public void update(float delta) {
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_PLAYER, player));
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_ENEMY, enemy));
		movementSystem.update(delta, player);
		movementSystem.update(delta, enemy);
		player.update(delta);
		enemy.update(delta, player);
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
			player.receiveMessage(message);
			break;
		case SEND_BLOCKING_OBJECTS:
			movementSystem.setBlockingRectangles((Array<TypedRectangle>) message.getValue());
			break;
		default:
			break;

		}
	}

	@Override
	public void dispose() {

	}

}
