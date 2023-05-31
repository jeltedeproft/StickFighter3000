package jelte.mygame.logic.collisions;

import jelte.mygame.logic.character.physics.CharacterPhysicsComponent;
import jelte.mygame.logic.character.physics.SpellPhysicsComponent;

public class StaticTopCollisionStrategy implements CollisionStrategy {

	@Override
	public void resolvePossibleCollision(Collidable object1, Collidable object2) {
		StaticBlockTop statick = (StaticBlockTop) object1;

		// character
		if (object2 instanceof CharacterPhysicsComponent character) {
			if (statick.overlaps(character.getRectangle())) {
				character.setCollided(true);
				statick.calculateOverlapPlayer(character.getRectangle());
				character.move(0, -statick.getOverlapY());
				character.getVelocity().y = 0;
				character.getAcceleration().y = 0;
			}
		}

		// spell
		if (object2 instanceof SpellPhysicsComponent spell) {
			if (statick.overlaps(spell.getRectangle())) {
				statick.calculateOverlapPlayer(spell.getRectangle());
				spell.setCollided(true);
				spell.move(0, -statick.getOverlapY());
				spell.getVelocity().y = 0;
				spell.getAcceleration().y = 0;
			}
		}
	}

}
