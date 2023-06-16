package jelte.mygame.logic.ai;

import jelte.mygame.Message;
import jelte.mygame.logic.character.Character;

public interface AiStrategy {

	public void update(float delta, Character player, AiState state);

	public void sendMessage(Message message);

}
