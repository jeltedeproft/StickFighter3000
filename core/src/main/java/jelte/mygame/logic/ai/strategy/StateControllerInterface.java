package jelte.mygame.logic.ai.strategy;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;

public interface StateControllerInterface {

	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player);

	public Message getNextCommand(float delta, AiCharacter self, PlayerCharacter player);

}
