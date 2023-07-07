package jelte.mygame.logic.ai.stateControllers;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;

public class IdleStateController implements StateControllerInterface {
	private float timer = 0f;
	private float maxIdleTime = 4f;

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		timer += delta;
		if (self.isPlayerSeen()) {
			return AI_STATE.CHASE;
		}
		if (timer >= maxIdleTime) {
			return AI_STATE.PATROL;
		}
		return AI_STATE.IDLE;
	}

	@Override
	public Array<Message> getNextCommandsFromThisState(float delta, AiCharacter self, PlayerCharacter player) {
		return null;
	}

}
