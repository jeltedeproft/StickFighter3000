package jelte.mygame.logic.collisions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.spatialMesh.SpatialMesh;

public class CollisionDetectionSystemImpl implements CollisionDetectionSystem {
	private static final String TAG = CollisionDetectionSystemImpl.class.getSimpleName();
	private SpatialMesh spatialMesh;

	@Override
	public void updateSpatialMesh(Set<Collidable> collidables) {
		spatialMesh.updateCollidables(collidables);
	}

	@Override
	public Set<CollisionPair> getCollidingpairs() {
		Set<CollisionPair> processedCollisions = new HashSet<>();
		Set<CollisionPair> collidedPairs = new HashSet<>();

		for (CollisionData collisionData : spatialMesh.getAllPossibleCollisions()) {
			List<Collidable> dynamicCollidables = new ArrayList<>(collisionData.getDynamicCollidables());
			List<Collidable> staticCollidables = new ArrayList<>(collisionData.getStaticCollidables());

			if (dynamicCollidables.isEmpty()) {
				continue;
			}

			for (Collidable dynamicCollidable : dynamicCollidables) {
				for (Collidable staticCollidable : staticCollidables) {
					checkPossibleCollision(staticCollidable, dynamicCollidable, processedCollisions, collidedPairs); // DYNAMIC -- STATIC
				}

				for (int i = dynamicCollidables.indexOf(dynamicCollidable) + 1; i < dynamicCollidables.size(); i++) {
					Collidable dynamicCollidable2 = dynamicCollidables.get(i);
					checkPossibleCollision(dynamicCollidable, dynamicCollidable2, processedCollisions, collidedPairs); // DYNAMIC -- DYNAMIC
				}
			}
		}
		return collidedPairs;
	}

	private void checkPossibleCollision(Collidable object1, Collidable object2, Set<CollisionPair> checkedPairs, Set<CollisionPair> collidedPairs) {
		CollisionPair pair = new CollisionPair(object1, object2);
		if (!checkedPairs.contains(pair)) {
			checkedPairs.add(pair);
			if (object1.getRectangle().overlaps(object2.getRectangle())) {
				if (object1.getType().toString().equals("SPELL") || object2.getType().toString().equals("SPELL")) {
					int j = 5;
				}
				Gdx.app.error(TAG, "collision between : " + object1.getType().toString() + " and " + object2.getType().toString());
				collidedPairs.add(pair);
			}
		}
	}

	@Override
	public void initSpatialMesh(Vector2 mapBoundaries) {
		spatialMesh = new SpatialMesh(mapBoundaries);
	}

	@Override
	public void reset() {
		spatialMesh.removeAllCollidables();
	}

	@Override
	public void addStatickCollidables(Set<Collidable> blockingObjects) {
		spatialMesh.addStatickCollidables(blockingObjects);
	}

	@Override
	public void removeStatickCollidable(Collidable collidableToRemove) {
		spatialMesh.removeStatickCollidable(collidableToRemove);
	}

	@Override
	public Set<Collidable> getCollidables() {
		return spatialMesh.getCollidablesInPlay();
	}

}