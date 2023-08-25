package jelte.mygame.logic.ai;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.ai.strategy.AiStrategy;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.advanced.AdvancedAiStrategy;
import jelte.mygame.logic.ai.strategy.archer.ArcherAiStrategy;
import jelte.mygame.logic.ai.strategy.basic.BasicAiStrategy;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.collisions.collidable.Collidable;
import lombok.Getter;

@Getter
public class AiManager {

	private Map<AiCharacter, AiStrategy> charactersWithStrategies;

	public enum AI_STRATEGY {
		BASIC,
		ADVANCED,
		AGGRESIVE,
		PASSIVE,
		ARCHER,
		KEEP_DISTANCE,
		PEACEFULL;
	}

	public AiManager() {
		charactersWithStrategies = new HashMap<>();
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
		case ADVANCED -> new AdvancedAiStrategy();
		case ARCHER -> new ArcherAiStrategy();
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

			if (!oldState.equals(newState)) {
				// strategy.exitCurrentState(aiCharacter);
				// aiCharacter.setState(newState);
				// strategy.entryCurrentState(aiCharacter);
			}

			transitionState(aiCharacter, oldState, newState);

			Message nextCommand = strategy.generateCommand(delta, aiCharacter, player);
			if (nextCommand != null) {
				aiCharacter.receiveMessage(nextCommand);
			}
		}
	}

	private void transitionState(AiCharacter aiCharacter, AI_STATE oldState, AI_STATE newState) {
		if (!oldState.equals(newState)) {
			transitionEffect(aiCharacter, oldState, newState);
			aiCharacter.setState(newState);
		}

	}

	private void transitionEffect(AiCharacter aiCharacter, AI_STATE oldState, AI_STATE newState) {
		if (newState == AI_STATE.ATTACK) {
			aiCharacter.getInputBox().updateButtonPressed(BUTTONS.ATTACK, true);
			aiCharacter.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP, aiCharacter.getInputBox()));
			aiCharacter.getInputBox().updateButtonPressed(BUTTONS.ATTACK, false);
		}
		if (newState == AI_STATE.CAST) {
			aiCharacter.getInputBox().updateButtonPressed(BUTTONS.SPELL0, true);// TODO configure which spell somehow
			aiCharacter.receiveMessage(new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP, aiCharacter.getInputBox()));
			aiCharacter.getInputBox().updateButtonPressed(BUTTONS.SPELL0, false);
		}
	}

}
