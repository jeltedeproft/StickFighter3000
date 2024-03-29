package jelte.mygame.logic.ai.strategy.advanced.stateControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.StateControllerInterface;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.utility.AiUtility;
import jelte.mygame.utility.Constants;

public class AdvancedPatrolStateController implements StateControllerInterface {
	private static final String TAG = AdvancedPatrolStateController.class.getSimpleName();
	private float timeSinceLastPatrol = 0f;

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		timeSinceLastPatrol += delta;
		if (self.getVisionCollidable().isPlayerSeen() && timeSinceLastPatrol > 3) {
			Gdx.app.log(TAG, "switching from patrol to chase");
			timeSinceLastPatrol = 0;
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
