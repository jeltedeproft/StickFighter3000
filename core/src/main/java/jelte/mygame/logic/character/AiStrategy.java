package jelte.mygame.logic.character;

import jelte.mygame.Message;

public interface AiStrategy {

	public void update(float delta, Character player);

	public void sendMessage(Message message);

}
