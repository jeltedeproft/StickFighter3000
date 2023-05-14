package jelte.mygame.logic.character;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.logic.Direction;

public class PassiveAiStrategy implements AiStrategy {
	private NpcCharacter self;
	private float timeSinceLastCommand = 0f;

	public PassiveAiStrategy(NpcCharacter self) {
		this.self = self;
	}

	@Override
	// go left and right
	public void update(float delta, Character player) {
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

		sb.append("strategy");
		sb.append(" --> ");
		sb.append("passive");
		sb.append("\n");

		return sb.toString();
	}

}
