package jelte.mygame.logic.collisions;

import com.badlogic.gdx.utils.Array;

public interface CollisionSystem {

	public void addToSpatialMesh(Array<Collidable> collidables);

	public void updateSpatialMesh(Array<Collidable> collidables);

	public void executeCollisions();

}
