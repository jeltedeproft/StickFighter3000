package jelte.mygame.logic.spells.factories;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.spells.AbstractSpell;
import jelte.mygame.logic.spells.spells.DefaultSpell;

public class SummonSpellFactory implements SpellFactory {

	@Override
	public AbstractSpell createSpell(SpellData spellData, Character caster, Vector2 mousePosition) {
		return new DefaultSpell(spellData, caster, mousePosition);
	}

}
