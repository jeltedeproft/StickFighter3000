package jelte.mygame.input;

import jelte.mygame.Message;

public interface InputHandler {

	public void update(float delta);

	public void dispose();

	void receiveMessage(Message message);

}
