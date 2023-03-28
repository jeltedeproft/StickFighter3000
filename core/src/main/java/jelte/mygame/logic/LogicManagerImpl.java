package jelte.mygame.logic;

import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterFileReader;

public class LogicManagerImpl implements LogicManager {
	private static final String TAG = LogicManagerImpl.class.getSimpleName();
	private MessageListener listener;
	private Character player;
	private MovementSystem movementSystem;

	public LogicManagerImpl(MessageListener listener) {
		this.listener = listener;
		movementSystem = new MovementSystem();
		player = new Character(CharacterFileReader.getUnitData().get(2), UUID.randomUUID());
	}

	@Override
	public void update(float delta) {
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_PLAYER, player));
		movementSystem.update(delta, player);
		player.update(delta);
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
			player.receiveMessage(message);
			break;
		case SEND_BLOCKING_OBJECTS:
			movementSystem.initBlockingObjects((Array<Rectangle>) message.getValue());
			break;
		default:
			break;

		}
	}

	@Override
	public void dispose() {

	}

}
