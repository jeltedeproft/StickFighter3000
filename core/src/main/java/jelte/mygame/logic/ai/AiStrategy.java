package jelte.mygame.logic.ai;

import jelte.mygame.Message;
import jelte.mygame.logic.character.PlayerCharacter;

public interface AiStrategy {

	public void update(float delta, PlayerCharacter player, AiState state);

	public void sendMessage(Message message);

}
