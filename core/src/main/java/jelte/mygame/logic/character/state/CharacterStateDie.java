package jelte.mygame.logic.character.state;import com.badlogic.gdx.utils.StringBuilder;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;

public class CharacterStateDie implements CharacterState {
	private CharacterStateManager characterStateManager;
	private CHARACTER_STATE state = CHARACTER_STATE.DIE;

	public CharacterStateDie(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_DEATH);
		characterStateManager.getCharacter().getPhysicsComponent().setVelocity(new Vector2(0, 0));
		characterStateManager.getCharacter().getPhysicsComponent().setAcceleration(new Vector2(0, 0));
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(EVENT event) {
		// final state
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
}
