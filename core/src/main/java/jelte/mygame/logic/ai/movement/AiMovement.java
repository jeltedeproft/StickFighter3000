package jelte.mygame.logic.ai.movement;

import jelte.mygame.Message;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;

public interface AiMovement {

	public Message generateMovementCommand(AiCharacter self, PlayerCharacter player);

}
