package jelte.mygame.logic.character.state;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.Constants;

public class CharacterStateWallSlidingStop implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float duration;
	private float timer = 0f;
	private STATE state = STATE.WALLSLIDINGSTOP;

	public CharacterStateWallSlidingStop(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		this.duration = duration;
		timer = duration;
	}

	@Override
	public void entry() {

	}

	@Override
	public void update(float delta) {
		timer -= delta;
		if (timer <= 0) {
			timer = duration;
			characterStateManager.transition(STATE.HOLDING);
		}
	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case ATTACK_PRESSED:
			characterStateManager.transition(STATE.ATTACKING);
			break;
		case DAMAGE_TAKEN:
			characterStateManager.transition(STATE.HURT);
			break;
		case JUMP_PRESSED:
			characterStateManager.transition(STATE.JUMPING);
			break;
		case LEFT_PRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = -Constants.MOVEMENT_SPEED;
			characterStateManager.getCharacter().setCurrentDirection(Direction.left);
			break;
		case RIGHT_UNPRESSED:
		case LEFT_UNPRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = 0;
			characterStateManager.getCharacter().setMovementVector(new Vector2(0, 0));
			break;
		case RIGHT_PRESSED:
			characterStateManager.getCharacter().getAccelerationVector().x = Constants.MOVEMENT_SPEED;
			characterStateManager.getCharacter().setCurrentDirection(Direction.right);
			break;
		case DOWN_PRESSED:
			characterStateManager.transition(STATE.CROUCHED);
			break;
		case NO_COLLISION:
			characterStateManager.transition(STATE.FALLING);
			break;
		default:
			break;

		}
	}

	@Override
	public void exit() {

	}

	@Override
	public STATE getState() {
		return state;
	}

}
