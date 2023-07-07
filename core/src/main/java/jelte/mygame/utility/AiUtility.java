package jelte.mygame.utility;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.Direction;

public class AiUtility {

	private AiUtility() {

	}

	public static Array<Message> generateMoveInputToGoal(AiCharacter self, Vector2 goal) {
		Array<Message> input = new Array<>();
		Vector2 aiPosition = self.getPhysicsComponent().getPosition();
		Direction currentDirection = self.getPhysicsComponent().getDirection();

		if (aiPosition.x < goal.x) {
			if (currentDirection != Direction.right) {
				input.add(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_UNPRESSED));
				input.add(new Message(RECIPIENT.LOGIC, ACTION.LEFT_PRESSED));
			}
		} else {
			if (currentDirection != Direction.left) {
				input.add(new Message(RECIPIENT.LOGIC, ACTION.LEFT_UNPRESSED));
				input.add(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_PRESSED));
			}
		}
		return input;
	}
}
