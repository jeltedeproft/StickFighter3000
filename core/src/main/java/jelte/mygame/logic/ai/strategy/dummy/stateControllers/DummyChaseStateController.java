package jelte.mygame.logic.ai.strategy.dummy.stateControllers;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.StateControllerInterface;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;

public class DummyChaseStateController implements StateControllerInterface {
	private static final String TAG = DummyChaseStateController.class.getSimpleName();
	private static final float FLEE_HP = 15f;

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		return AI_STATE.IDLE;

	}

	@Override
	public Message getNextCommand(float delta, AiCharacter self, PlayerCharacter player) {
		return null;
	}

}
