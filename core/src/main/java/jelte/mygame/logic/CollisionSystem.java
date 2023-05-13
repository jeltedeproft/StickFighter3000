package jelte.mygame.logic;

import com.badlogic.gdx.utils.Array;

public interface CollisionSystem {

	public void addToSpatialMesh(Array<Collidable> Collidable);

	public void updateSpatialMesh(Array<Collidable> collidables);

	public void executeCollisions();

}
