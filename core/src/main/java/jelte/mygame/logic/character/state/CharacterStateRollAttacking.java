package jelte.mygame.logic.character.state;

import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.utility.Constants;

public class CharacterStateRollAttacking implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float timer = 0f;
	private float duration;
	private CHARACTER_STATE state = CHARACTER_STATE.ROLLATTACK;

	public CharacterStateRollAttacking(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		timer = duration;
		this.duration = duration;
	}

	@Override
	public void entry() {
		// TODO Auto-generated method stub

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
		case DAMAGE_TAKEN:
			characterStateManager.transition(CHARACTER_STATE.HURT);
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
