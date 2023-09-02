package jelte.mygame.logic.collisions.strategy;

import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.Collidable.COLLIDABLE_TYPE;
import jelte.mygame.logic.collisions.collidable.StaticBlockRight;
import jelte.mygame.logic.physics.CharacterPhysicsComponentImpl;
import jelte.mygame.logic.physics.SpellPhysicsComponent;

public class StaticRightCollisionStrategy implements CollisionStrategy {

	@Override
	public void resolvePossibleCollision(Collidable object1, Collidable object2) {
		StaticBlockRight statick = (StaticBlockRight) object1;

		// character
		if (object2 instanceof CharacterPhysicsComponentImpl character) {
			character.setCollided(true, statick.getType());
			statick.calculateOverlapPlayer(character.getRectangle());
			character.move(-statick.getOverlapX(), 0);
			character.getVelocity().x = 0;
			character.getAcceleration().x = 0;

		}

		// spell
		if (object2 instanceof SpellPhysicsComponent spell) {
			if (!spell.goesTroughWalls()) {
				spell.setCollided(true, COLLIDABLE_TYPE.STATIC_RIGHT);
			}
		}
	}

}
