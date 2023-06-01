package jelte.mygame.logic.collisions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlock;
import jelte.mygame.logic.collisions.collidable.Collidable.COLLIDABLE_TYPE;
import jelte.mygame.logic.collisions.spatialMesh.SpatialMesh;
import jelte.mygame.logic.collisions.strategy.CharacterCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.CollisionStrategy;
import jelte.mygame.logic.collisions.strategy.SpellCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticBotCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticLeftCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticPlatformCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticRightCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticTopCollisionStrategy;

public class CollisionSystemImpl implements CollisionSystem {
	private SpatialMesh spatialMesh;
	private Map<COLLIDABLE_TYPE, CollisionStrategy> collisionStrategies;

	public CollisionSystemImpl() {
		collisionStrategies = new HashMap<>();
		collisionStrategies.put(COLLIDABLE_TYPE.CHARACTER, new CharacterCollisionStrategy());
		collisionStrategies.put(COLLIDABLE_TYPE.SPELL, new SpellCollisionStrategy());
		collisionStrategies.put(COLLIDABLE_TYPE.STATIC_TOP, new StaticTopCollisionStrategy());
		collisionStrategies.put(COLLIDABLE_TYPE.STATIC_BOT, new StaticBotCollisionStrategy());
		collisionStrategies.put(COLLIDABLE_TYPE.STATIC_LEFT, new StaticLeftCollisionStrategy());
		collisionStrategies.put(COLLIDABLE_TYPE.STATIC_RIGHT, new StaticRightCollisionStrategy());
		collisionStrategies.put(COLLIDABLE_TYPE.STATIC_PLATFORM, new StaticPlatformCollisionStrategy());
	}

	@Override
	public void addToSpatialMesh(Collidable collidable) {// TODO shouldnt know about spatialMesh, just executreCollisions and check inside spatialmesh if new characters needto be added or updated
		spatialMesh.addCollidable(collidable);
	}

	@Override
	public void updateSpatialMesh(Collidable collidable) {
		spatialMesh.updateCollidable(collidable);
	}

	@Override
	public void addToSpatialMesh(Array<Collidable> collidables) {
		spatialMesh.addCollidables(collidables);
	}

	@Override
	public void updateSpatialMesh(Array<Collidable> collidables) {
		spatialMesh.updateCollidables(collidables);
	}

	@Override
	public void executeCollisions() {
		Set<CollisionPair> processedCollisions = new HashSet<>();

		for (CollisionData collisionData : spatialMesh.getAllPossibleCollisions()) {
			List<Collidable> dynamicCollidables = new ArrayList<>(collisionData.getDynamicCollidables());
			List<Collidable> staticCollidables = new ArrayList<>(collisionData.getStaticCollidables());

			if (dynamicCollidables.isEmpty()) {
				continue;
			}

			for (Collidable dynamicCollidable : dynamicCollidables) {
				for (Collidable staticCollidable : staticCollidables) {
					CollisionPair pair = new CollisionPair(dynamicCollidable, staticCollidable);
					if (!processedCollisions.contains(pair)) {
						resolveDynamicCollision(staticCollidable, dynamicCollidable); // DYNAMIC -- STATIC
						processedCollisions.add(pair);
					}
				}

				for (int i = dynamicCollidables.indexOf(dynamicCollidable) + 1; i < dynamicCollidables.size(); i++) {
					Collidable dynamicCollidable2 = dynamicCollidables.get(i);
					CollisionPair pair = new CollisionPair(dynamicCollidable, dynamicCollidable2);
					if (!processedCollisions.contains(pair)) {
						resolveDynamicCollision(dynamicCollidable, dynamicCollidable2); // DYNAMIC -- DYNAMIC
						processedCollisions.add(pair);
					}
				}
			}
		}
	}

	private void resolveDynamicCollision(Collidable object1, Collidable object2) {
		COLLIDABLE_TYPE type1 = object1.getType();
		COLLIDABLE_TYPE type2 = object2.getType();

		CollisionStrategy collisionStrategy1 = collisionStrategies.get(type1);
		CollisionStrategy collisionStrategy2 = collisionStrategies.get(type2);

		if (collisionStrategy1 != null && collisionStrategy2 != null) {
			collisionStrategy1.resolvePossibleCollision(object1, object2);
			collisionStrategy2.resolvePossibleCollision(object2, object1);
		}
	}

	private Array<StaticBlock> getOverlappingObstacles(Rectangle playerRect, Set<Collidable> staticColliders) {
		Array<StaticBlock> overlappingObstacles = new Array<>();
		for (Collidable obstacle : staticColliders) {
			StaticBlock staticBlock = (StaticBlock) obstacle;
			if (staticBlock.overlaps(playerRect)) {
				staticBlock.calculateOverlapPlayer(playerRect);
				overlappingObstacles.add(staticBlock);
			}
		}
		return overlappingObstacles;
	}

	@Override
	public void initSpatialMesh(Vector2 mapBoundaries) {
		spatialMesh = new SpatialMesh(mapBoundaries);
	}

	@Override
	public void reset() {
		spatialMesh.removeAllCollidables();
	}

}