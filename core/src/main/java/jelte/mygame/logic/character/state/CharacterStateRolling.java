package jelte.mygame.logic.character.state;import com.badlogic.gdx.utils.StringBuilder;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.utility.Constants;

public class CharacterStateRolling implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float duration;
	private float timer = 0f;
	private CHARACTER_STATE state = CHARACTER_STATE.ROLLING;

	public CharacterStateRolling(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		this.duration = duration;
		timer = duration;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_ROLL);
	}

	@Override
	public void update(float delta) {
		timer -= delta;
		if (timer <= 0) {
			timer = duration;
			if (Math.abs(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x) > 0) {
				characterStateManager.transition(CHARACTER_STATE.RUNNING);
			} else {
				characterStateManager.transition(CHARACTER_STATE.IDLE);
			}

		}
		if (characterStateManager.getCharacter().getPhysicsComponent().getDirection().equals(Direction.right)) {
			characterStateManager.getCharacter().getPhysicsComponent().move(Constants.ROLL_SPEED * delta, 0);
		} else {
			characterStateManager.getCharacter().getPhysicsComponent().move(-Constants.ROLL_SPEED * delta, 0);
		}
	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case RIGHT_UNPRESSED:
		case LEFT_UNPRESSED:
			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = 0;
			characterStateManager.getCharacter().getPhysicsComponent().setVelocity(new Vector2(0, 0));
			break;
		case ATTACK_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.ROLLATTACK);
			break;
		default:
			break;

		}
	}

	@Override
	public void exit() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_STOP, AudioEnum.SOUND_ROLL);
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
