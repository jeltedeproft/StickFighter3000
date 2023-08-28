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

public class CharacterStateSprinting implements CharacterState {
	private CharacterStateManager characterStateManager;
	private CHARACTER_STATE state = CHARACTER_STATE.SPRINTING;

	public CharacterStateSprinting(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_SPRINTING, characterStateManager.getCharacter().getPhysicsComponent());
		boolean rightPressed = characterStateManager.getCharacter().getCharacterInputHandler().getInputBox().isPressed(BUTTONS.RIGHT);
		boolean leftPressed = characterStateManager.getCharacter().getCharacterInputHandler().getInputBox().isPressed(BUTTONS.LEFT);
		boolean upPressed = characterStateManager.getCharacter().getCharacterInputHandler().getInputBox().isPressed(BUTTONS.UP);
		boolean downPressed = characterStateManager.getCharacter().getCharacterInputHandler().getInputBox().isPressed(BUTTONS.DOWN);
		if (upPressed) {
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.JUMPING);
		}
		if (leftPressed && !rightPressed) {
			characterStateManager.startMovingOnTheGround(-Constants.SPRINT_SPEED);
		}
		if (!leftPressed && rightPressed) {
			characterStateManager.startMovingOnTheGround(Constants.SPRINT_SPEED);
		}
		if (downPressed) {
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.CROUCHED);
		}
	}

	@Override
	public void update(float delta) {
		InputBox inputBox = characterStateManager.getCharacter().getCharacterInputHandler().getInputBox();
		boolean rightPressed = characterStateManager.getCharacter().getCharacterInputHandler().getInputBox().isPressed(BUTTONS.RIGHT);
		boolean leftPressed = characterStateManager.getCharacter().getCharacterInputHandler().getInputBox().isPressed(BUTTONS.LEFT);
		if (characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0 && characterStateManager.getCharacter().getCharacterInputHandler().getInputBox().noMovementKeyPressed()) {
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.IDLE);
		}
		if (leftPressed && !rightPressed) {
			characterStateManager.continueMovingOnTheGround(-Constants.SPRINT_SPEED);
		}
		if (!leftPressed && rightPressed) {
			characterStateManager.continueMovingOnTheGround(Constants.SPRINT_SPEED);
		}
		if (characterStateManager.getCharacter().getCharacterInputHandler().getInputBox().noMovementKeyPressed()) {
			characterStateManager.stopMovingOnTheGround();
		}
		if (!inputBox.isPressed(BUTTONS.SPRINT)) {
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.WALKING);
		}
	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case DAMAGE_TAKEN:
			characterStateManager.pushState(CHARACTER_STATE.HURT);
			break;
		case NO_COLLISION:
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.FALLING);
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
				characterStateManager.pushState(CHARACTER_STATE.BLOCKING);
			}
			break;
		case DASH:
			if (inputBox.isPressed(BUTTONS.DASH)) {
				characterStateManager.pushState(CHARACTER_STATE.DASHING);
			}
			break;
		case DOWN:
			if (inputBox.isPressed(BUTTONS.DOWN)) {
				characterStateManager.pushState(CHARACTER_STATE.CROUCHED);
			}
			break;
		case ROLL:
			if (inputBox.isPressed(BUTTONS.ROLL)) {
				characterStateManager.pushState(CHARACTER_STATE.ROLLING);
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
		case UP:
			if (inputBox.isPressed(BUTTONS.UP)) {
				characterStateManager.pushState(CHARACTER_STATE.JUMPING);
			}
			break;
		default:
			break;

		}
	}

	@Override
	public void pauze() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_STOP, AudioEnum.SOUND_WALK);

	}

	@Override
	public void resume() {
		InputBox inputBox = characterStateManager.getCharacter().getCharacterInputHandler().getInputBox();
		if (inputBox.isPressed(BUTTONS.RIGHT) || inputBox.isPressed(BUTTONS.LEFT)) {
			MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_WALK, characterStateManager.getCharacter().getPhysicsComponent());
		} else {
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.IDLE);
		}

	}

	@Override
	public void exit() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_STOP, AudioEnum.SOUND_SPRINTING);
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
