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
	private InputBox inputBox;

	public enum BUTTONS {
		LEFT,
		RIGHT,
		UP,
		DOWN,
		ATTACK,
		SPRINT,
		BLOCK,
		DASH,
		ROLL,
		TELEPORT,
		SPELL0;
	}

	public InputHandlerImpl(MessageListener listener) {
		this.listener = listener;
		inputBox = new InputBox();
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(inputMultiplexer);
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
		if (keycode == KeyBindings.getBinding(Constants.EXIT)) {
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.EXIT_GAME));
		} else {
			inputBox.updateButtonPressed(keycode, true);
			listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP, inputBox));
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		inputBox.updateButtonPressed(keycode, false);
		listener.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP, inputBox));
		return true;
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.CAMERA_ZOOM, amountY));
		return true;
	}

	@Override
	public void dispose() {

	}

	@Override
	public String toString() {
		return downKeys.toString();
	}

}
