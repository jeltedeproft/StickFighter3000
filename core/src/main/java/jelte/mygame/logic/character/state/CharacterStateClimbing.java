package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.utility.Constants;

public class CharacterStateClimbing implements CharacterState {
	private CharacterStateManager characterStateManager;
	private CHARACTER_STATE state = CHARACTER_STATE.CLIMBING;

	public CharacterStateClimbing(CharacterStateManager characterStateManager) {
		this.characterStateManager = characterStateManager;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_CLIMB, characterStateManager.getCharacter().getPhysicsComponent());
	}

	@Override
	public void update(float delta) {
		InputBox inputBox = characterStateManager.getCharacter().getCharacterInputHandler().getInputBox();
		if (inputBox.isPressed(BUTTONS.UP)) {
			characterStateManager.climb(Constants.CLIMB_SPEED);
		}
		if (inputBox.isPressed(BUTTONS.DOWN)) {
			characterStateManager.climb(-Constants.CLIMB_SPEED);
		}
	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case DAMAGE_TAKEN:
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.IDLE);
			characterStateManager.pushState(CHARACTER_STATE.HURT);
			break;
		case NO_COLLISION:
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.FALLING);
			break;
		default:
			break;

		}
	}

	@Override
	public void handleInput(InputBox inputBox) {
		switch (inputBox.getLastUsedButton()) {
		case LEFT:
			if (inputBox.isPressed(BUTTONS.LEFT)) {
				characterStateManager.startMovingInTheAir(-Constants.WALK_SPEED);
			} else {
				characterStateManager.stopMovingInTheAir();
			}
			break;
		case RIGHT:
			if (inputBox.isPressed(BUTTONS.RIGHT)) {
				characterStateManager.startMovingInTheAir(Constants.WALK_SPEED);
			} else {
				characterStateManager.stopMovingInTheAir();
			}
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

	@Override
	public void pauze() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
