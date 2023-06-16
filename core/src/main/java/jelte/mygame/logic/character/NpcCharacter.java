package jelte.mygame.logic.character;

import java.util.UUID;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.AiStateManager;
import jelte.mygame.logic.ai.BasicEnemyAiStrategy;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.utility.Constants;

public class NpcCharacter extends Character {
	private BasicEnemyAiStrategy aiStrategy;
	private AiStateManager aiStateManager;

	public NpcCharacter(CharacterData data, UUID id) {
		super(data, id);
		physicsComponent.setPosition(Constants.ENEMY_START.cpy());
		aiStrategy = new BasicEnemyAiStrategy(this);
		aiStateManager = new AiStateManager(this);
	}

	public void update(float delta, Character player) {
		super.update(delta);
		aiStateManager.update(delta);
		aiStrategy.update(delta, player, aiStateManager.getCurrentAiState());
	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case DOWN_PRESSED:
			characterStateManager.handleEvent(EVENT.DOWN_PRESSED);
			break;
		case DOWN_UNPRESSED:
			characterStateManager.handleEvent(EVENT.DOWN_UNPRESSED);
			break;
		case LEFT_PRESSED:
			characterStateManager.handleEvent(EVENT.LEFT_PRESSED);
			break;
		case LEFT_UNPRESSED:
			characterStateManager.handleEvent(EVENT.LEFT_UNPRESSED);
			break;
		default:
			break;
		}
		super.receiveMessage(message);
	}

}
