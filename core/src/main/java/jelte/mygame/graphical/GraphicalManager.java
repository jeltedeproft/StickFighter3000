package jelte.mygame.graphical;

import jelte.mygame.Message;

public interface GraphicalManager {
	public void update(float delta);

	public void resize(int width, int height);

	public void dispose();

	void receiveMessage(Message message);

}
