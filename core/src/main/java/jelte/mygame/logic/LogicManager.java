package jelte.mygame.logic;

import jelte.mygame.Message;

public interface LogicManager {

	public void update(float delta);

	public void dispose();

	void receiveMessage(Message message);

}
