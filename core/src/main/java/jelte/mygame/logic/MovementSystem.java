package jelte.mygame.logic;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.utility.Constants;

public class MovementSystem {

	public Map<Character, Vector2> update(float delta, Array<Character> characters) {
		Map<Character, Vector2> futurePositions = new HashMap<>();
		for (Character character : characters) {
			Vector2 position = character.getPositionVector();
			Vector2 velocity = character.getMovementVector();
			Vector2 acceleration = character.getAccelerationVector();

			velocity.add(acceleration);
			velocity.add(Constants.GRAVITY);

			// Limit speed
			if (velocity.len2() > Constants.MAX_SPEED * Constants.MAX_SPEED) {
				velocity.setLength(Constants.MAX_SPEED);
			}
			futurePositions.put(character, position.cpy().add(velocity.cpy().scl(delta)));
		}
		return futurePositions;
	}
}