package jelte.mygame.logic.character;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.graphical.map.PatrolPoint;
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

	public AiCharacter(EnemyData data, UUID id, Vector2 spawnPoint, Array<PatrolPoint> patrolPoints) {
		super(id);
		this.data = data;
		characterStateManager = new CharacterStateManager(this);
		currentHp = data.getMaxHP();

		physicsComponent = new EnemyPhysicsComponent(id, Constants.PLAYER_START.cpy(), data.getVisionShapeWidth(), data.getVisionShapeHeight());
		physicsComponent.setPosition(spawnPoint);
		this.patrolPoints = patrolPoints;
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

}
