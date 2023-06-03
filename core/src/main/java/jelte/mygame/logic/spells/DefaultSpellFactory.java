package jelte.mygame.logic.spells;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;

public class DefaultSpellFactory implements SpellFactory {

	@Override
	public Spell createSpell(SpellData spellData, Character character, Vector2 mousePosition) {
		return new DefaultSpell(spellData, character.getPhysicsComponent().getPosition(), mousePosition);
	}

}
