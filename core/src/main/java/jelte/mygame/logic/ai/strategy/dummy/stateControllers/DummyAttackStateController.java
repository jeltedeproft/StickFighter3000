package jelte.mygame.logic.ai.strategy.dummy.stateControllers;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.StateControllerInterface;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;

public class DummyAttackStateController implements StateControllerInterface {
	private static final String TAG = DummyAttackStateController.class.getSimpleName();
	private float timeSinceLastAttack = 0f;

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		return AI_STATE.IDLE;

	}

	@Override
	public Message getNextCommand(float delta, AiCharacter self, PlayerCharacter player) {
		return null;
	}

}
