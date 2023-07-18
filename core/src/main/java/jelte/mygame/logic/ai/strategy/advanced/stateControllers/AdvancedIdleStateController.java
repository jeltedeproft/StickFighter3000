package jelte.mygame.logic.ai.strategy.advanced.stateControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.StateControllerInterface;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;

public class AdvancedIdleStateController implements StateControllerInterface {
	private static final String TAG = AdvancedIdleStateController.class.getSimpleName();
	private float timer = 0f;
	private float maxIdleTime = 4;

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		timer += delta;
		if (self.getVisionCollidable().isPlayerSeen()) {
			timer = 0f;
			Gdx.app.log(TAG, "switching from idle to chase");
			return AI_STATE.CHASE;
		}
		if (timer >= maxIdleTime) {
			timer = 0f;
			Gdx.app.log(TAG, "switching from idle to patrol");
			return AI_STATE.PATROL;
		}
		return AI_STATE.IDLE;
	}

	@Override
	public Array<Message> getNextCommands(float delta, AiCharacter self, PlayerCharacter player) {
		return null;
	}

}
