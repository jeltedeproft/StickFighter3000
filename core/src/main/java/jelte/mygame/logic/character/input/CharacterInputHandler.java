package jelte.mygame.logic.character.input;

import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterInputHandler {
	private InputBox inputBox = new InputBox();

	public BUTTONS getLastPressedButton() {
		return inputBox.getLastUsedButton();
	}

}
