package jelte.mygame.logic.spells;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;

public class ProjectileSpellFactory implements SpellFactory {

	@Override
	public Spell createSpell(SpellData spellData, Character character, Vector2 mousePosition, UUID casterId) {
		return new ProjectileSpell(spellData, character.getPhysicsComponent().getPosition(), mousePosition, casterId);
	}

}
