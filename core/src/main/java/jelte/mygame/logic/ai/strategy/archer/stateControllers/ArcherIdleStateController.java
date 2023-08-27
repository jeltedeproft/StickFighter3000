package jelte.mygame.logic.ai.strategy.archer.stateControllers;

import com.badlogic.gdx.Gdx;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.StateControllerInterface;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;

public class ArcherIdleStateController implements StateControllerInterface {
	private static final String TAG = ArcherIdleStateController.class.getSimpleName();
	private float timer = 0f;
	private float maxIdleTime = 4;

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		timer += delta;
		if (timer >= maxIdleTime && self.getVisionCollidable().isPlayerSeen() && self.getCharacterStateManager().getCurrentCharacterState().getState() != CHARACTER_STATE.FALLING) {
			timer = 0f;
			Gdx.app.log(TAG, "switching from idle to attack");
			return AI_STATE.ATTACK;
		}
		Gdx.app.log(TAG, "stay in idle state");
		return AI_STATE.IDLE;
	}

	@Override
	public Message getNextCommand(float delta, AiCharacter self, PlayerCharacter player) {
		return null;
	}

}
