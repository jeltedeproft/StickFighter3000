package jelte.mygame.logic.ai.stateControllers;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;

public interface StateControllerInterface {

	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player);

	public Array<Message> getNextCommandsFromThisState(float delta, AiCharacter self, PlayerCharacter player);

}
