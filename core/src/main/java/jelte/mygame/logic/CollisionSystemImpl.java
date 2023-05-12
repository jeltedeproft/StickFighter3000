package jelte.mygame.logic;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.physics.PhysicsComponent;

public class CollisionSystemImpl implements CollisionSystem {
	private SpatialMesh spatialMesh;
	private Array<StaticBlock> blockingRectangles = new Array<>();

	// XXX important, change the way collisiondetection works,use spatialmesh, look at all cells adjacent to the one we currently have,
	@Override
	public void updateCollisions(Array<Character> characters) {
		updateCollisionsStaticObjects(characters);
		updateCollisionsDynamicObjects(characters);
	}

	private void updateCollisionsStaticObjects(Array<Character> characters) {
		for (Character character : characters) {
			PhysicsComponent body = character.getPhysicsComponent();
			Array<StaticBlock> overlappingObstacles = getOverlappingObstacles(body.getRectangle());
			boolean collided = !overlappingObstacles.isEmpty();

			if (collided) {
				System.out.println("collision");
				body.setCollided(true);
				Vector2 pos = body.getPosition();
				handleCollision(body, pos, overlappingObstacles);
				body.setPosition(pos);
			}
		}
	}

	private Array<StaticBlock> getOverlappingObstacles(Rectangle playerRect) {
		Array<StaticBlock> overlappingObstacles = new Array<>();

		for (StaticBlock obstacle : blockingRectangles) {
			if (obstacle.overlaps(playerRect)) {
				obstacle.calculateOverlapPlayer(playerRect);
				overlappingObstacles.add(obstacle);
			}
		}

		return overlappingObstacles;
	}

	private void handleCollision(PhysicsComponent body, Vector2 pos, Array<StaticBlock> overlappingObstacles) {
		overlappingObstacles.sort();
		StaticBlock obstacle = overlappingObstacles.first();
		obstacle.handleCollision(body, pos);
	}

	private void updateCollisionsDynamicObjects(Array<Character> characters) {
		for (int i = 0; i < characters.size; i++) {
			for (int j = 0; j < characters.size; j++) {
				if (i != j) {
					PhysicsComponent mainBody = characters.get(i).getPhysicsComponent();
					PhysicsComponent otherBody = characters.get(j).getPhysicsComponent();
					if (mainBody.getRectangle().overlaps(otherBody.getRectangle())) {
						characters.get(i).damage(0.5f);
					}
				}
			}
		}
	}

	@Override
	public void setBlockingRectangles(Array<StaticBlock> blockingRectangles) {
		this.blockingRectangles = blockingRectangles;
	}

	public void initSpatialMesh(Vector2 mapBoundaries) {
		spatialMesh = new SpatialMesh(mapBoundaries);
	}

}