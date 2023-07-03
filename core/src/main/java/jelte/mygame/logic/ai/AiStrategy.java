package jelte.mygame.logic.ai;

import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;

public interface AiStrategy {

	public enum AI_STATE {
		IDLE,
		PATROL,
		CHASE,
		ATTACK,
		CAST;
	}

	public enum AI_COMMAND {
		MOVE_RIGHT,
		MOVE_LEFT,
		ATTACK,
		CAST,
		JUMP,
		STOP_MOVE;
	}

	public AI_COMMAND decideNextMove(float delta, AiCharacter self, PlayerCharacter player);

}
