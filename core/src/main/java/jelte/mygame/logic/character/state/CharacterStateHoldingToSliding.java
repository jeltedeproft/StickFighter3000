package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;

public class CharacterStateHoldingToSliding implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float duration;
	private float timer = 0f;
	private CHARACTER_STATE state = CHARACTER_STATE.HOLDINGTOSLIDING;

	public CharacterStateHoldingToSliding(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		this.duration = duration;
		timer = duration;
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
			characterStateManager.pushState(CHARACTER_STATE.WALLSLIDING);
		}
	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case DAMAGE_TAKEN:
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.FALLING);
			characterStateManager.pushState(CHARACTER_STATE.HURT);
			break;
		default:
			break;
		}
	}

	@Override
	public void handleInput(InputBox inputBox) {
		switch (inputBox.getLastUsedButton()) {
		case UP:
			if (inputBox.isPressed(BUTTONS.UP)) {
				characterStateManager.popState();
				characterStateManager.pushState(CHARACTER_STATE.JUMPING);
			}
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

		sb.append("in");
		sb.append(state.name());
		sb.append("for");
		sb.append(timer);
		sb.append("seconds");

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

}
