package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.utility.Constants;

public class CharacterStateIdle implements CharacterState {
	private CharacterStateManager characterStateManager;
	private CHARACTER_STATE state = CHARACTER_STATE.IDLE;

	public CharacterStateIdle(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		for (EVENT event : characterStateManager.getPressedKeysBuffer()) {
			handleEvent(event);
		}
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case ATTACK_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.ATTACKING);
			break;
		case DAMAGE_TAKEN:
			characterStateManager.transition(CHARACTER_STATE.HURT);
			break;
		case JUMP_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.JUMPING);
			break;
		case LEFT_PRESSED:
			characterStateManager.accelerateCharacterX(Direction.left, Constants.MOVEMENT_SPEED);
			characterStateManager.transition(CHARACTER_STATE.WALKING);
			break;
		case RIGHT_UNPRESSED, LEFT_UNPRESSED:
			characterStateManager.stopCharacter();
			break;
		case RIGHT_PRESSED:
			characterStateManager.accelerateCharacterX(Direction.right, Constants.MOVEMENT_SPEED);
			characterStateManager.transition(CHARACTER_STATE.WALKING);
			break;
		case DOWN_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.CROUCHED);
			break;
		case NO_COLLISION:
			characterStateManager.transition(CHARACTER_STATE.FALLING);
			break;
		case TELEPORT_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.TELEPORTING);
			break;
		case DASH_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.DASHING);
			break;
		case ROLL_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.ROLLING);
			break;
		case BLOCK_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.BLOCKING);
			break;
		case CAST_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.PRECAST);
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
	public CHARACTER_STATE getState() {
		return state;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("in");
		sb.append(state.name());

		return sb.toString();
	}

}
