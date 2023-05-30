package jelte.mygame.logic.character.state;import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;

public interface CharacterState {
	public void entry();

	public void update(float delta);

	public void handleEvent(EVENT event);

	public void exit();

	public CHARACTER_STATE getState();

}
