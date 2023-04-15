package jelte.mygame.logic.character.state;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManager.AudioCommand;
import jelte.mygame.graphical.audio.MusicManager.AudioEnum;
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
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_FALL1);
	}

	@Override
	public void update(float delta) {
		if (Math.abs(characterStateManager.getCharacter().getMovementVector().y) == 0) {
			characterStateManager.transition(STATE.LANDING);
		}
	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case JUMP_PRESSED:
			characterStateManager.transition(STATE.JUMPING);
			break;
		case LEFT_PRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = -Constants.MOVEMENT_SPEED;
			characterStateManager.getCharacter().setCurrentDirection(Direction.left);
			break;
		case LEFT_UNPRESSED:
		case RIGHT_UNPRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = 0;
			characterStateManager.getCharacter().setMovementVector(new Vector2(0, 0));
			break;
		case RIGHT_PRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = Constants.MOVEMENT_SPEED;
			characterStateManager.getCharacter().setCurrentDirection(Direction.right);
			break;
		case TELEPORT_PRESSED:
			characterStateManager.transition(STATE.TELEPORTING);
			break;
		case DASH_PRESSED:
			characterStateManager.transition(STATE.DASHING);
			break;
		default:
			break;
		}
	}

	@Override
	public void exit() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_STOP, AudioEnum.SOUND_FALL1);
	}

	@Override
	public STATE getState() {
		return state;
	}

}
