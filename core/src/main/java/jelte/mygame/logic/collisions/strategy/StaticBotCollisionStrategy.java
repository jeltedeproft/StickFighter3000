package jelte.mygame.logic.collisions.strategy;

import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlockBot;
import jelte.mygame.logic.physics.CharacterPhysicsComponentImpl;
import jelte.mygame.logic.physics.SpellPhysicsComponent;

public class StaticBotCollisionStrategy implements CollisionStrategy {

	@Override
	public void resolvePossibleCollision(Collidable object1, Collidable object2) {
		StaticBlockBot statick = (StaticBlockBot) object1;

		// character
		if (object2 instanceof CharacterPhysicsComponentImpl character) {
			statick.calculateOverlapPlayer(character.getRectangle());
			character.setCollided(true, statick.getType());
			character.setOnGround(true);
			character.move(0, statick.getOverlapY());
			if (character.getVelocity().y < 0) {
				character.getVelocity().y = 0;
			}
			if (character.getAcceleration().y < 0) {
				character.getAcceleration().y = 0;
			}
		}

		// spell
		if (object2 instanceof SpellPhysicsComponent spell) {

		}
	}
}
