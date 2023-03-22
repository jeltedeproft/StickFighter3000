package jelte.mygame.logic.character.state;

import com.badlogic.gdx.physics.box2d.Body;

import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;

public class CharacterStateAppear implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float timer = 0f;
	private STATE state = STATE.APPEARING;
	private float duration;

	public CharacterStateAppear(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		timer = duration;
		this.duration = duration;
	}

	@Override
	public void entry() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float delta, Body body) {
		timer -= delta;
		if (timer <= 0) {
			characterStateManager.transition(STATE.IDLE);
		}
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
