package jelte.mygame.logic.ai;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.collisions.collidable.Collidable;

public class AiManager {

	private Map<AiCharacter, AiStrategy> charactersWithStrategies;

	public enum AI_STRATEGY {
		BASIC
	}

	public void update(float delta, PlayerCharacter player, Set<Collidable> collidables) {
		for (Entry<AiCharacter, AiStrategy> entry : charactersWithStrategies.entrySet()) {
			for (Message message : entry.getValue().generateCommands(delta, entry.getKey(), player)) {
				entry.getKey().receiveMessage(message);
			}
		}
	}

	public void addEnemies(Array<AiCharacter> enemies) {
		for (AiCharacter enemy : enemies) {
			charactersWithStrategies.put(enemy, getStrategy(enemy));
		}
	}

	private AiStrategy getStrategy(AiCharacter enemy) {
		String strategyName = enemy.getData().getAiStrategyName();
		AI_STRATEGY strategyEnum = AI_STRATEGY.valueOf(strategyName);
		return switch (strategyEnum) {
		case BASIC -> new BasicAiStrategy();
		default -> new BasicAiStrategy();
		};
	}

}
