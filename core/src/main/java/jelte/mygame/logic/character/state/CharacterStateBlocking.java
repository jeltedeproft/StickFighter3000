package jelte.mygame.logic.character.state;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManager.AudioCommand;
import jelte.mygame.graphical.audio.MusicManager.AudioEnum;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;

public class CharacterStateBlocking implements CharacterState {
	private CharacterStateManager characterStateManager;
	private STATE state = STATE.BLOCKING;
	private float timer = 0f;
	private float duration;

	public CharacterStateBlocking(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		timer = duration;
		this.duration = duration;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_SHIELD1);
		characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = 0;
		characterStateManager.getCharacter().getPhysicsComponent().setVelocity(new Vector2(0, 0));
	}

	@Override
	public void update(float delta) {
		timer -= delta;
		if (timer <= 0) {
			timer = duration;
			characterStateManager.transition(STATE.IDLE);
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
		case TELEPORT_PRESSED:
			characterStateManager.transition(STATE.TELEPORTING);
			break;
		case DASH_PRESSED:
			characterStateManager.transition(STATE.DASHING);
			break;
		case ROLL_PRESSED:
			characterStateManager.transition(STATE.ROLLING);
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
	public STATE getState() {
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
