package jelte.mygame.logic.character.state;

import com.badlogic.gdx.physics.box2d.Body;

import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;

public interface CharacterState {
	public void entry();

	public void update(float delta, Body body);

	public void handleEvent(EVENT event);

	public void exit();

	public STATE getState();

}
