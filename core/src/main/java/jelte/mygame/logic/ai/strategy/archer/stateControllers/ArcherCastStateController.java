package jelte.mygame.logic.ai.strategy.archer.stateControllers;

import com.badlogic.gdx.Gdx;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.StateControllerInterface;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;

public class ArcherCastStateController implements StateControllerInterface {
	private static final String TAG = ArcherCastStateController.class.getSimpleName();

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		if (self.getCurrentCharacterState().getState() != CHARACTER_STATE.CAST) {
			Gdx.app.log(TAG, "switching from vzdt to chase");
			return AI_STATE.CHASE;
		}
		return AI_STATE.CAST;
	}

	@Override
	public Message getNextCommand(float delta, AiCharacter self, PlayerCharacter player) {
		return null;
	}

}
