package jelte.mygame.logic.collisions.strategy;

import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlockLeft;
import jelte.mygame.logic.physics.CharacterPhysicsComponent;
import jelte.mygame.logic.physics.SpellPhysicsComponent;

public class StaticLeftCollisionStrategy implements CollisionStrategy {

	@Override
	public void resolvePossibleCollision(Collidable object1, Collidable object2) {
		StaticBlockLeft statick = (StaticBlockLeft) object1;

		// character
		if (object2 instanceof CharacterPhysicsComponent character) {

			character.setCollided(true);
			statick.calculateOverlapPlayer(character.getRectangle());
			character.move(statick.getOverlapX(), 0);
			character.getVelocity().x = 0;
			character.getAcceleration().x = 0;

		}

		// spell
		if (object2 instanceof SpellPhysicsComponent spell) {

			statick.calculateOverlapPlayer(spell.getRectangle());
			spell.setCollided(true);
			spell.move(statick.getOverlapX(), 0);
			spell.getVelocity().x = 0;
			spell.getAcceleration().x = 0;

		}
	}

}
