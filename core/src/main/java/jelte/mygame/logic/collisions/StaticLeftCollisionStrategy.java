package jelte.mygame.logic.collisions;

import jelte.mygame.logic.character.physics.CharacterPhysicsComponent;
import jelte.mygame.logic.character.physics.SpellPhysicsComponent;

public class StaticLeftCollisionStrategy implements CollisionStrategy {

	@Override
	public void resolvePossibleCollision(Collidable object1, Collidable object2) {
		StaticBlockLeft statick = (StaticBlockLeft) object1;

		// character
		if (object2 instanceof CharacterPhysicsComponent character) {
			if (statick.overlaps(character.getRectangle())) {
				character.setCollided(true);
				statick.calculateOverlapPlayer(character.getRectangle());
				character.move(statick.getOverlapX(), 0);
				character.getVelocity().x = 0;
				character.getAcceleration().x = 0;
			}
		}

		// spell
		if (object2 instanceof SpellPhysicsComponent spell) {
			if (statick.overlaps(spell.getRectangle())) {
				statick.calculateOverlapPlayer(spell.getRectangle());
				spell.setCollided(true);
				spell.move(statick.getOverlapX(), 0);
				spell.getVelocity().x = 0;
				spell.getAcceleration().x = 0;
			}
		}
	}

}
