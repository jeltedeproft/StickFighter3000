package jelte.mygame.logic;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.utility.Constants;

public class MovementSystem {

	public void update(float delta, Character player) {
		Vector2 currentPosition = player.getPositionVector();
		Vector2 velocityVector = player.getMovementVector();
		Vector2 accelerationVector = player.getAccelerationVector();

		// update velocity and limit speed
		velocityVector.add(accelerationVector.scl(delta));
		float speed = (float) Math.sqrt(velocityVector.x * velocityVector.x + velocityVector.y * velocityVector.y);
		if (speed > Constants.MAX_SPEED) {
			velocityVector.x *= Constants.MAX_SPEED / speed;
			velocityVector.y *= Constants.MAX_SPEED / speed;
		}

		// update position
		currentPosition.x = velocityVector.x * delta;
		currentPosition.y = velocityVector.y * delta;
		player.setPositionVector(currentPosition);

	}

}
