package jelte.mygame.logic.character.state;

import jelte.mygame.logic.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.Constants;

public class CharacterStateRunning implements CharacterState {
	private CharacterStateManager characterStateManager;
	private STATE state = STATE.RUNNING;

	public CharacterStateRunning(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case ATTACK_PRESSED:
			characterStateManager.transition(STATE.ATTACK);
			break;
		case DAMAGE_TAKEN:
			characterStateManager.transition(STATE.HURT);
			break;
		case JUMP_PRESSED:
			characterStateManager.transition(STATE.JUMPING);
			break;
		case LEFT_PRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = -Constants.MOVEMENT_SPEED;
			characterStateManager.getCharacter().setCurrentDirection(Direction.left);
			break;
		case LEFT_UNPRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = 0;
			if (characterStateManager.getCharacter().getMovementVector().epsilonEquals(0, 0)) {
				characterStateManager.transition(STATE.STOPRUNNING);
			}
			break;
		case RIGHT_PRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = Constants.MOVEMENT_SPEED;
			System.out.println("RUNNING : right pressed, acceleration = " + characterStateManager.getCharacter().getAccelerationVector());
			characterStateManager.getCharacter().setCurrentDirection(Direction.right);
			break;
		case RIGHT_UNPRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = 0;
			if (characterStateManager.getCharacter().getMovementVector().epsilonEquals(0, 0)) {
				characterStateManager.transition(STATE.STOPRUNNING);
			}
			break;
		case DOWN_PRESSED:
			characterStateManager.transition(STATE.CROUCHED);
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
