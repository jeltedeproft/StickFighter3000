package jelte.mygame.logic.character.state;

import jelte.mygame.logic.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.Constants;

public class CharacterStateFalling implements CharacterState {
	private CharacterStateManager characterStateManager;
	private STATE state = STATE.FALLING;

	public CharacterStateFalling(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float delta) {
		if (Math.abs(characterStateManager.getCharacter().getMovementVector().y) < 0.1) {
			characterStateManager.transition(STATE.LANDING);
		}
	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case LEFT_PRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = -Constants.MOVEMENT_SPEED;
			characterStateManager.getCharacter().setCurrentDirection(Direction.left);
			break;
		case LEFT_UNPRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = 0;
			break;
		case RIGHT_PRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = Constants.MOVEMENT_SPEED;
			characterStateManager.getCharacter().setCurrentDirection(Direction.right);
			break;
		case RIGHT_UNPRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = 0;
			break;
		default:
			break;
		}
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

	@Override
	public STATE getState() {
		return state;
	}

}
