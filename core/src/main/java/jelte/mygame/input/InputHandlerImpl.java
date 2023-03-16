package jelte.mygame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;

public class InputHandlerImpl implements InputHandler, InputProcessor {
	private MessageListener listener;

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
	    switch (keycode){
		case Keys.LEFT:
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC,ACTION.CAMERA_MOVE_X,-5));
			break;
		case Keys.RIGHT:
			listener.receiveMessage(new Message(RECIPIENT.GRAPHIC,ACTION.CAMERA_MOVE_X,5));
			break;
	    }
	    return true;
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
