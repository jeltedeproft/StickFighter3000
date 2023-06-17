package jelte.mygame.logic.collisions.strategy;

import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlockTop;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.logic.physics.SpellPhysicsComponent;

public class StaticTopCollisionStrategy implements CollisionStrategy {

	@Override
	public void resolvePossibleCollision(Collidable object1, Collidable object2) {
		StaticBlockTop statick = (StaticBlockTop) object1;

		// character
		if (object2 instanceof PlayerPhysicsComponent character) {
			character.setCollided(true, statick.getType());
			statick.calculateOverlapPlayer(character.getRectangle());
			character.move(0, -statick.getOverlapY());
			character.getVelocity().y = 0;
			character.getAcceleration().y = 0;

		}

		// spell
		if (object2 instanceof SpellPhysicsComponent spell) {

		}
	}

}
