package jelte.mygame.logic.ai.strategy.archer.stateControllers;

import com.badlogic.gdx.Gdx;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.StateControllerInterface;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.utility.AiUtility;

public class ArcherChaseStateController implements StateControllerInterface {
	private static final String TAG = ArcherChaseStateController.class.getSimpleName();
	private static final float FLEE_HP = 15f;

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		if (self.getPhysicsComponent().getPosition().dst(player.getPhysicsComponent().getPosition()) <= self.getData().getAttackrange()) {
			Gdx.app.log(TAG, "switching from chase to attack");
			return AI_STATE.ATTACK;
		}
		if (self.getCurrentHp() < FLEE_HP) {
			Gdx.app.log(TAG, "switching from chase to flee");
			return AI_STATE.FLEE;
		}
		Gdx.app.log(TAG, "stay in chase state");
		return AI_STATE.CHASE;
	}

	@Override
	public Message getNextCommand(float delta, AiCharacter self, PlayerCharacter player) {
		return AiUtility.generateMoveInputToGoal(self, player.getPhysicsComponent().getPosition());
	}

}