package jelte.mygame.logic;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.physics.PhysicsComponent;

public class CollisionSystemImpl implements CollisionSystem {
	private Array<TypedRectangle> blockingRectangles = new Array<>();

	@Override
	public void updateCollisions(Array<Character> characters) {
		updateCollisionsStaticObjects(characters);
		updateCollisionsDynamicObjects(characters);
	}

	private void updateCollisionsStaticObjects(Array<Character> characters) {
		for (Character character : characters) {
			PhysicsComponent body = character.getPhysicsComponent();
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
	public void setBlockingRectangles(Array<TypedRectangle> blockingRectangles) {
		this.blockingRectangles = blockingRectangles;
	}
}