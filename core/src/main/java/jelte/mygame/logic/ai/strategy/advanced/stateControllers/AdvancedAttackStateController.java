package jelte.mygame.logic.ai.strategy.advanced.stateControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.StateControllerInterface;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;

public class AdvancedAttackStateController implements StateControllerInterface {
	private static final String TAG = AdvancedAttackStateController.class.getSimpleName();
	private float timeSinceLastAttack = 0f;

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		timeSinceLastAttack += delta;
		if (self.getCurrentCharacterState().getState() != CHARACTER_STATE.ATTACKING && timeSinceLastAttack > 3) {
			timeSinceLastAttack = 0;
			Gdx.app.log(TAG, "switching from attack to chase");
			return AI_STATE.CHASE;
		}
		return AI_STATE.ATTACK;
	}

	@Override
	public Array<Message> getNextCommands(float delta, AiCharacter self, PlayerCharacter player) {
		return null;
	}

}
