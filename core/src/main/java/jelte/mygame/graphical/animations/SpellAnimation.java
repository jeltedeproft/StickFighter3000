package jelte.mygame.graphical.animations;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.spells.state.SpellStateManager.SPELL_STATE;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpellAnimation implements AnimationName {
	private String spellName;
	private SPELL_STATE state;
	private Float timeRunning = 0f;
	private String fullName;

	public SpellAnimation(String spellName, SPELL_STATE state) {
		this.spellName = spellName;
		this.state = state;
		rebuildName();
	}

	public void changeSpell(String newSpellName) {
		spellName = newSpellName;
		rebuildName();
		resetTimer();
	}

	public void changeState(SPELL_STATE newState) {
		state = newState;
		rebuildName();
		resetTimer();
	}

	@Override
	public void rebuildName() {
		StringBuilder sb = new StringBuilder();
		sb.append(spellName);
		sb.append("-");
		sb.append(state.toString());
		fullName = sb.toString();
	}

	@Override
	public String getFullName() {
		return fullName;
	}

	@Override
	public void resetTimer() {
		timeRunning = 0f;
	}

	@Override
	public void update(float delta) {
		timeRunning += delta;
	}

	public boolean updateData(String spellName, SPELL_STATE state) {
		boolean changed = false;
		if (!spellName.equals(this.spellName)) {
			changed = true;
			this.spellName = spellName;
		}
		if (!state.equals(this.state)) {
			changed = true;
			this.state = state;
		}
		if (changed) {
			resetTimer();
			rebuildName();
		}
		return changed;
	}

	@Override
	public String toString() {
		return fullName;
	}

}
