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

public class CharacterStateFalling implements CharacterState {
	private CharacterStateManager characterStateManager;
	private CHARACTER_STATE state = CHARACTER_STATE.FALLING;

	public CharacterStateFalling(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_FALL, characterStateManager.getCharacter().getPhysicsComponent());
	}

	@Override
	public void update(float delta) {
		Array<COLLIDABLE_TYPE> collidedWith = characterStateManager.getCharacterCollisions();
		if (collidedWithCorner(collidedWith)) {
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.GRABBING);
		} else if (collidedWithWall(collidedWith)) {
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.HOLDING);
		} else if (characterStateManager.characterHaslanded() && collidedWith.contains(COLLIDABLE_TYPE.STATIC_BOT, false) || collidedWith.contains(COLLIDABLE_TYPE.STATIC_PLATFORM, false) && !characterStateManager.characterIsFalltrough()) {
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.LANDING);
		}

		boolean rightPressed = characterStateManager.getCharacter().getCharacterInputHandler().getInputBox().isPressed(BUTTONS.RIGHT);
		boolean leftPressed = characterStateManager.getCharacter().getCharacterInputHandler().getInputBox().isPressed(BUTTONS.LEFT);
		if (leftPressed && !rightPressed) {
			characterStateManager.startMovingInTheAir(-Constants.FALL_MOVEMENT_SPEED);
		}
		if (!leftPressed && rightPressed) {
			characterStateManager.startMovingInTheAir(Constants.FALL_MOVEMENT_SPEED);
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
			characterStateManager.pushState(CHARACTER_STATE.HURT);
			break;
		default:
			break;
		}
	}

	@Override
	public void handleInput(InputBox inputBox) {
		if (inputBox.getLastUsedButton() != null) {
			switch (inputBox.getLastUsedButton()) {
			case ATTACK:
				if (inputBox.isPressed(BUTTONS.ATTACK)) {
					characterStateManager.popState();
					characterStateManager.pushState(CHARACTER_STATE.FALLATTACKING);
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
					characterStateManager.startMovingInTheAir(-Constants.FALL_MOVEMENT_SPEED);
				} else {
					characterStateManager.stopMovingInTheAir();
				}
				break;
			case RIGHT:
				if (inputBox.isPressed(BUTTONS.RIGHT)) {
					characterStateManager.startMovingInTheAir(Constants.FALL_MOVEMENT_SPEED);
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
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_STOP, AudioEnum.SOUND_FALL);
	}

	@Override
	public void resume() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_FALL, characterStateManager.getCharacter().getPhysicsComponent());
	}
}