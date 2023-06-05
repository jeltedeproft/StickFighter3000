package jelte.mygame.logic.collisions.strategy;

import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlockBot;
import jelte.mygame.logic.physics.CharacterPhysicsComponent;
import jelte.mygame.logic.physics.SpellPhysicsComponent;

public class StaticBotCollisionStrategy implements CollisionStrategy {

	@Override
	public void resolvePossibleCollision(Collidable object1, Collidable object2) {
		StaticBlockBot statick = (StaticBlockBot) object1;

		// character
		if (object2 instanceof CharacterPhysicsComponent character) {
			statick.calculateOverlapPlayer(character.getRectangle());
			character.setCollided(true);
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
			statick.calculateOverlapPlayer(spell.getRectangle());
			spell.setCollided(true);
			spell.move(0, statick.getOverlapY());
			if (spell.getVelocity().y < 0) {
				spell.getVelocity().y = 0;
			}
			if (spell.getAcceleration().y < 0) {
				spell.getAcceleration().y = 0;
			}

		}
	}
}