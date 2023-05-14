package jelte.mygame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.IntSet;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.input.KeyBindings.MyKeys;
import jelte.mygame.utility.Constants;

public class InputHandlerImpl implements InputHandler, InputProcessor {
	private static final String TAG = InputHandlerImpl.class.getSimpleName();
	private MessageListener listener;
	private final IntSet downKeys = new IntSet(Constants.MAX_DOWNKEYS);
	private InputMultiplexer inputMultiplexer;

	public InputHandlerImpl(MessageListener listener) {
		this.listener = listener;
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case SEND_STAGE:
			inputMultiplexer.addProcessor((Stage) message.getValue());
			break;
		default:
			break;

		}
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int key = button == Buttons.LEFT ? MyKeys.LEFT_MOUSE
				: MyKeys.RIGHT_MOUSE;
		return keyDown(key);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		int key = button == Buttons.LEFT ? MyKeys.LEFT_MOUSE
				: MyKeys.RIGHT_MOUSE;
		return keyUp(key);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == KeyBindings.getBinding(Constants.LEFT)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.LEFT_PRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.RIGHT)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_PRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.UP)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.UP_PRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.DOWN)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.DOWN_PRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.ATTACK)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.ATTACK_PRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.KEY_SPRINT)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SPRINT_PRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.KEY_BLOCK)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.BLOCK_PRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.KEY_DASH)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.DASH_PRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.KEY_ROLL)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.ROLL_PRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.KEY_TELEPORT)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.TELEPORT_PRESSED));
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == KeyBindings.getBinding(Constants.LEFT)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.LEFT_UNPRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.RIGHT)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_UNPRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.DOWN)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.DOWN_UNPRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.KEY_SPRINT)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SPRINT_UNPRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.KEY_BLOCK)) {
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.BLOCK_UNPRESSED));
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.CAMERA_ZOOM, amountY));
		return true;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return downKeys.toString();
	}

}
