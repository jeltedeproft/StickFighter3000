package jelte.mygame.logic;

import jelte.mygame.Message;
import jelte.mygame.MessageListener;

public class LogicManagerImpl implements LogicManager {
	private MessageListener listener;

	public LogicManagerImpl(MessageListener listener) {
		this.listener = listener;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveMessage(Message message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
