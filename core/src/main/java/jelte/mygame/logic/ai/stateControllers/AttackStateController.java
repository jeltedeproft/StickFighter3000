package jelte.mygame.logic.ai.stateControllers;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;

public class AttackStateController implements StateControllerInterface {

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		if (self.getCurrentCharacterState().getState() != CHARACTER_STATE.ATTACKING) {
			return AI_STATE.CHASE;
		}
		return AI_STATE.ATTACK;
	}

	@Override
	public Array<Message> getNextCommandsFromThisState(float delta, AiCharacter self, PlayerCharacter player) {
		return null;
	}

}
