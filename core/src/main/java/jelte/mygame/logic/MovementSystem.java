package jelte.mygame.logic;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.utility.Constants;

public class MovementSystem {
	Array<Rectangle> blockingRectangles;

	public void update(float delta, Character player) {
		Vector2 position = player.getPositionVector();
		Vector2 velocity = player.getMovementVector();
		Vector2 acceleration = player.getAccelerationVector();

		// velocity.add(acceleration.x * delta, acceleration.y * delta);

		if (velocity.len2() > Constants.MAX_SPEED * Constants.MAX_SPEED) {
			velocity.setLength(Constants.MAX_SPEED);
		}

		applygravity(velocity, delta);

		Vector2 futurePlayerPos = new Vector2(position).add(velocity).add(acceleration);

		// updatePositionIfNotBlocked(delta, position, velocity);

		// check if the player collides with the obstacle after moving
		if (obstacle.overlaps(new Rectangle(futurePlayerPos.x, futurePlayerPos.y, playerPos.x, playerPos.y))) {
			// handle collision by resolving the position of the player
			float overlapX = Math.abs(playerVelocity.x) + Math.abs(playerAcceleration.x);
			float overlapY = Math.abs(playerVelocity.y) + Math.abs(playerAcceleration.y);
			if (playerVelocity.x > 0) {
				playerPos.x = obstacle.x - playerPos.x - overlapX;
			} else if (playerVelocity.x < 0) {
				playerPos.x = obstacle.x + obstacle.width + overlapX;
			}
			if (playerVelocity.y > 0) {
				playerPos.y = obstacle.y - playerPos.y - overlapY;
			} else if (playerVelocity.y < 0) {

			}
		}

	}

	private void applygravity(Vector2 velocity, float delta) {
		velocity.y -= Constants.GRAVITY.y * delta;
	}

	private void updatePositionIfNotBlocked(float delta, Vector2 position, Vector2 velocity) {
		Vector2 newPosition = position.cpy();
		Rectangle playerRect = new Rectangle(newPosition.x, newPosition.y, 10, 10);// TODO change to sprite width and height
		if (!positionBlocks(playerRect, blockingRectangles)) {
			position.add(velocity.x * delta, velocity.y * delta);
		}
	}

	private boolean positionBlocks(Rectangle playerRect, Array<Rectangle> blockingRectangles) {
		for (Rectangle rect : blockingRectangles) {
			if (playerRect.overlaps(rect)) {
				return true;
			}
		}
		return false;
	}

	public void initBlockingObjects(Array<Rectangle> blockingRectangles) {
		this.blockingRectangles = blockingRectangles;
	}

}
