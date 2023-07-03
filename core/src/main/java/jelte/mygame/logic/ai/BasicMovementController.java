package jelte.mygame.logic.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.utility.Constants;

public class BasicMovementController implements AiMovementController {
	private static final String TAG = BasicMovementController.class.getSimpleName();

	protected Array<Vector2> patrolPositions = new Array<>();
	protected int currentPatrolPointIndex = 0;

	protected boolean isPointReached(AiCharacter self, Vector2 goal) {
		Gdx.app.log(TAG, "distance to goal : " + self.getPhysicsComponent().getPosition().dst(goal));
		return self.getPhysicsComponent().getPosition().dst(goal) <= Constants.CONTROL_POINT_REACHED_BUFFER_DISTANCE;
	}

	protected void shiftPatrolPoint() {
		currentPatrolPointIndex++;
		if (currentPatrolPointIndex >= patrolPositions.size) {
			currentPatrolPointIndex = 0;
		}
	}

	@Override
	public void moveTowardsGoal(AiCharacter self, Vector2 goal) {
		Vector2 direction = goal.cpy().sub(self.getPhysicsComponent().getPosition()).nor();
		Direction currentDirection = self.getPhysicsComponent().getDirection();
		if (direction.x < 0 && currentDirection == Direction.right) {
			Gdx.app.log(TAG, "RIGHT UNPRESSED");
			Gdx.app.log(TAG, "LEFT PRESSED");
			receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_UNPRESSED));
			receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.LEFT_PRESSED));
		} else if (direction.x > 0 && currentDirection == Direction.left) {
			Gdx.app.log(TAG, "LEFT UNPRESSED");
			Gdx.app.log(TAG, "RIGHT PRESSED");
			receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.LEFT_UNPRESSED));
			receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_PRESSED));
		}
	}

}
