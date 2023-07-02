package jelte.mygame.logic.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.logic.ai.AiStateManager.AI_EVENT;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.NpcCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.utility.Constants;

public class BasicEnemyAiStrategy implements AiStrategy {
	private static final String TAG = BasicEnemyAiStrategy.class.getSimpleName();
	private NpcCharacter self;
	private int currentPatrolPointIndex = 0;
	private float timeSinceLastCommand = 0f;

	public BasicEnemyAiStrategy(NpcCharacter self) {
		this.self = self;
	}

	@Override
	public void update(float delta, PlayerCharacter player, AiState state) {
		Gdx.app.log(TAG, "UPDATING " + state);
		timeSinceLastCommand += delta;
		switch (state.getState()) {
		case ATTACK:
			if (!self.getCurrentCharacterState().getState().equals(CHARACTER_STATE.ATTACKING)) {
				self.getAiStateManager().handleEvent(AI_EVENT.ATTACKED_PLAYER);
			}
			break;
		case CAST:
			break;
		case CHASE:
			Vector2 playerPosition = player.getPhysicsComponent().getPosition().cpy();
			if (isPointReached(self, playerPosition)) {
				self.getAiStateManager().handleEvent(AI_EVENT.PLAYER_IN_ATTACK_RANGE);
			} else {
				moveTowardsGoal(self, playerPosition);
			}
			break;
		case IDLE:
			if (self.checkPlayerVisibility(player)) {
				self.getAiStateManager().handleEvent(AI_EVENT.PLAYER_SEEN);
			}
			break;
		case PATROL:
			Vector2 goal = self.getPatrolPositions().get(currentPatrolPointIndex);
			if (isPointReached(self, goal)) {
				shiftPatrolPoint();
			} else {
				moveTowardsGoal(self, goal);
			}
			break;
		default:
			break;

		}
	}

	private boolean isPointReached(NpcCharacter self, Vector2 goal) {
		return self.getPhysicsComponent().getPosition().dst(goal) <= Constants.CONTROL_POINT_REACHED_BUFFER_DISTANCE;
	}

	private void shiftPatrolPoint() {
		currentPatrolPointIndex++;
		if (currentPatrolPointIndex >= self.getPatrolPositions().size) {
			currentPatrolPointIndex = 0;
		}
	}

	private void moveTowardsGoal(NpcCharacter self, Vector2 goal) {
		Vector2 direction = goal.cpy().sub(self.getPhysicsComponent().getPosition()).nor();
		Direction currentDirection = self.getPhysicsComponent().getDirection();
		if (direction.x < 0 && currentDirection == Direction.right) {
			sendMessage(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_UNPRESSED));
			sendMessage(new Message(RECIPIENT.LOGIC, ACTION.LEFT_PRESSED));
		} else if (direction.x > 0 && currentDirection == Direction.left) {
			sendMessage(new Message(RECIPIENT.LOGIC, ACTION.LEFT_UNPRESSED));
			sendMessage(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_PRESSED));
		}
	}

	@Override
	public void sendMessage(Message message) {
		self.receiveMessage(message);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("basic enemy ai strategy");
		return sb.toString();
	}

}
