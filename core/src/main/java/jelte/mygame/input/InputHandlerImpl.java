package jelte.mygame.input;

import jelte.mygame.MessageListener;

public class InputHandlerImpl implements InputHandler {
	private MessageListener listener;

	public InputHandlerImpl(MessageListener listener) {
		this.listener = listener;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

}
