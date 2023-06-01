package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.utility.Constants;

public class CharacterStateCrouched implements CharacterState {
	private CharacterStateManager characterStateManager;
	private CHARACTER_STATE state = CHARACTER_STATE.CROUCHED;

	public CharacterStateCrouched(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		characterStateManager.getCharacter().getPhysicsComponent().setFallTrough(true);
		characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y -= 50;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case DOWN_UNPRESSED:
			characterStateManager.transition(CHARACTER_STATE.IDLE);
			break;
		case LEFT_PRESSED:
			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = -Constants.MOVEMENT_SPEED_CROUCHED;
			characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
			break;
		case LEFT_UNPRESSED:
			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = 0;
			break;
		case RIGHT_PRESSED:
			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = Constants.MOVEMENT_SPEED_CROUCHED;
			characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.right);
			break;
		case RIGHT_UNPRESSED:
			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = 0;
			break;
		case NO_COLLISION:
			System.out.println("no colisoin");// TODO this triggers hwen couched on p^latofrm
			characterStateManager.transition(CHARACTER_STATE.FALLING);
			break;
		case CAST_PRESSED:
			characterStateManager.transition(CHARACTER_STATE.PRECAST);
			break;
		default:
			break;

		}
	}

	@Override
	public void exit() {
		characterStateManager.getCharacter().getPhysicsComponent().setFallTrough(false);
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
