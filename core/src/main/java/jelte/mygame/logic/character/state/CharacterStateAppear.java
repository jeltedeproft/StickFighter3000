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

public class CharacterStateAppear implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float timer = 0f;
	private CHARACTER_STATE state = CHARACTER_STATE.APPEARING;
	private float duration;

	public CharacterStateAppear(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		timer = duration;
		this.duration = duration;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_APPEAR, characterStateManager.getCharacter().getPhysicsComponent());
	}

	@Override
	public void update(float delta) {
		timer -= delta;
		if (timer <= 0) {
			timer = duration;
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.IDLE);
		}
	}

	@Override
	public void pauze() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case DAMAGE_TAKEN:
			characterStateManager.popState();
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
				characterStateManager.startMovingOnTheGround(-Constants.WALK_SPEED);
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.WALKING);
			} else {
				characterStateManager.stopMovingOnTheGround();
			}
			break;
		case RIGHT:
			if (inputBox.isPressed(BUTTONS.RIGHT)) {
				characterStateManager.startMovingOnTheGround(Constants.WALK_SPEED);
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
		timer = duration;
	}

	@Override
	public CHARACTER_STATE getState() {
		return state;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("in ");
		sb.append(state.name());
		sb.append(" for ");
		sb.append(timer);
		sb.append(" more seconds ");

		return sb.toString();
	}

}
