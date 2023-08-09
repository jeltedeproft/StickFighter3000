package jelte.mygame.utility;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
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
				self.getInputBox().updateButtonPressed(BUTTONS.LEFT, false);
				self.getInputBox().updateButtonPressed(BUTTONS.RIGHT, true);
				input.add(new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP, self.getInputBox()));
			}
		} else {
			if (currentDirection != Direction.left) {
				self.getInputBox().updateButtonPressed(BUTTONS.RIGHT, false);
				self.getInputBox().updateButtonPressed(BUTTONS.LEFT, true);
				input.add(new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP, self.getInputBox()));
			}
		}
		return input;
	}

	public static Array<Message> generateMoveInputAwayFromGoal(AiCharacter self, Vector2 goal) {
		Array<Message> input = new Array<>();
		Vector2 aiPosition = self.getPhysicsComponent().getPosition();
		Direction currentDirection = self.getPhysicsComponent().getDirection();

		if (aiPosition.x < goal.x) {
			if (currentDirection != Direction.left) {
				self.getInputBox().updateButtonPressed(BUTTONS.RIGHT, false);
				self.getInputBox().updateButtonPressed(BUTTONS.LEFT, true);
				input.add(new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP, self.getInputBox()));
			}
		} else {
			if (currentDirection != Direction.right) {
				self.getInputBox().updateButtonPressed(BUTTONS.LEFT, false);
				self.getInputBox().updateButtonPressed(BUTTONS.RIGHT, true);
				input.add(new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP, self.getInputBox()));
			}
		}
		return input;
	}
}
