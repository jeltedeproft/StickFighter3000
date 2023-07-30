package jelte.mygame.logic.collisions;

import com.badlogic.gdx.math.Vector2;

import java.util.Set;

import jelte.mygame.logic.collisions.collidable.Collidable;

public interface CollisionDetectionSystem {
	public void initSpatialMesh(Vector2 value);

	public void updateSpatialMesh(Set<Collidable> collidables);

	public Set<CollisionPair> getCollidingpairs();

	public void reset();

	public void addStatickCollidables(Set<Collidable> blockingObjects);

	public void removeStatickCollidable(Collidable collidableToRemove);

	public Set<Collidable> getCollidables();

}
