package jelte.mygame.tests.input;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.badlogic.gdx.Input;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.input.InputHandlerImpl;
import jelte.mygame.input.KeyBindings;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;

@RunWith(GdxTestRunner.class)
public class TestInputHandlerImpl {

	private MessageListener mockListener;
	private InputHandlerImpl inputHandler;

	@Before
	public void setup() {
		mockListener = Mockito.mock(MessageListener.class);
		inputHandler = new InputHandlerImpl(mockListener);
	}

	@Test
	public void testKeyBindings() {
		int leftKey = KeyBindings.getBinding(Constants.LEFT);
		int rightKey = KeyBindings.getBinding(Constants.RIGHT);
		int downKey = KeyBindings.getBinding(Constants.DOWN);
		int sprintKey = KeyBindings.getBinding(Constants.KEY_SPRINT);
		int blockKey = KeyBindings.getBinding(Constants.KEY_BLOCK);
		int spell0Key = KeyBindings.getBinding(Constants.KEY_SPELL0);

		inputHandler.keyDown(leftKey);
		verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.LEFT_PRESSED));

		inputHandler.keyDown(rightKey);
		verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_PRESSED));

		inputHandler.keyDown(downKey);
		verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.DOWN_PRESSED));

		inputHandler.keyDown(sprintKey);
		verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SPRINT_PRESSED));

		inputHandler.keyDown(blockKey);
		verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.BLOCK_PRESSED));

		inputHandler.keyDown(spell0Key);
		verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.CAST_PRESSED, 2));
	}

	@Test
	public void testKeyBindingsUnpressed() {
		int leftKey = KeyBindings.getBinding(Constants.LEFT);
		int rightKey = KeyBindings.getBinding(Constants.RIGHT);
		int downKey = KeyBindings.getBinding(Constants.DOWN);
		int sprintKey = KeyBindings.getBinding(Constants.KEY_SPRINT);
		int blockKey = KeyBindings.getBinding(Constants.KEY_BLOCK);
		int spell0Key = KeyBindings.getBinding(Constants.KEY_SPELL0);

		inputHandler.keyUp(leftKey);
		verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.LEFT_UNPRESSED));

		inputHandler.keyUp(rightKey);
		verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_UNPRESSED));

		inputHandler.keyUp(downKey);
		verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.DOWN_UNPRESSED));

		inputHandler.keyUp(sprintKey);
		verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SPRINT_UNPRESSED));

		inputHandler.keyUp(blockKey);
		verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.BLOCK_UNPRESSED));

		inputHandler.keyUp(spell0Key);
		verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.CAST_RELEASED, 1));
	}

	@Test
	public void testTouchDown() {
		int screenX = 100;
		int screenY = 100;

		inputHandler.touchDown(screenX, screenY, 0, Input.Buttons.LEFT);
		// verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.LEFT_MOUSE_PRESSED));

		inputHandler.touchDown(screenX, screenY, 0, Input.Buttons.RIGHT);
		// verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_MOUSE_PRESSED));
	}

	@Test
	public void testTouchUp() {
		int screenX = 100;
		int screenY = 100;

		inputHandler.touchUp(screenX, screenY, 0, Input.Buttons.LEFT);
		// verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.LEFT_MOUSE_RELEASED));

		inputHandler.touchUp(screenX, screenY, 0, Input.Buttons.RIGHT);
		// verify(mockListener).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_MOUSE_RELEASED));
	}

	@Test
	public void testScrolled() {
		float amountX = 0.0f;
		float amountY = 1.0f;

		inputHandler.scrolled(amountX, amountY);
		verify(mockListener).receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.CAMERA_ZOOM, amountY));
	}

	// Additional test cases can be added as needed

}
