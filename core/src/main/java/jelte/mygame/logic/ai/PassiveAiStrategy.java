package jelte.mygame.logic.ai;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;

public class PassiveAiStrategy extends AbstractAiStrategy {
	private float timeSinceLastCommand = 0f;

	public PassiveAiStrategy(AiCharacter self) {
		super(self);
	}

	@Override
	protected void updateIdleState(float delta, PlayerCharacter player) {
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
	protected void updatePatrolState(float delta, PlayerCharacter player) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void updateChaseState(float delta, PlayerCharacter player) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void updateCastState(float delta, PlayerCharacter player) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void updateAttackState(float delta, PlayerCharacter player) {
		// TODO Auto-generated method stub
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("passive strategy");
		return sb.toString();
	}

}
