package jelte.mygame.logic.character.state;

import com.badlogic.gdx.physics.box2d.Body;

import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;

public class CharacterStateFalling implements CharacterState {
	private CharacterStateManager characterStateManager;
	private STATE state = STATE.FALLING;

	public CharacterStateFalling(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float delta, Body body) {
		if (Math.abs(body.getLinearVelocity().y) < 1) {
			characterStateManager.transition(STATE.LANDING);
		}
	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
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
