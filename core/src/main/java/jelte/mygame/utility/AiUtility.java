package jelte.mygame.utility;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.Direction;

public class AiUtility {

	private AiUtility() {

	}

	public static Message generateMoveInputToGoal(AiCharacter self, Vector2 goal) {
		Vector2 aiPosition = self.getPhysicsComponent().getPosition();
		Direction currentDirection = self.getPhysicsComponent().getDirection();

		if (aiPosition.x < goal.x) {
			if (currentDirection != Direction.right) {
				self.getInputBox().updateButtonPressed(BUTTONS.LEFT, false);
				self.getInputBox().updateButtonPressed(BUTTONS.RIGHT, true);
			}
		} else {
			if (currentDirection != Direction.left) {
				self.getInputBox().updateButtonPressed(BUTTONS.RIGHT, false);
				self.getInputBox().updateButtonPressed(BUTTONS.LEFT, true);
			}
		}
		return new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP, self.getInputBox());
	}

	public static Message generateMoveInputAwayFromGoal(AiCharacter self, Vector2 goal) {
		Vector2 aiPosition = self.getPhysicsComponent().getPosition();
		Direction currentDirection = self.getPhysicsComponent().getDirection();

		if (aiPosition.x < goal.x) {
			if (currentDirection != Direction.left) {
				self.getInputBox().updateButtonPressed(BUTTONS.RIGHT, false);
				self.getInputBox().updateButtonPressed(BUTTONS.LEFT, true);
			}
		} else {
			if (currentDirection != Direction.right) {
				self.getInputBox().updateButtonPressed(BUTTONS.LEFT, false);
				self.getInputBox().updateButtonPressed(BUTTONS.RIGHT, true);
			}
		}
		return new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP, self.getInputBox());
	}
}
