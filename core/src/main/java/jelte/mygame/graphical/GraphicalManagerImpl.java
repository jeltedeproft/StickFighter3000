package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import jelte.mygame.MessageListener;

public class GraphicalManagerImpl implements GraphicalManager {
	private SpriteBatch batch;
	private MessageListener listener;

	public GraphicalManagerImpl(MessageListener listener) {
		this.listener = listener;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

}
