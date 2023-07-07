package jelte.mygame.logic.ai.stateControllers;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.utility.AiUtility;

public class FleeStateController implements StateControllerInterface {
	private static final float FLEE_SAFETY_DISTANCE = 200f;

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		if (self.getPhysicsComponent().getPosition().dst(player.getPhysicsComponent().getPosition()) >= FLEE_SAFETY_DISTANCE) {
			return AI_STATE.PATROL;
		}
		return AI_STATE.FLEE;
	}

	@Override
	public Array<Message> getNextCommandsFromThisState(float delta, AiCharacter self, PlayerCharacter player) {
		return AiUtility.generateMoveInputToGoal(self, new Position(-player.getPhysicsComponent().getPosition().x);
	}

}
