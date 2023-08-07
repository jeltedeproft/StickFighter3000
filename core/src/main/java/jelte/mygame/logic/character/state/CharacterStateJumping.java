package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.collisions.collidable.Collidable.COLLIDABLE_TYPE;
import jelte.mygame.utility.Constants;

public class CharacterStateJumping implements CharacterState {
	private CharacterStateManager characterStateManager;
	private CHARACTER_STATE state = CHARACTER_STATE.JUMPING;
	private boolean jumpPressed = false;

	public CharacterStateJumping(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		jumpPressed = true;
		characterStateManager.startJump();
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_JUMP);
	}

	@Override
	public void update(float delta) {
		if (jumpPressed) {
			characterStateManager.applyJumpForce();
		}
		Array<COLLIDABLE_TYPE> collidedWith = characterStateManager.getCharacterCollisions();
		if (collidedWithCorner(collidedWith)) {
			characterStateManager.transition(CHARACTER_STATE.GRABBING);
		} else if (collidedWithWall(collidedWith)) {
			characterStateManager.transition(CHARACTER_STATE.HOLDING);
		} else if (characterStateManager.characterIsAtHighestPoint()) {
			characterStateManager.transition(CHARACTER_STATE.JUMPTOFALL);
		}
	}

	private boolean collidedWithWall(Array<COLLIDABLE_TYPE> collidedWith) {
		return collidedWith.contains(COLLIDABLE_TYPE.STATIC_LEFT, false) || collidedWith.contains(COLLIDABLE_TYPE.STATIC_RIGHT, false);
	}

	private boolean collidedWithCorner(Array<COLLIDABLE_TYPE> collidedWith) {
		return collidedWith.contains(COLLIDABLE_TYPE.STATIC_LEFT, false) && collidedWith.contains(COLLIDABLE_TYPE.STATIC_TOP, false) || collidedWith.contains(COLLIDABLE_TYPE.STATIC_RIGHT, false) && collidedWith.contains(COLLIDABLE_TYPE.STATIC_TOP, false);
	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case JUMP_UNPRESSED:
			jumpPressed = false;
			break;
		case LEFT_PRESSED:
			characterStateManager.startMovingInTheAir(Constants.FALL_MOVEMENT_SPEED, false);
			break;
		case LEFT_UNPRESSED, RIGHT_UNPRESSED:
			characterStateManager.stopMovingInTheAir();
			break;
		case RIGHT_PRESSED:
			characterStateManager.startMovingInTheAir(Constants.FALL_MOVEMENT_SPEED, true);
			break;
		case TELEPORT_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.TELEPORTING);
			break;
		case DASH_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.DASHING);
			break;
		case CAST_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.PRECAST);
			break;
		case ATTACK_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.ATTACKING);
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
