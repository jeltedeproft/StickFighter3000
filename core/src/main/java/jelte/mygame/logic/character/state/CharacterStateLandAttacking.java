package jelte.mygame.logic.character.state;import com.badlogic.gdx.utils.StringBuilder;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;

public class CharacterStateLandAttacking implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float timer = 0f;
	private float duration;
	private CHARACTER_STATE state = CHARACTER_STATE.LANDATTACKING;

	public CharacterStateLandAttacking(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		timer = duration;
		this.duration = duration;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_ATTACK);
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
		case DAMAGE_TAKEN:
			characterStateManager.transition(CHARACTER_STATE.HURT);
			break;
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
