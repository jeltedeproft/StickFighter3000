package jelte.mygame.logic.ai;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
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

	public Array<Message> generateCommands(float delta, AiCharacter self, PlayerCharacter player);

}
