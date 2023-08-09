package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
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
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.GRABBING);
		} else if (collidedWithWall(collidedWith)) {
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.HOLDING);
		} else if (characterStateManager.characterIsAtHighestPoint()) {
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.JUMPTOFALL);
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
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.FALLING);
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
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.TELEPORTING);
			}
			break;
		case UP:
			if (!inputBox.isPressed(BUTTONS.UP)) {
				jumpPressed = false;
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
