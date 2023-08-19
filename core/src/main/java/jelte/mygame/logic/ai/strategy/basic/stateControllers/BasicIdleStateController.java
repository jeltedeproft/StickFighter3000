package jelte.mygame.logic.ai.strategy.basic.stateControllers;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.StateControllerInterface;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;

public class BasicIdleStateController implements StateControllerInterface {
	private float timer = 0f;
	private float maxIdleTime = 4f;

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		timer += delta;
		if (self.getVisionCollidable().isPlayerSeen()) {
			return AI_STATE.CHASE;
		}
		if (timer >= maxIdleTime) {
			return AI_STATE.PATROL;
		}
		return AI_STATE.IDLE;
	}

	@Override
	public Message getNextCommand(float delta, AiCharacter self, PlayerCharacter player) {
		return null;
	}

}
