package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.utility.Constants;

public class CharacterStateJumpToFall implements CharacterState {
	private CharacterStateManager characterStateManager;

	private CHARACTER_STATE state = CHARACTER_STATE.JUMPTOFALL;

	public CharacterStateJumpToFall(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {

	}

	@Override
	public void update(float delta) {
		if (characterStateManager.characterisFalling()) {
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.FALLING);
		}
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
		case ATTACK:
			if (inputBox.isPressed(BUTTONS.ATTACK)) {
				characterStateManager.pushState(CHARACTER_STATE.ATTACKING);
			}
			break;
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
		case LEFT:
			if (inputBox.isPressed(BUTTONS.LEFT)) {
				characterStateManager.startMovingInTheAir(Constants.FALL_MOVEMENT_SPEED, false);
			} else {
				characterStateManager.stopMovingInTheAir();
			}
			break;
		case RIGHT:
			if (inputBox.isPressed(BUTTONS.RIGHT)) {
				characterStateManager.startMovingInTheAir(Constants.FALL_MOVEMENT_SPEED, true);
			} else {
				characterStateManager.stopMovingInTheAir();
			}
			break;
		case SPELL0:
			if (inputBox.isPressed(BUTTONS.SPELL0)) {
				characterStateManager.pushState(CHARACTER_STATE.PRECAST);
			}
			break;
		case TELEPORT:
			if (inputBox.isPressed(BUTTONS.TELEPORT)) {
				characterStateManager.pushState(CHARACTER_STATE.TELEPORTING);
			}
			break;
		default:
			break;

		}
	}

	@Override
	public void exit() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_STOP, AudioEnum.SOUND_FALL);
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
