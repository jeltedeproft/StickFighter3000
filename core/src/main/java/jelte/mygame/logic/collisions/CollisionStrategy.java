package jelte.mygame.logic.collisions;

public interface CollisionStrategy {
	void resolvePossibleCollision(Collidable object1, Collidable object2);
}
