package jelte.mygame.logic.ai;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.logic.ai.strategy.AiStrategy;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.BasicAiStrategy;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.collisions.collidable.Collidable;
import lombok.Getter;

@Getter
public class AiManager {

	private Map<AiCharacter, AiStrategy> charactersWithStrategies;

	public enum AI_STRATEGY {
		BASIC,
		AGGRESIVE,
		PASSIVE,
		KEEP_DISTANCE,
		PEACEFULL;
	}

	// INITIALIZATION

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

	// UPDATE

	public void update(float delta, PlayerCharacter player, Set<Collidable> collidables) {
		for (Entry<AiCharacter, AiStrategy> entry : charactersWithStrategies.entrySet()) {
			AiCharacter aiCharacter = entry.getKey();
			AiStrategy strategy = entry.getValue();
			AI_STATE oldState = aiCharacter.getState();
			AI_STATE newState = strategy.getNextState(delta, aiCharacter, player);
			transitionState(aiCharacter, oldState, newState);

			Array<Message> nextCommands = strategy.generateCommands(delta, aiCharacter, player);
			if (nextCommands != null) {
				for (Message message : nextCommands) {
					aiCharacter.receiveMessage(message);
				}
			}
		}
	}

	private void transitionState(AiCharacter aiCharacter, AI_STATE oldState, AI_STATE newState) {
		if (!oldState.equals(newState)) {
			transitionEffect(aiCharacter, oldState, newState);
		}
		aiCharacter.setState(newState);
	}

	private void transitionEffect(AiCharacter aiCharacter, AI_STATE oldState, AI_STATE newState) {
		if (newState == AI_STATE.ATTACK) {
			aiCharacter.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.ATTACK_PRESSED));
		}
		if (newState == AI_STATE.CAST) {
			aiCharacter.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.CAST_PRESSED, 2));// TODO configure this number somehow
		}
	}

}
