package jelte.mygame.logic.character;

import java.util.UUID;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.AiStateManager;
import jelte.mygame.logic.ai.AiStateManager.AI_EVENT;
import jelte.mygame.logic.ai.BasicEnemyAiStrategy;
import jelte.mygame.logic.physics.EnemyPhysicsComponent;
import jelte.mygame.utility.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NpcCharacter extends Character {
	private EnemyData data;
	private BasicEnemyAiStrategy aiStrategy;
	private AiStateManager aiStateManager;

	public NpcCharacter(EnemyData data, UUID id) {
		super(id);
		this.data = data;
		physicsComponent = new EnemyPhysicsComponent(id, Constants.PLAYER_START.cpy(), data.getVisionShapeWidth(), data.getVisionShapeheigt());
		physicsComponent.setPosition(Constants.ENEMY_START.cpy());
		aiStrategy = new BasicEnemyAiStrategy(this);
		aiStateManager = new AiStateManager(this);
	}

	public void update(float delta, PlayerCharacter player) {
		super.update(delta);
		aiStateManager.update(delta);
		aiStrategy.update(delta, player, aiStateManager.getCurrentAiState());
	}

	public boolean checkPlayerVisibility(PlayerCharacter player) {
		EnemyPhysicsComponent physics = (EnemyPhysicsComponent) physicsComponent;
		return physics.getVisionRectangle().overlaps(player.getPhysicsComponent().getRectangle());
	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case PLAYER_SEEN:
			aiStateManager.handleEvent(AI_EVENT.PLAYER_SEEN);
			break;
		case IN_ATTACK_RANGE:
			aiStateManager.handleEvent(AI_EVENT.PLAYER_IN_ATTACK_RANGE);
			break;
		case OUT_ATTACK_RANGE:
			aiStateManager.handleEvent(AI_EVENT.PLAYER_OUT_ATTACK_RANGE);
			break;
		case IN_CAST_RANGE:
			aiStateManager.handleEvent(AI_EVENT.PLAYER_IN_CAST_RANGE);
			break;
		case OUT_CAST_RANGE:
			aiStateManager.handleEvent(AI_EVENT.PLAYER_OUT_CAST_RANGE);
			break;
		case LOST_PLAYER:
			aiStateManager.handleEvent(AI_EVENT.PLAYER_LOST);
			break;
		case START_PATROLLING:
			aiStateManager.handleEvent(AI_EVENT.START_PATROLLING);
			break;

		default:
			break;
		}
		super.receiveMessage(message);
	}

}
