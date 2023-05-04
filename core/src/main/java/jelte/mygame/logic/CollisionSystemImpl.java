package jelte.mygame.logic;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.physics.PhysicsComponent;

public class CollisionSystemImpl implements CollisionSystem {
	private Array<TypedRectangle> blockingRectangles = new Array<>();

	@Override
	public void updateCollisions(Array<PhysicsComponent> bodies) {
		updateCollisionsStaticObjects(bodies);
		updateCollisionsDynamicObjects(bodies);
	}

	private void updateCollisionsStaticObjects(Array<PhysicsComponent> bodies) {
		for (PhysicsComponent body : bodies) {
			Array<TypedRectangle> overlappingObstacles = getOverlappingObstacles(body.getRectangle());
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

	private Array<TypedRectangle> getOverlappingObstacles(Rectangle playerRect) {
		Array<TypedRectangle> overlappingObstacles = new Array<>();

		for (TypedRectangle obstacle : blockingRectangles) {
			if (obstacle.overlaps(playerRect)) {
				obstacle.setCollisionData(playerRect);
				overlappingObstacles.add(obstacle);
			}
		}

		return overlappingObstacles;
	}

	private void handleCollision(PhysicsComponent body, Vector2 pos, Array<TypedRectangle> overlappingObstacles) {
		overlappingObstacles.sort();

		TypedRectangle obstacle = overlappingObstacles.first();

		if (obstacle.isBlocksTop()) {
			pos.y -= obstacle.getOverlapY();
			body.getVelocity().y = 0;
		}

		if (obstacle.isBlocksBot()) {
			pos.y += obstacle.getOverlapY();
			body.getVelocity().y = 0;
		}

		if (obstacle.isBlocksLeft()) {
			pos.x += obstacle.getOverlapX();
			body.getVelocity().x = 0;
		}

		if (obstacle.isBlocksRight()) {
			pos.x -= obstacle.getOverlapX();
			body.getVelocity().x = 0;
		}

		if (obstacle.isFallTrough() && !body.isFallTrough()) {
			pos.y += obstacle.getOverlapY();
			body.getVelocity().y = 0;
		}
	}

	private void updateCollisionsDynamicObjects(Array<PhysicsComponent> bodies) {
		// TODO
	}

	@Override
	public void setBlockingRectangles(Array<TypedRectangle> blockingRectangles) {
		this.blockingRectangles = blockingRectangles;
	}
}