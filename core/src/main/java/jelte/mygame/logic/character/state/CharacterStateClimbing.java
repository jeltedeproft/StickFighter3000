package jelte.mygame.logic.character.state;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManager.AudioCommand;
import jelte.mygame.graphical.audio.MusicManager.AudioEnum;
import jelte.mygame.logic.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.Constants;

public class CharacterStateClimbing implements CharacterState {
	private CharacterStateManager characterStateManager;
	private STATE state = STATE.CLIMBING;

	public CharacterStateClimbing(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_CLIMB1);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case ATTACK_PRESSED:
			characterStateManager.transition(STATE.ATTACKING);
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
			characterStateManager.transition(STATE.RUNNING);
			break;
		case RIGHT_UNPRESSED:
		case LEFT_UNPRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = 0;
			characterStateManager.getCharacter().setMovementVector(new Vector2(0, 0));
			break;
		case RIGHT_PRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = Constants.MOVEMENT_SPEED;
			System.out.println("IDLE : right pressed, acceleration = " + characterStateManager.getCharacter().getAccelerationVector());
			characterStateManager.getCharacter().setCurrentDirection(Direction.right);
			characterStateManager.transition(STATE.RUNNING);
			break;
		case DOWN_PRESSED:
			characterStateManager.transition(STATE.CROUCHED);
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
		// TODO Auto-generated method stub

	}

	@Override
	public STATE getState() {
		return state;
	}

}