package jelte.mygame.input;

import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.utility.Constants;
import lombok.Getter;

@Getter
public class InputBox {
	private boolean[] buttonPressed = new boolean[BUTTONS.values().length];
	private BUTTONS lastUsedButton;

	public boolean isPressed(BUTTONS button) {
		return buttonPressed[button.ordinal()];
	}

	public void updateButtonPressed(BUTTONS button, boolean isPressed) {
		buttonPressed[button.ordinal()] = isPressed;
	}

	public void updateButtonPressed(int keycode, boolean isPressed) {
		int buttonIndex = getButtonIndexForKeycode(keycode);
		if (buttonIndex != -1) {
			buttonPressed[buttonIndex] = isPressed;
		}
	}

	private int getButtonIndexForKeycode(int keycode) {
		if (keycode == KeyBindings.getBinding(Constants.LEFT)) {
			lastUsedButton = BUTTONS.LEFT;
			return BUTTONS.LEFT.ordinal();
		}
		if (keycode == KeyBindings.getBinding(Constants.RIGHT)) {
			lastUsedButton = BUTTONS.RIGHT;
			return BUTTONS.RIGHT.ordinal();
		}
		if (keycode == KeyBindings.getBinding(Constants.UP)) {
			lastUsedButton = BUTTONS.UP;
			return BUTTONS.UP.ordinal();
		}
		if (keycode == KeyBindings.getBinding(Constants.DOWN)) {
			lastUsedButton = BUTTONS.DOWN;
			return BUTTONS.DOWN.ordinal();
		}
		if (keycode == KeyBindings.getBinding(Constants.ATTACK)) {
			lastUsedButton = BUTTONS.ATTACK;
			return BUTTONS.ATTACK.ordinal();
		}
		if (keycode == KeyBindings.getBinding(Constants.KEY_SPRINT)) {
			lastUsedButton = BUTTONS.SPRINT;
			return BUTTONS.SPRINT.ordinal();
		}
		if (keycode == KeyBindings.getBinding(Constants.KEY_BLOCK)) {
			lastUsedButton = BUTTONS.BLOCK;
			return BUTTONS.BLOCK.ordinal();
		}
		if (keycode == KeyBindings.getBinding(Constants.KEY_DASH)) {
			lastUsedButton = BUTTONS.DASH;
			return BUTTONS.DASH.ordinal();
		}
		if (keycode == KeyBindings.getBinding(Constants.KEY_ROLL)) {
			lastUsedButton = BUTTONS.ROLL;
			return BUTTONS.ROLL.ordinal();
		}
		if (keycode == KeyBindings.getBinding(Constants.KEY_TELEPORT)) {
			lastUsedButton = BUTTONS.TELEPORT;
			return BUTTONS.TELEPORT.ordinal();
		}
		if (keycode == KeyBindings.getBinding(Constants.KEY_SPELL0)) {
			lastUsedButton = BUTTONS.SPELL0;
			return BUTTONS.SPELL0.ordinal();
		}
		return -1;
	}

}
