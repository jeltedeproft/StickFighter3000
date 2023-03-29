package jelte.mygame.logic;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.utility.Constants;

public class MovementSystem {
	Array<Rectangle> blockingRectangles = new Array<>();

	public void update(float delta, Character player) {
		Vector2 position = player.getPositionVector();
		Vector2 velocity = player.getMovementVector();
		Vector2 acceleration = player.getAccelerationVector();
		boolean collided = false;
		boolean onGround = false;

		velocity.add(acceleration.x * delta, acceleration.y * delta);

		velocity.add(Constants.GRAVITY.x * delta, Constants.GRAVITY.y * delta);

		if (velocity.len2() > Constants.MAX_SPEED * Constants.MAX_SPEED) {
			velocity.setLength(Constants.MAX_SPEED);
		}

		Vector2 futurePlayerPos = new Vector2(position.x + velocity.x * delta, position.y + velocity.y * delta);

		if (acceleration.x >= 49) {
			int j = 5;
		}

		// check if the player collides with the obstacle after moving
		for (Rectangle obstacle : blockingRectangles) {
			if (obstacle.overlaps(new Rectangle(futurePlayerPos.x, futurePlayerPos.y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT))) {
				// handle collision by resolving the position of the player
				collided = true;
				System.out.println("");
				System.out.println("");
				System.out.println("handling collision");
				System.out.println("==================");
				float overlapX = 0;
				float overlapY = 0;
				if (velocity.x > 0) {
					overlapX = obstacle.x - (position.x + Constants.PLAYER_WIDTH);
				} else if (velocity.x < 0) {
					overlapX = obstacle.x + obstacle.width - position.x;
				}
				if (velocity.y > 0) {
					overlapY = obstacle.y - (position.y + Constants.PLAYER_HEIGHT);
				} else if (velocity.y < 0) {
					overlapY = obstacle.y + obstacle.height - position.y;
				}

				// adjust the position of the player based on its size
				float absOverlapX = Math.abs(overlapX);
				float absOverlapY = Math.abs(overlapY);
				if (absOverlapX < absOverlapY) {
					position.x += overlapX;
				} else {
					position.y += overlapY;
				}
				position.y = Math.max(position.y, 0); // Ensure the player stays on the screen

				// set velocity to zero after collision
				System.out.println("velocity = 0");
				velocity.x = 0;
				velocity.y = 0;
			}
		}

		if (!collided) {
			System.out.println("no collisions, moving according to the forces of physics");
			player.setPositionVector(futurePlayerPos);
		}
	}

	public void initBlockingObjects(Array<Rectangle> blockingRectangles) {
		this.blockingRectangles = blockingRectangles;
	}

}
