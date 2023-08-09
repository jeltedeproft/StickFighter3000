package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.utility.Constants;

public class CharacterStateRunning implements CharacterState {
	private CharacterStateManager characterStateManager;
	private CHARACTER_STATE state = CHARACTER_STATE.RUNNING;

	public CharacterStateRunning(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_WALK);
	}

	@Override
	public void update(float delta) {

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
		case LEFT_UNPRESSED, RIGHT_UNPRESSED:
			characterStateManager.stopCharacter();
			if (characterStateManager.characterisStandingStill()) {
				characterStateManager.transition(CHARACTER_STATE.STOPRUNNING);
			}
			break;
		case RIGHT_PRESSED:
			characterStateManager.accelerateCharacterX(Direction.right, Constants.MOVEMENT_SPEED);
			break;
		case DOWN_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.CROUCHED);
			break;
		case NO_COLLISION:
			characterStateManager.transition(CHARACTER_STATE.FALLING);
			break;
		case TELEPORT_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.TELEPORTING);
			break;
		case DASH_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.DASHING);
			break;
		case ROLL_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.ROLLING);
			break;
		case BLOCK_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.BLOCKING);
			break;
		case SPRINT_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.SPRINTING);
			break;
		case CAST_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.PRECAST);
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
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.IDLE);
				characterStateManager.pushState(CHARACTER_STATE.ATTACKING);
			}
			break;
		case BLOCK:
			if (inputBox.isPressed(BUTTONS.BLOCK)) {
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.IDLE);
				characterStateManager.pushState(CHARACTER_STATE.BLOCKING);
			}
			break;
		case DASH:
			if (inputBox.isPressed(BUTTONS.DASH)) {
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.DASHING);
			}
			break;
		case DOWN:
			if (inputBox.isPressed(BUTTONS.DOWN)) {
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.CROUCHED);
			}
			break;
		case LEFT:
			if (inputBox.isPressed(BUTTONS.LEFT)) {
				characterStateManager.startMovingOnTheGround(Constants.WALK_SPEED, false);
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.WALKING);
			} else {
				characterStateManager.stopMovingOnTheGround();
			}
			break;
		case RIGHT:
			if (inputBox.isPressed(BUTTONS.RIGHT)) {
				characterStateManager.startMovingOnTheGround(Constants.WALK_SPEED, true);
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.WALKING);
			} else {
				characterStateManager.stopMovingOnTheGround();
			}
			break;
		case ROLL:
			if (inputBox.isPressed(BUTTONS.ROLL)) {
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.IDLE);
				characterStateManager.pushState(CHARACTER_STATE.ROLLING);
			}
			break;
		case SPELL0:
			if (inputBox.isPressed(BUTTONS.SPELL0)) {
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.IDLE);
				characterStateManager.pushState(CHARACTER_STATE.PRECAST);
			}
			break;
		case SPRINT:
			if (inputBox.isPressed(BUTTONS.SPRINT)) {
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.SPRINTING);
			}
			break;
		case TELEPORT:
			if (inputBox.isPressed(BUTTONS.TELEPORT)) {
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.IDLE);
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
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_STOP, AudioEnum.SOUND_WALK);
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
