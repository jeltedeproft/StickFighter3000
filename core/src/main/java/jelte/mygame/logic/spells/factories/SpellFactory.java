package jelte.mygame.logic.spells.factories;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.spells.Spell;

public interface SpellFactory {
	Spell createSpell(SpellData spellData, Character character, Vector2 mousePosition);
}
