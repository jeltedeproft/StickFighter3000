package jelte.mygame.logic.character;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.graphical.map.PatrolPoint;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.physics.EnemyPhysicsComponent;
import jelte.mygame.utility.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiCharacter extends Character {
	private static final String TAG = AiCharacter.class.getSimpleName();
	private Array<PatrolPoint> patrolPoints;
	private AI_STATE state = AI_STATE.PATROL;
	private boolean playerSeen = false;

	public AiCharacter(EnemyData data, UUID id, Vector2 spawnPoint, Array<PatrolPoint> patrolPoints) {
		super(id);
		this.data = data;
		this.patrolPoints = patrolPoints;
		characterStateManager = new CharacterStateManager(this);
		currentHp = data.getMaxHP();

		physicsComponent = new EnemyPhysicsComponent(id, Constants.PLAYER_START.cpy(), data.getVisionShapeWidth(), data.getVisionShapeHeight());
		physicsComponent.setPosition(spawnPoint);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		physicsComponent.getCollidedWith().clear();
	}

	@Override
	public EnemyData getData() {
		return (EnemyData) data;
	}

	public void playerSeen() {
		playerSeen = true;
	}

}
