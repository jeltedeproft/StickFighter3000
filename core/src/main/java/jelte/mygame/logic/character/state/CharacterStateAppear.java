package jelte.mygame.logic.character.state;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.character.Direction;
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
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_APPEAR);
	}

	@Override
	public void update(float delta) {
		timer -= delta;
		if (timer <= 0) {
			timer = duration;
			characterStateManager.transition(CHARACTER_STATE.IDLE);
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
			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = -Constants.MOVEMENT_SPEED;
			characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
			characterStateManager.transition(CHARACTER_STATE.WALKING);
			break;
		case LEFT_UNPRESSED:
			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = 0;
			break;
		case RIGHT_PRESSED:
			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = Constants.MOVEMENT_SPEED;
			characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.right);
			characterStateManager.transition(CHARACTER_STATE.WALKING);
			break;
		case RIGHT_UNPRESSED:
			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = 0;
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
		case CAST_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.PRECAST);
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
