package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.utility.Constants;

public class CharacterStateHolding implements CharacterState {
	private CharacterStateManager characterStateManager;
	private CHARACTER_STATE state = CHARACTER_STATE.HOLDING;

	public CharacterStateHolding(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		characterStateManager.grabLedge();

	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case DAMAGE_TAKEN:
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.FALLING);
			characterStateManager.pushState(CHARACTER_STATE.HURT);
			break;
		default:
			break;
		}
	}

	@Override
	public void handleInput(InputBox inputBox) {
		switch (inputBox.getLastUsedButton()) {
		case BLOCK:
			if (inputBox.isPressed(BUTTONS.BLOCK)) {
				characterStateManager.pushState(CHARACTER_STATE.BLOCKING);
			}
			break;
		case DASH:
			if (inputBox.isPressed(BUTTONS.DASH)) {
				characterStateManager.pushState(CHARACTER_STATE.DASHING);
			}
			break;
		case DOWN:
			if (inputBox.isPressed(BUTTONS.DOWN)) {
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.HOLDINGTOSLIDING);
			}
			break;
		case LEFT:
			if (inputBox.isPressed(BUTTONS.LEFT)) {
				characterStateManager.startMovingInTheAir(-Constants.FALL_MOVEMENT_SPEED);
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.FALLING);
			}
			break;
		case RIGHT:
			if (inputBox.isPressed(BUTTONS.RIGHT)) {
				characterStateManager.startMovingInTheAir(Constants.FALL_MOVEMENT_SPEED);
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.FALLING);
			}
			break;
		case TELEPORT:
			if (inputBox.isPressed(BUTTONS.TELEPORT)) {
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.TELEPORTING);
			}
			break;
		case UP:
			if (inputBox.isPressed(BUTTONS.UP)) {
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.JUMPING);
			}
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

	@Override
	public void pauze() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
