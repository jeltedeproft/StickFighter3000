package jelte.mygame.logic.spells.state;

import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.spells.state.SpellStateManager.SPELL_STATE;

public interface SpellState {
	public void entry();

	public void update(float delta);

	public void handleEvent(EVENT event);

	public void exit();

	public SPELL_STATE getState();

}
