package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.collisions.collidable.Collidable.COLLIDABLE_TYPE;
import jelte.mygame.utility.Constants;

public class CharacterStateWallSliding implements CharacterState {
	private CharacterStateManager characterStateManager;
	private CHARACTER_STATE state = CHARACTER_STATE.WALLSLIDING;

	public CharacterStateWallSliding(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_SLIDE);
		characterStateManager.pullDown(10);// TODO change in speed here, used to be 5 velocity and 10 acceleration
	}

	@Override
	public void update(float delta) {
		Array<COLLIDABLE_TYPE> collidedWith = characterStateManager.getCharacterCollisions();
		if (characterStateManager.characterHaslanded() && collidedWith.contains(COLLIDABLE_TYPE.STATIC_BOT, false)) {
			characterStateManager.transition(CHARACTER_STATE.LANDING);
		}
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
			break;
		case RIGHT_UNPRESSED, LEFT_UNPRESSED:
			characterStateManager.stopCharacter();
			break;
		case RIGHT_PRESSED:
			characterStateManager.accelerateCharacterX(Direction.right, Constants.MOVEMENT_SPEED);
			break;
		case DOWN_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.CROUCHED);
			break;
		default:
			break;

		}
	}

	@Override
	public void exit() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_STOP, AudioEnum.SOUND_SLIDE);
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
