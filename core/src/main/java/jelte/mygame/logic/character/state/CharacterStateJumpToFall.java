package jelte.mygame.logic.character.state;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManager.AudioCommand;
import jelte.mygame.graphical.audio.MusicManager.AudioEnum;
import jelte.mygame.logic.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.Constants;

public class CharacterStateJumpToFall implements CharacterState {
	private CharacterStateManager characterStateManager;

	private STATE state = STATE.JUMPTOFALL;

	public CharacterStateJumpToFall(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {

	}

	@Override
	public void update(float delta) {
		if (characterStateManager.getCharacter().getMovementVector().y * delta < -0.1) {
			characterStateManager.transition(STATE.FALLING);
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