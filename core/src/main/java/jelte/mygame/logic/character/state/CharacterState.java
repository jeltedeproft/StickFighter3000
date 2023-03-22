package jelte.mygame.logic.character.state;

import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;

public interface CharacterState {
	public void entry();

	public void update(float delta);

	public void handleEvent(EVENT event);

	public void exit();

	public STATE getState();

}
