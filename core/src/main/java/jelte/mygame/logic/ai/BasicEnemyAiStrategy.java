package jelte.mygame.logic.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.character.NpcCharacter;
import jelte.mygame.logic.character.NpcCharacter.AI_STATE;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;

public class BasicEnemyAiStrategy extends AbstractAiStrategy {
	private static final String TAG = BasicEnemyAiStrategy.class.getSimpleName();

	public BasicEnemyAiStrategy(NpcCharacter self) {
		super(self);
	}

	@Override
	protected void updatePatrolState(float delta, PlayerCharacter player) {
		Vector2 goal = self.getPatrolPositions().get(currentPatrolPointIndex);
		if (isPointReached(self, goal)) {
			Gdx.app.log(TAG, "shifting patrol points");
			shiftPatrolPoint();
		} else {
			moveTowardsGoal(self, goal);
		}

		if (self.checkPlayerVisibility(player)) {
			changeState(AI_STATE.CHASE);
		}
	}

	@Override
	protected void updateIdleState(float delta, PlayerCharacter player) {
		if (self.checkPlayerVisibility(player)) {
			changeState(AI_STATE.CHASE);
		}
	}

	@Override
	protected void updateChaseState(float delta, PlayerCharacter player) {
		Vector2 playerPosition = player.getPhysicsComponent().getPosition().cpy();
		if (isPointReached(self, playerPosition)) {
			changeState(AI_STATE.ATTACK);
			timeSinceLastAttack = 0;
		} else {
			moveTowardsGoal(self, playerPosition);
		}
	}

	@Override
	protected void updateCastState(float delta, PlayerCharacter player) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateAttackState(float delta, PlayerCharacter player) {
		if (!self.getCurrentCharacterState().getState().equals(CHARACTER_STATE.ATTACKING)) {
			self.getPhysicsComponent().setVelocity(new Vector2(0, 0));
			changeState(AI_STATE.IDLE);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("basic enemy ai strategy");
		return sb.toString();
	}

}
