package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.input.InputBox;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.utility.Constants;

public class CharacterStateTeleporting implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float timer = 0f;
	private CHARACTER_STATE state = CHARACTER_STATE.TELEPORTING;
	private float duration;

	public CharacterStateTeleporting(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		timer = duration;
		this.duration = duration;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT, characterStateManager.getCharacter().getPhysicsComponent());
		boolean isRight = characterStateManager.getCharacter().getPhysicsComponent().getDirection() == Direction.right;
		characterStateManager.applyHorizontalForce(isRight ? Constants.TELEPORT_DISTANCE : -Constants.TELEPORT_DISTANCE);

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
		// TODO Auto-generated method stub

	}

	@Override
	public void handleInput(InputBox inputBox) {
		// TODO Auto-generated method stub

	}

}
