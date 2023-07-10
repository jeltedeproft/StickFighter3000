package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.character.Direction;
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
			characterStateManager.transition(CHARACTER_STATE.FALLING);
		}
	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case JUMP_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.JUMPING);
			break;
		case LEFT_PRESSED:
			characterStateManager.accelerateCharacterX(Direction.left, Constants.MOVEMENT_SPEED);
			break;
		case LEFT_UNPRESSED, RIGHT_UNPRESSED:
			characterStateManager.stopCharacter();
			break;
		case RIGHT_PRESSED:
			characterStateManager.accelerateCharacterX(Direction.right, Constants.MOVEMENT_SPEED);
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

}
