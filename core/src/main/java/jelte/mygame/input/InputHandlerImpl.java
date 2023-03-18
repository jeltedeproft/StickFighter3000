package jelte.mygame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.IntSet;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.utility.Constants;

public class InputHandlerImpl implements InputHandler, InputProcessor {
	private static final String TAG = InputHandlerImpl.class.getSimpleName();
	private MessageListener listener;
	private final IntSet downKeys = new IntSet(Constants.MAX_DOWNKEYS);

	public InputHandlerImpl(MessageListener listener) {
		this.listener = listener;
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void receiveMessage(Message message) {
		// TODO Auto-generated method stub

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
		if (keycode == KeyBindings.getBinding(Constants.CAMERA_LEFT_KEY)) {
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.CAMERA_LEFT));
		}
		if (keycode == KeyBindings.getBinding(Constants.CAMERA_RIGHT_KEY)) {
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.CAMERA_RIGHT));
		}
		if (keycode == KeyBindings.getBinding(Constants.CAMERA_UP_KEY)) {
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.CAMERA_UP));
		}
		if (keycode == KeyBindings.getBinding(Constants.CAMERA_DOWN_KEY)) {
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.CAMERA_DOWN));
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
		if (keycode == KeyBindings.getBinding(Constants.CAMERA_LEFT_KEY)) {
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.CAMERA_LEFT_UNPRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.CAMERA_RIGHT_KEY)) {
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.CAMERA_RIGHT_UNPRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.CAMERA_UP_KEY)) {
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.CAMERA_UP_UNPRESSED));
		}
		if (keycode == KeyBindings.getBinding(Constants.CAMERA_DOWN_KEY)) {
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.CAMERA_DOWN_UNPRESSED));
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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

}
