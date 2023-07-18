package jelte.mygame.logic.ai.strategy.basic.stateControllers;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.StateControllerInterface;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.utility.AiUtility;
import jelte.mygame.utility.Constants;

public class BasicPatrolStateController implements StateControllerInterface {

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		if (self.getVisionCollidable().isPlayerSeen()) {
			return AI_STATE.CHASE;
		}
		return AI_STATE.PATROL;
	}

	@Override
	public Array<Message> getNextCommands(float delta, AiCharacter self, PlayerCharacter player) {
		if (self.getPhysicsComponent().getPosition().dst(self.getPatrolPoints().get(self.getActivePatrolPointIndex()).getPosition()) <= Constants.CONTROL_POINT_REACHED_BUFFER_DISTANCE) {
			self.incrementPatrolPointIndex();
		}
		return AiUtility.generateMoveInputToGoal(self, self.getPatrolPoints().get(self.getActivePatrolPointIndex()).getPosition());
	}

}
