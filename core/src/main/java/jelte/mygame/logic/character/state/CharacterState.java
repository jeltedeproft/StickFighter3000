package jelte.mygame.logic.character.state;

import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;

public interface CharacterState {
	public void entry();

	public void update(float delta);

	public void pauze();

	public void resume();

	public void handleEvent(EVENT event);

	public void exit();

	public CHARACTER_STATE getState();

}
