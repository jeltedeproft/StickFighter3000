package jelte.mygame.logic.ai.strategy.advanced.stateControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.StateControllerInterface;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.utility.AiUtility;

public class AdvancedFleeStateController implements StateControllerInterface {
	private static final String TAG = AdvancedFleeStateController.class.getSimpleName();
	private static final float FLEE_SAFETY_DISTANCE = 200f;

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		if (self.getPhysicsComponent().getPosition().dst(player.getPhysicsComponent().getPosition()) >= FLEE_SAFETY_DISTANCE) {
			Gdx.app.log(TAG, "switching from flee to patrol");
			return AI_STATE.PATROL;
		}
		return AI_STATE.FLEE;
	}

	@Override
	public Message getNextCommand(float delta, AiCharacter self, PlayerCharacter player) {
		return AiUtility.generateMoveInputAwayFromGoal(self, new Vector2(player.getPhysicsComponent().getPosition().x, player.getPhysicsComponent().getPosition().y));
	}

}
