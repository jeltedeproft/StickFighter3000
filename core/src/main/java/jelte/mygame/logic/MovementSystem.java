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

		// Update velocity
		velocity.add(acceleration.x * delta, acceleration.y * delta);

		// Limit velocity to maximum speed
		if (velocity.len2() > (Constants.MAX_SPEED * Constants.MAX_SPEED)) {
			velocity.setLength(Constants.MAX_SPEED);
		}

		// Update position
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
