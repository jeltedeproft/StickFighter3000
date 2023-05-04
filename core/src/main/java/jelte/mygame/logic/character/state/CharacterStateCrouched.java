package jelte.mygame.logic.character.state;

import jelte.mygame.logic.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.Constants;

public class CharacterStateCrouched implements CharacterState {
	private CharacterStateManager characterStateManager;
	private STATE state = STATE.CROUCHED;

	public CharacterStateCrouched(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		characterStateManager.getCharacter().getPhysicsComponent().setFallTrough(true);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case DOWN_UNPRESSED:
			characterStateManager.transition(STATE.IDLE);
			break;
		case LEFT_PRESSED:
			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = -Constants.MOVEMENT_SPEED_CROUCHED;
			characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
			break;
		case LEFT_UNPRESSED:
			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = 0;
			break;
		case RIGHT_PRESSED:
			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = Constants.MOVEMENT_SPEED_CROUCHED;
			characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.right);
			break;
		case RIGHT_UNPRESSED:
			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = 0;
			break;
		case NO_COLLISION:
			characterStateManager.transition(STATE.FALLING);
			break;
		default:
			break;

		}
	}

	@Override
	public void exit() {
		characterStateManager.getCharacter().getPhysicsComponent().setFallTrough(false);
	}

	@Override
	public STATE getState() {
		return state;
	}

}
