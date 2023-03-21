package jelte.mygame.logic.character.state;

import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;

public class CharacterStateIdle implements CharacterState {
	private CharacterStateManager characterStateManager;
	private STATE state = STATE.IDLE;

	public CharacterStateIdle(CharacterStateManager characterStateManager) {
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
		case ATTACK_PRESSED:
			characterStateManager.transition(STATE.ATTACK);
			break;
		case DAMAGE_TAKEN:
			characterStateManager.transition(STATE.HURT);
			break;
		case JUMP_PRESSED:
			characterStateManager.transition(STATE.JUMPING);
			break;
		case MOVE_PRESSED:
			characterStateManager.transition(STATE.RUNNING);
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
