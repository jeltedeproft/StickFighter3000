package jelte.mygame.logic.character.state;

import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;

public class CharacterStateJumping implements CharacterState {
	private CharacterStateManager characterStateManager;
	private STATE state = STATE.JUMPING;

	public CharacterStateJumping(CharacterStateManager characterStateManager) {
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
		case LANDED:
			characterStateManager.transition(STATE.IDLE);
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

}
