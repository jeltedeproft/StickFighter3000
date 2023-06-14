package jelte.mygame.logic.spells.factories;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellsEnum;
import jelte.mygame.logic.spells.spells.AoeSpell;
import jelte.mygame.logic.spells.spells.Spell;

public class AoeSpellFactory implements SpellFactory {

	@Override
	public Spell createSpell(SpellData spellData, Character caster, Vector2 mousePosition) {
		return switch (SpellsEnum.values()[spellData.getId()]) {
		case ATTACK -> new AoeSpell(spellData, caster, caster.getPhysicsComponent().getPosition(), false);
		case FALLATTACK -> new AoeSpell(spellData, caster, mousePosition, true);
		default -> new AoeSpell(spellData, caster, mousePosition, false);
		};

	}

}