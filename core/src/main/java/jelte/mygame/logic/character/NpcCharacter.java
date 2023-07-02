package jelte.mygame.logic.character;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.graphical.map.PatrolPoint;
import jelte.mygame.logic.ai.BasicEnemyAiStrategy;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.physics.EnemyPhysicsComponent;
import jelte.mygame.utility.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NpcCharacter extends Character {
	private static final String TAG = NpcCharacter.class.getSimpleName();
	private Array<Vector2> patrolPositions;
	private BasicEnemyAiStrategy aiStrategy;
	private AI_STATE state = AI_STATE.PATROL;

	public enum AI_STATE {
		IDLE,
		PATROL,
		CHASE,
		ATTACK,
		CAST;
	}

	public NpcCharacter(EnemyData data, UUID id) {
		super(id);
		this.data = data;
		characterStateManager = new CharacterStateManager(this);
		currentHp = data.getMaxHP();
		patrolPositions = new Array<>();
		physicsComponent = new EnemyPhysicsComponent(id, Constants.PLAYER_START.cpy(), data.getVisionShapeWidth(), data.getVisionShapeHeight());
		physicsComponent.setPosition(Constants.ENEMY_START.cpy());
		aiStrategy = new BasicEnemyAiStrategy(this);
	}

	public void update(float delta, PlayerCharacter player) {
		super.update(delta);
		aiStrategy.update(delta, player, state);
	}

	public void addPatrolPoints(Array<PatrolPoint> patrolPoints) {
		patrolPositions.ensureCapacity(patrolPoints.size);
		for (PatrolPoint patrolPoint : patrolPoints) {
			patrolPositions.insert(Integer.parseInt(patrolPoint.getPathIndex()), patrolPoint.getPosition());
		}
	}

	public boolean checkPlayerVisibility(PlayerCharacter player) {
		EnemyPhysicsComponent physics = (EnemyPhysicsComponent) physicsComponent;
		return physics.getVisionRectangle().overlaps(player.getPhysicsComponent().getRectangle());
	}

}
