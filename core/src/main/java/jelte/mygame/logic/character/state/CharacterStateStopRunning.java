package jelte.mygame.logic.character.state;

import jelte.mygame.logic.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.Constants;

public class CharacterStateStopRunning implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float timer = 0f;
	private STATE state = STATE.STOPRUNNING;
	private float duration;

	public CharacterStateStopRunning(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		timer = duration;
		this.duration = duration;
	}

	@Override
	public void entry() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float delta) {
		timer -= delta;
		if (timer <= 0) {
			characterStateManager.transition(STATE.IDLE);
		}
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
			characterStateManager.getCharacter().getMovementVector().add(-Constants.MOVEMENT_SPEED, 0);
			characterStateManager.getCharacter().setCurrentDirection(Direction.left);
			characterStateManager.transition(STATE.RUNNING);
			break;
		case LEFT_UNPRESSED:
			characterStateManager.getCharacter().getMovementVector().add(Constants.MOVEMENT_SPEED, 0);
			break;
		case RIGHT_PRESSED:
			characterStateManager.getCharacter().getMovementVector().add(Constants.MOVEMENT_SPEED, 0);
			characterStateManager.getCharacter().setCurrentDirection(Direction.right);
			characterStateManager.transition(STATE.RUNNING);
			break;
		case RIGHT_UNPRESSED:
			characterStateManager.getCharacter().getMovementVector().add(-Constants.MOVEMENT_SPEED, 0);
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
