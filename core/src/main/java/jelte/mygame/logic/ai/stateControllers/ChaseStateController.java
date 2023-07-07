package jelte.mygame.logic.ai.stateControllers;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.utility.AiUtility;

public class ChaseStateController implements StateControllerInterface {

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		if (self.getPhysicsComponent().getPosition().dst(player.getPhysicsComponent().getPosition()) <= self.getData().getAttackrange()) {
			return AI_STATE.ATTACK;
		}
		return AI_STATE.CHASE;
	}

	@Override
	public Array<Message> getNextCommandsFromThisState(float delta, AiCharacter self, PlayerCharacter player) {
		return AiUtility.generateMoveInputToGoal(self, player.getPhysicsComponent().getPosition());
	}

}
