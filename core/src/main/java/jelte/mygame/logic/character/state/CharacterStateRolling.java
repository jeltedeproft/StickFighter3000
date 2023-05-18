package jelte.mygame.logic.character.state;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManager.AudioCommand;
import jelte.mygame.graphical.audio.MusicManager.AudioEnum;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.Constants;

public class CharacterStateRolling implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float duration;
	private float timer = 0f;
	private STATE state = STATE.ROLLING;

	public CharacterStateRolling(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		this.duration = duration;
		timer = duration;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_ROLL1);
	}

	@Override
	public void update(float delta) {
		timer -= delta;
		if (timer <= 0) {
			timer = duration;
			if (Math.abs(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x) > 0) {
				characterStateManager.transition(STATE.RUNNING);
			} else {
				characterStateManager.transition(STATE.IDLE);
			}

		}
		Direction direction = characterStateManager.getCharacter().getPhysicsComponent().getDirection();
		if (direction.equals(Direction.right)) {
			characterStateManager.getCharacter().getPhysicsComponent().getPosition().add(Constants.ROLL_SPEED * delta, 0);// TODO make this smooth in update method.
		} else {
			characterStateManager.getCharacter().getPhysicsComponent().getPosition().add(-Constants.ROLL_SPEED * delta, 0);
		}
	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case RIGHT_UNPRESSED:
		case LEFT_UNPRESSED:
			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = 0;
			characterStateManager.getCharacter().getPhysicsComponent().setVelocity(new Vector2(0, 0));
			break;
//		case RIGHT_PRESSED:TODO find a way to remember that we pressed left or right during roll so that we can move appropriately after finishing the roll
//			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = Constants.MOVEMENT_SPEED;
//			characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.right);
//			break;
//		case LEFT_PRESSED:
//			characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = -Constants.MOVEMENT_SPEED;
//			characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
//			break;
		default:
			break;

		}
	}

	@Override
	public void exit() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_STOP, AudioEnum.SOUND_ROLL1);
	}

	@Override
	public STATE getState() {
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

}
