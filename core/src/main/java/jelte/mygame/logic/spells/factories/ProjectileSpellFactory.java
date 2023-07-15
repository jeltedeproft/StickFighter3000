package jelte.mygame.logic.spells.factories;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.spells.AbstractSpell;
import jelte.mygame.logic.spells.spells.ProjectileSpell;

public class ProjectileSpellFactory implements SpellFactory {

	@Override
	public AbstractSpell createSpell(SpellData spellData, Character character, Character target, Vector2 mousePosition) {
		return new ProjectileSpell(spellData, character, mousePosition);
	}

}
