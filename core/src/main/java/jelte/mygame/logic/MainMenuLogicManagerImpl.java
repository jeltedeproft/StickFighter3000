package jelte.mygame.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import jelte.mygame.Message;
import jelte.mygame.MessageListener;

public class MainMenuLogicManagerImpl implements LogicManager {
	private static final String TAG = MainMenuLogicManagerImpl.class.getSimpleName();
	private MessageListener listener;
	private Vector2 mousePosition = new Vector2();

	public MainMenuLogicManagerImpl(MessageListener listener) {
		this.listener = listener;
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case SEND_MOUSE_COORDINATES -> {
			Vector3 mouseVector = (Vector3) message.getValue();
			mousePosition.x = mouseVector.x;
			mousePosition.y = mouseVector.y;
		}
		default -> {}

		}
	}

	public Vector2 getMousePosition() {
		return mousePosition;
	}

	@Override
	public void dispose() {

	}

}
