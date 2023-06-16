package jelte.mygame.logic.ai;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.NpcCharacter;

public class BasicEnemyAiStrategy implements AiStrategy {
	private NpcCharacter self;
	private float timeSinceLastCommand = 0f;

	public BasicEnemyAiStrategy(NpcCharacter self) {
		this.self = self;
	}

	@Override
	// go left and right
	public void update(float delta, Character player, AiState state) {
		timeSinceLastCommand += delta;
		if (timeSinceLastCommand >= 2.0f) {
			if (self.getPhysicsComponent().getDirection() == Direction.left) {
				sendMessage(new Message(RECIPIENT.LOGIC, ACTION.LEFT_UNPRESSED));
				sendMessage(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_PRESSED));
			} else {
				sendMessage(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_UNPRESSED));
				sendMessage(new Message(RECIPIENT.LOGIC, ACTION.LEFT_PRESSED));
			}
			timeSinceLastCommand = 0f;
		}
	}

	@Override
	public void sendMessage(Message message) {
		self.receiveMessage(message);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("basic enemy ai strategy");
		return sb.toString();
	}

}
