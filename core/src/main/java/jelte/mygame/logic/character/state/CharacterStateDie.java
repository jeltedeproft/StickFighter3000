package jelte.mygame.logic.character.state;

import com.badlogic.gdx.physics.box2d.Body;

import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;

public class CharacterStateDie implements CharacterState {
	private CharacterStateManager characterStateManager;
	private STATE state = STATE.DIE;

	public CharacterStateDie(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float delta, Body body) {
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
	public STATE getState() {
		return state;
	}
}
