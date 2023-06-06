package jelte.mygame.logic.collisions;

import java.util.Set;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.collisions.collidable.Collidable;

public interface CollisionDetectionSystem {
	public void initSpatialMesh(Vector2 value);

	public void updateSpatialMesh(Set<Collidable> collidables);

	public Set<CollisionPair> getCollidingpairs();

	public void reset();

	public void initializeStatickCollidables(Set<Collidable> blockingObjects);

}
