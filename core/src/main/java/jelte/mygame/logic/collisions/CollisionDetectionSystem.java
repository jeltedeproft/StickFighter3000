package jelte.mygame.logic.collisions;

import java.util.Set;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.collisions.collidable.Collidable;

public interface CollisionDetectionSystem {
	public void initSpatialMesh(Vector2 value);

	public void addToSpatialMesh(Array<Collidable> collidables);

	public void addToSpatialMesh(Collidable collidable);

	public void updateSpatialMesh(Array<Collidable> collidables);

	public void updateSpatialMesh(Collidable collidables);

	public Set<CollisionPair> getCollidingpairs();

	public void reset();

}
