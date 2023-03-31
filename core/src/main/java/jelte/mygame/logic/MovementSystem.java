package jelte.mygame.logic;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.Constants;

public class MovementSystem {
	private Array<TypedRectangle> blockingRectangles = new Array<>();

	public void update(float delta, Character player) {
		Vector2 position = player.getPositionVector();
		Vector2 velocity = player.getMovementVector();
		Vector2 acceleration = player.getAccelerationVector();
		System.out.println("");
		System.out.println("");
		System.out.println("startposition = " + position);

		velocity.add(acceleration);
		velocity.add(Constants.GRAVITY);

		// Limit speed
		if (velocity.len2() > Constants.MAX_SPEED * Constants.MAX_SPEED) {
			velocity.setLength(Constants.MAX_SPEED);
		}

		Vector2 futurePlayerPos = position.cpy().add(velocity.cpy().scl(delta));
		Rectangle playerRect = new Rectangle(futurePlayerPos.x, futurePlayerPos.y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);

		Array<TypedRectangle> overlappingObstacles = getOverlappingObstacles(playerRect);

		if (!overlappingObstacles.isEmpty()) {
			handleCollision(player, futurePlayerPos, velocity, overlappingObstacles, playerRect);
		}

		player.setPositionVector(futurePlayerPos);
	}

	private Array<TypedRectangle> getOverlappingObstacles(Rectangle playerRect) {
		Array<TypedRectangle> overlappingObstacles = new Array<>();

		for (TypedRectangle obstacle : blockingRectangles) {
			if (obstacle.overlaps(playerRect)) {
				overlappingObstacles.add(obstacle);
			}
		}

		return overlappingObstacles;
	}

	private void handleCollision(Character player, Vector2 futurePlayerPos, Vector2 velocity, Array<TypedRectangle> overlappingObstacles, Rectangle playerRect) {
		// Calculate total overlap with all obstacles
		float overlapX = 0;
		float overlapY = 0;

		overlappingObstacles.sort();

		for (TypedRectangle obstacle : overlappingObstacles) {
			Rectangle intersection = new Rectangle();
			Intersector.intersectRectangles(obstacle, playerRect, intersection);
			overlapX = intersection.width;
			overlapY = intersection.height;

			System.out.println("handling collision " + obstacle.getType());
			System.out.println("==================");
			System.out.println("future position = " + futurePlayerPos);
			System.out.println("overlapX = " + overlapX);
			System.out.println("overlapY = " + overlapY);

			// Adjust position based on overlap and velocity
			float absOverlapX = Math.abs(overlapX);
			float absOverlapY = Math.abs(overlapY);

			switch (obstacle.getType()) {
			case CEILING:
				handleCollisionWall(absOverlapX, absOverlapY, futurePlayerPos, velocity, overlapX, overlapY);
				break;
			case GROUND:
				handleCollisionGround(absOverlapX, absOverlapY, futurePlayerPos, velocity, overlapX, overlapY);
				break;
			case PLATFORM:
				if (player.getCurrentCharacterState().getState() != STATE.CROUCHED && player.getCurrentCharacterState().getState() != STATE.JUMPING) {
					handleCollisionGround(absOverlapX, absOverlapY, futurePlayerPos, velocity, overlapX, overlapY);
				}
				break;
			case WALL:
				handleCollisionWall(absOverlapX, absOverlapY, futurePlayerPos, velocity, overlapX, overlapY);
				break;
			default:
				break;

			}
		}

	}

	private void handleCollisionGround(float absOverlapX, float absOverlapY, Vector2 futurePlayerPos, Vector2 velocity, float overlapX, float overlapY) {
		if (absOverlapX < absOverlapY) {
			if (velocity.x > 0) {
				futurePlayerPos.x -= overlapX;
				System.out.println("moving right : adjusting X = " + futurePlayerPos.x);
			}
			if (velocity.x < 0) {
				futurePlayerPos.x += overlapX;
				System.out.println("moving left : adjusting X = " + futurePlayerPos.x);
			}
		} else {
			if (velocity.y > 0) {
				futurePlayerPos.y -= overlapY;
				System.out.println("moving up : adjusting Y = " + futurePlayerPos.y);
			}
			if (velocity.y < 0) {
				futurePlayerPos.y += overlapY;
				System.out.println("moving down : adjusting Y = " + futurePlayerPos.y);
			}
		}

		// Set velocity to zero after collision
		velocity.y = 0;

	}

	private void handleCollisionWall(float absOverlapX, float absOverlapY, Vector2 futurePlayerPos, Vector2 velocity, float overlapX, float overlapY) {
		if (absOverlapX < absOverlapY) {
			if (velocity.x > 0) {
				futurePlayerPos.x -= overlapX;
				System.out.println("moving right : adjusting X = " + futurePlayerPos.x);
			}
			if (velocity.x < 0) {
				futurePlayerPos.x += overlapX;
				System.out.println("moving left : adjusting X = " + futurePlayerPos.x);
			}
		} else {
			if (velocity.y > 0) {
				futurePlayerPos.y -= overlapY;
				System.out.println("moving up : adjusting Y = " + futurePlayerPos.y);
			}
			if (velocity.y < 0) {
				futurePlayerPos.y += overlapY;
				System.out.println("moving down : adjusting Y = " + futurePlayerPos.y);
			}
		}

		velocity.x = 0;

	}

	public void setBlockingRectangles(Array<TypedRectangle> blockingRectangles) {
		this.blockingRectangles = blockingRectangles;
	}
}