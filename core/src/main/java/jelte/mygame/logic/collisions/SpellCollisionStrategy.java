package jelte.mygame.logic.collisions;

import jelte.mygame.logic.character.physics.CharacterPhysicsComponent;
import jelte.mygame.logic.character.physics.SpellPhysicsComponent;

public class SpellCollisionStrategy implements CollisionStrategy {

	@Override
	public void resolvePossibleCollision(Collidable object1, Collidable object2) {
		SpellPhysicsComponent spell = (SpellPhysicsComponent) object1;

		// character
		if (object2 instanceof CharacterPhysicsComponent character) {
			if (spell.getRectangle().overlaps(character.getRectangle())) {
				character.hitBySpell(spell.getSpellsEnum());
			}
		}

		// spell
		if (object2 instanceof SpellPhysicsComponent spell2) {
			// TODO define spell - spell interactions
		}
	}
}
