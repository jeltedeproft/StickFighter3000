package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.utility.Constants;

public class CharacterStateDashing implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float timer = 0f;
	private CHARACTER_STATE state = CHARACTER_STATE.DASHING;
	private float duration;

	public CharacterStateDashing(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		timer = duration;
		this.duration = duration;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_DASH, characterStateManager.getCharacter().getPhysicsComponent());
	}

	@Override
	public void update(float delta) {
		timer -= delta;
		boolean isRight = characterStateManager.getCharacter().getPhysicsComponent().getDirection() == Direction.right;
		characterStateManager.applyHorizontalForce((isRight ? Constants.DASH_DISTANCE : -Constants.DASH_DISTANCE) * delta);
		if (timer <= 0) {
			exit();
		}
	}

	@Override
	public void exit() {
		timer = duration;
		InputBox inputBox = characterStateManager.getCharacter().getCharacterInputHandler().getInputBox();
		if (inputBox.isPressed(BUTTONS.RIGHT)) {
			characterStateManager.startMovingOnTheGround(Constants.WALK_SPEED);
			characterStateManager.pushState(CHARACTER_STATE.WALKING);
		} else if (inputBox.isPressed(BUTTONS.LEFT)) {
			characterStateManager.startMovingOnTheGround(-Constants.WALK_SPEED);
			characterStateManager.pushState(CHARACTER_STATE.WALKING);
		} else {
			characterStateManager.stopMovingOnTheGround();
			characterStateManager.pushState(CHARACTER_STATE.IDLE);
		}
	}

	@Override
	public CHARACTER_STATE getState() {
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

	@Override
	public void pauze() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(EVENT event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleInput(InputBox inputBox) {
		// TODO Auto-generated method stub

	}

}
