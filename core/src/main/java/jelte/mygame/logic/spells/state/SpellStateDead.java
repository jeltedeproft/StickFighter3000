package jelte.mygame.logic.spells.state;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.spells.state.SpellStateManager.SPELL_STATE;

public class SpellStateDead implements SpellState {
	private SpellStateManager spellStateManager;
	private SPELL_STATE state = SPELL_STATE.DEAD;

	public SpellStateDead(SpellStateManager spellStateManager) {
		this.spellStateManager = spellStateManager;
	}

	@Override
	public void entry() {
		spellStateManager.getSpell().setComplete(true);
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void exit() {

	}

	@Override
	public SPELL_STATE getState() {
		return state;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("in ");
		sb.append(state.name());

		return sb.toString();
	}

	@Override
	public void handleEvent(EVENT event) {
		// TODO Auto-generated method stub

	}

}
