package jelte.mygame.logic.spells.state;

import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.spells.Spell;
import jelte.mygame.logic.spells.SpellData;

public class SpellStateManager {
	private SpellState currentSpellState;
	private SpellState spellStateWindup;
	private SpellState spellStateLoop;
	private SpellState spellStateEnd;
	private SpellState spellStateDead;
	private Spell spell;

	public enum SPELL_STATE {
		WINDUP,
		LOOP,
		END,
		DEAD;
	}

	public SpellStateManager(Spell spell) {
		this.spell = spell;
		SpellData data = spell.getData();
		spellStateWindup = new SpellStateWindup(this, data.getWindupFullTime());
		spellStateLoop = new SpellStateLoop(this, data.getLoopFullTime());
		spellStateEnd = new SpellStateEnd(this, data.getEndFullTime());
		spellStateDead = new SpellStateDead(this);
		currentSpellState = spellStateWindup;
	}

	public void update(float delta) {
		currentSpellState.update(delta);
	}

	public SpellState getCurrentSpellState() {
		return currentSpellState;
	}

	public void transition(SPELL_STATE state) {
		currentSpellState.exit();
		switch (state) {
		case END:
			currentSpellState = spellStateEnd;
			break;
		case LOOP:
			currentSpellState = spellStateLoop;
			break;
		case WINDUP:
			currentSpellState = spellStateWindup;
			break;
		default:
			break;

		}
		currentSpellState.entry();
	}

	public void handleEvent(EVENT event) {
		currentSpellState.handleEvent(event);
	}

	public Spell getSpell() {
		return spell;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("currentSpellState : ");
		sb.append(currentSpellState);

		return sb.toString();
	}

}