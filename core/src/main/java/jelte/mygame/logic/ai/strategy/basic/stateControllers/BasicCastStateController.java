package jelte.mygame.logic.ai.strategy.basic.stateControllers;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.StateControllerInterface;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;

public class BasicCastStateController implements StateControllerInterface {

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		if (self.getCurrentCharacterState().getState() != CHARACTER_STATE.CAST) {
			return AI_STATE.CHASE;
		}
		return AI_STATE.CAST;
	}

	@Override
	public Message getNextCommand(float delta, AiCharacter self, PlayerCharacter player) {
		return null;
	}

}
