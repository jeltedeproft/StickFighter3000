package jelte.mygame.logic.collisions.strategy;

import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlockPlatform;
import jelte.mygame.logic.physics.CharacterPhysicsComponentImpl;
import jelte.mygame.logic.physics.SpellPhysicsComponent;

public class StaticPlatformCollisionStrategy implements CollisionStrategy {

	@Override
	public void resolvePossibleCollision(Collidable object1, Collidable object2) {
		StaticBlockPlatform statick = (StaticBlockPlatform) object1;

		// character
		if (object2 instanceof CharacterPhysicsComponentImpl character) {
			statick.calculateOverlapPlayer(character.getRectangle());
			if (!character.isFallTrough() && character.getVelocity().y < 0) {
				character.setCollided(true, statick.getType());
				character.move(0, statick.getOverlapY());
				character.getVelocity().y = 0;
				character.getAcceleration().y = 0;
			}
			if (character.isFallTrough()) {
				character.setCollided(true, statick.getType());
				character.move(0, -statick.getOverlapY() * 3);
			}

		}

		// spell
		if (object2 instanceof SpellPhysicsComponent spell) {

		}
	}
}
