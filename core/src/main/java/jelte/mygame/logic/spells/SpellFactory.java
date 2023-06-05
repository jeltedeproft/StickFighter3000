package jelte.mygame.logic.spells;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;

public interface SpellFactory {
	Spell createSpell(SpellData spellData, Character character, Vector2 mousePosition);
}