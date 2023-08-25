package jelte.mygame.logic.ai.strategy.archer.stateControllers;

import com.badlogic.gdx.Gdx;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.StateControllerInterface;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;

public class ArcherAttackStateController implements StateControllerInterface {
	private static final String TAG = ArcherAttackStateController.class.getSimpleName();
	private float timeSinceLastAttack = 0f;

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		timeSinceLastAttack += delta;
		if (self.getCurrentCharacterState().getState() != CHARACTER_STATE.ATTACKING && timeSinceLastAttack > self.getData().getAttackCooldown()) {
			timeSinceLastAttack = 0;
			Gdx.app.log(TAG, "switching from attack to chase");
			return AI_STATE.IDLE;
		}
		return AI_STATE.ATTACK;
	}

	@Override
	public Message getNextCommand(float delta, AiCharacter self, PlayerCharacter player) {
		return null;
	}

}
