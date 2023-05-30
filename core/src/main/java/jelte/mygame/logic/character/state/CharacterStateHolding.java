package jelte.mygame.logic.character.state;import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;

public class CharacterStateHolding implements CharacterState {
	private CharacterStateManager characterStateManager;
	private CHARACTER_STATE state = CHARACTER_STATE.HOLDING;

	public CharacterStateHolding(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case DAMAGE_TAKEN:
			characterStateManager.transition(CHARACTER_STATE.HURT);
			break;
		case JUMP_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.JUMPING);
			break;
		case DOWN_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.WALLSLIDING);
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
