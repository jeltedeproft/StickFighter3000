package jelte.mygame.logic.character;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.graphical.map.PatrolPoint;
import jelte.mygame.logic.ai.AiStrategy;
import jelte.mygame.logic.ai.AiStrategy.AI_COMMAND;
import jelte.mygame.logic.ai.BasicEnemyAiStrategy;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.physics.EnemyPhysicsComponent;
import jelte.mygame.utility.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiCharacter extends Character {
	private static final String TAG = AiCharacter.class.getSimpleName();
	private AiStrategy aiStrategy;
	private float attackCooldown;
	private AI_COMMAND previousCommand = AI_COMMAND.;

	public AiCharacter(EnemyData data, UUID id, Vector2 spawnPoint, Array<PatrolPoint> patrolPoints) {
		super(id);
		this.data = data;
		attackCooldown = data.getAttackCooldown();
		characterStateManager = new CharacterStateManager(this);
		currentHp = data.getMaxHP();

		physicsComponent = new EnemyPhysicsComponent(id, Constants.PLAYER_START.cpy(), data.getVisionShapeWidth(), data.getVisionShapeHeight());
		physicsComponent.setPosition(spawnPoint);
		aiStrategy = new BasicEnemyAiStrategy(patrolPoints);
	}

	public void update(float delta, PlayerCharacter player) {
		super.update(delta);
		executeNextCommand(aiStrategy.decideNextMove(delta, this, player));
		physicsComponent.getCollidedWith().clear();
	}

	private void executeNextCommand(AI_COMMAND nextMove) {
		switch (nextMove) {
		case ATTACK:
			break;
		case CAST:
			break;
		case JUMP:
			break;
		case MOVE_RIGHT:
			break;
		case MOVE_LEFT:
			break;
		case STOP_MOVE:
			break;
		default:
			break;

		}
	}

}
