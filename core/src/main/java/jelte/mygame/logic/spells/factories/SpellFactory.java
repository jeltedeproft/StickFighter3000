package jelte.mygame.logic.spells.factories;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.spells.AbstractSpell;

public interface SpellFactory {
	AbstractSpell createSpell(SpellData spellData, Character character, Character target, Vector2 mousePosition);
}
