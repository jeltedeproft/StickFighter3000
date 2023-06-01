package jelte.mygame.logic.collisions;

import jelte.mygame.logic.character.physics.CharacterPhysicsComponent;
import jelte.mygame.logic.character.physics.SpellPhysicsComponent;

public class StaticPlatformCollisionStrategy implements CollisionStrategy {

	@Override
	public void resolvePossibleCollision(Collidable object1, Collidable object2) {
		StaticBlockPlatform statick = (StaticBlockPlatform) object1;

		// character
		if (object2 instanceof CharacterPhysicsComponent character) {
			if (statick.overlaps(character.getRectangle())) {
				statick.calculateOverlapPlayer(character.getRectangle());
				if (!character.isFallTrough() && character.getVelocity().y < 0) {
					character.setCollided(true);
					character.move(0, statick.getOverlapY());
					character.getVelocity().y = 0;
					character.getAcceleration().y = 0;
				}
				if (character.isFallTrough()) {
					character.setCollided(true);
					character.move(0, -statick.getOverlapY() * 3);
				}
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
