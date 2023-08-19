package jelte.mygame.tests.input;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.Input;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.input.InputHandlerImpl;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
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
		assertTrue(inputHandler.getInputBox().isPressed(BUTTONS.LEFT));

		inputHandler.keyDown(rightKey);
		assertTrue(inputHandler.getInputBox().isPressed(BUTTONS.RIGHT));

		inputHandler.keyDown(downKey);
		assertTrue(inputHandler.getInputBox().isPressed(BUTTONS.DOWN));

		inputHandler.keyDown(sprintKey);
		assertTrue(inputHandler.getInputBox().isPressed(BUTTONS.SPRINT));

		inputHandler.keyDown(blockKey);
		assertTrue(inputHandler.getInputBox().isPressed(BUTTONS.BLOCK));

		inputHandler.keyDown(spell0Key);
		assertTrue(inputHandler.getInputBox().isPressed(BUTTONS.SPELL0));
		verify(mockListener, times(6)).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP));
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
		assertFalse(inputHandler.getInputBox().isPressed(BUTTONS.LEFT));

		inputHandler.keyUp(rightKey);
		assertFalse(inputHandler.getInputBox().isPressed(BUTTONS.RIGHT));

		inputHandler.keyUp(downKey);
		assertFalse(inputHandler.getInputBox().isPressed(BUTTONS.DOWN));

		inputHandler.keyUp(sprintKey);
		assertFalse(inputHandler.getInputBox().isPressed(BUTTONS.SPRINT));

		inputHandler.keyUp(blockKey);
		assertFalse(inputHandler.getInputBox().isPressed(BUTTONS.BLOCK));

		inputHandler.keyUp(spell0Key);
		assertFalse(inputHandler.getInputBox().isPressed(BUTTONS.SPELL0));
		verify(mockListener, times(6)).receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP, 2));// TODO remove this number, we have a button for every spell
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
