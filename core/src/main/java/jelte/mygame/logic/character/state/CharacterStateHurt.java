package jelte.mygame.logic.character.state;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;

public class CharacterStateHurt implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float duration;
	private float timer = 0f;
	private CHARACTER_STATE state = CHARACTER_STATE.HURT;

	public CharacterStateHurt(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		this.duration = duration;
		timer = duration;
	}

	@Override
	public void entry() {
		characterStateManager.getCharacter().getPhysicsComponent().setVelocity(new Vector2(0, 0));
		characterStateManager.getCharacter().getPhysicsComponent().setAcceleration(new Vector2(0, 0));
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
			timer = duration;
			break;
		case DIED:
			characterStateManager.transition(CHARACTER_STATE.DIE);
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
