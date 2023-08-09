package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.input.InputBox;
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
			characterStateManager.popState();
		}
		characterStateManager.startMovingOnTheGround(Constants.ROLL_SPEED, characterStateManager.getCharacter().getPhysicsComponent().getDirection() == Direction.right);
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
