package jelte.mygame.logic.collisions.strategy;

import jelte.mygame.logic.collisions.collidable.Collidable;

public interface CollisionStrategy {
	void resolvePossibleCollision(Collidable object1, Collidable object2);
}
