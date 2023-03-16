package jelte.mygame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.IntSet;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.utility.Variables;

//TODO : go over all posibilities key presses
public class InputHandlerImpl implements InputHandler, InputProcessor {
	private MessageListener listener;
	private final IntSet downKeys = new IntSet(Variables.MAX_DOWNKEYS);

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
		downKeys.add(keycode);
		if (downKeys.size >= 2) {
			checkMovementMultipleKeysDown();
		} else {
			checkMovementSingleKeyDown(keycode);
		}

		return true;
	}

	private void checkMovementSingleKeyDown(int keyCode) {
		if (keyCode == KeyBindings.getBinding(Variables.LEFT)) {
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.CAMERA_MOVE_LEFT));
		}
		if (keyCode == KeyBindings.getBinding(Variables.RIGHT)) {
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.CAMERA_MOVE_RIGHT));
		}
		if (keyCode == KeyBindings.getBinding(Variables.UP)) {
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.HERO_JUMP));
		}
		if (keyCode == KeyBindings.getBinding(Variables.DOWN)) {
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.HERO_DUCK));
		}
	}

	private void checkMovementMultipleKeysDown() {
		if ((downKeys.size == 2) && downKeys.contains(KeyBindings.getBinding(Variables.LEFT)) && downKeys.contains(KeyBindings.getBinding(Variables.UP))) {
			client.sendMoveRequest(Direction.topleft);
		}
		if ((downKeys.size == 2) && downKeys.contains(KeyBindings.getBinding(Variables.LEFT)) && downKeys.contains(KeyBindings.getBinding(Variables.DOWN))) {
			client.sendMoveRequest(Direction.bottomleft);
		}
		if ((downKeys.size == 2) && downKeys.contains(KeyBindings.getBinding(Variables.RIGHT)) && downKeys.contains(KeyBindings.getBinding(Variables.UP))) {
			client.sendMoveRequest(Direction.topright);
		}
		if ((downKeys.size == 2) && downKeys.contains(KeyBindings.getBinding(Variables.RIGHT)) && downKeys.contains(KeyBindings.getBinding(Variables.DOWN))) {
			client.sendMoveRequest(Direction.bottomright);
		}
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
