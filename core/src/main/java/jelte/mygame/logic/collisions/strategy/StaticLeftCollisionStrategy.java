package jelte.mygame.logic.collisions.strategy;

import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlockLeft;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.logic.physics.SpellPhysicsComponent;

public class StaticLeftCollisionStrategy implements CollisionStrategy {

	@Override
	public void resolvePossibleCollision(Collidable object1, Collidable object2) {
		StaticBlockLeft statick = (StaticBlockLeft) object1;

		// character
		if (object2 instanceof PlayerPhysicsComponent character) {

			statick.calculateOverlapPlayer(character.getRectangle());
			character.setCollided(true, statick.getType());
			character.move(statick.getOverlapX(), 0);
			character.getVelocity().x = 0;
			character.getAcceleration().x = 0;

		}

		// spell
		if (object2 instanceof SpellPhysicsComponent spell) {

		}
	}

}
