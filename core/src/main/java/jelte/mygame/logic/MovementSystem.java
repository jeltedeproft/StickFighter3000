package jelte.mygame.logic;

import com.badlogic.gdx.math.Intersector;
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

		velocity.add(acceleration.x, acceleration.y);

		velocity.add(Constants.GRAVITY.x, Constants.GRAVITY.y);

		if (velocity.len2() > Constants.MAX_SPEED * Constants.MAX_SPEED) {
			velocity.setLength(Constants.MAX_SPEED);
		}

		Vector2 futurePlayerPos = new Vector2(position.x + velocity.x * delta, position.y + velocity.y * delta);

		// check if the player collides with the obstacle after moving
		for (Rectangle obstacle : blockingRectangles) {
			Rectangle playerRect = new Rectangle(futurePlayerPos.x, futurePlayerPos.y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
			if (obstacle.overlaps(playerRect)) {
				Rectangle intersection = new Rectangle();
				// handle collision by resolving the position of the player
				Intersector.intersectRectangles(obstacle, playerRect, intersection);
				System.out.println("");
				System.out.println("");
				System.out.println("handling collision");
				System.out.println("==================");
				System.out.println("startposition = " + position);
				System.out.println("intersection = " + intersection);
				float overlapX = intersection.width;
				float overlapY = intersection.height;
				System.out.println("overlapX = " + overlapX);
				System.out.println("overlapY = " + overlapY);

				// adjust the position of the player based on its size
				float absOverlapX = Math.abs(overlapX);
				float absOverlapY = Math.abs(overlapY);
				if (absOverlapX < absOverlapY) {
					futurePlayerPos.x += overlapX;
					System.out.println("adjusting X = " + futurePlayerPos.x);
				} else {
					futurePlayerPos.y += overlapY;
					System.out.println("adjusting Y = " + futurePlayerPos.y);
				}
				// futurePlayerPos.y = Math.max(position.y, 0); // Ensure the player stays on the screen

				// set velocity to zero after collision
				System.out.println("velocity = 0");
				velocity.x = 0;
				velocity.y = 0;
			}
			System.out.println("no collision wit hthis rectangle");
		}

		player.setPositionVector(futurePlayerPos);

	}

	public void initBlockingObjects(Array<Rectangle> blockingRectangles) {
		this.blockingRectangles = blockingRectangles;
	}

}
