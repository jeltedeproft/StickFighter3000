package jelte.mygame.logic.spells.factories;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellsEnum;
import jelte.mygame.logic.spells.spells.AbstractSpell;
import jelte.mygame.logic.spells.spells.BuffSpell;

public class BuffSpellFactory implements SpellFactory {

	@Override
	public AbstractSpell createSpell(SpellData spellData, Character caster, Character target, Vector2 mousePosition) {
		return switch (SpellsEnum.values()[spellData.getId()]) {
			default -> new BuffSpell(spellData, caster, target);
		};

	}

}
