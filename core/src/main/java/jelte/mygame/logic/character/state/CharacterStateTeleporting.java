package jelte.mygame.logic.character.state;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManager.AudioCommand;
import jelte.mygame.graphical.audio.MusicManager.AudioEnum;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.Constants;

public class CharacterStateTeleporting implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float timer = 0f;
	private STATE state = STATE.TELEPORTING;
	private float duration;

	public CharacterStateTeleporting(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		timer = duration;
		this.duration = duration;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT1);
		Direction direction = characterStateManager.getCharacter().getPhysicsComponent().getDirection();
		if (direction.equals(Direction.right)) {
			characterStateManager.getCharacter().getPhysicsComponent().move(Constants.TELEPORT_DISTANCE, 0);
		} else {
			characterStateManager.getCharacter().getPhysicsComponent().move(-Constants.TELEPORT_DISTANCE, 0);
		}
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
		default:
			break;

		}
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

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
