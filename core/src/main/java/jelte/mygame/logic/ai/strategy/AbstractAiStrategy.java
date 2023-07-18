package jelte.mygame.logic.ai.strategy;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.movement.AiMovement;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;

public abstract class AbstractAiStrategy implements AiStrategy {
	protected AiMovement aiMovement;
	protected StateControllerInterface idleStateController;
	protected StateControllerInterface patrolStateController;
	protected StateControllerInterface chaseStateController;
	protected StateControllerInterface attackStateController;
	protected StateControllerInterface castStateController;
	protected StateControllerInterface fleeStateController;

	protected AbstractAiStrategy(StateControllerInterface idle, StateControllerInterface patrol, StateControllerInterface chase, StateControllerInterface attack, StateControllerInterface cast, StateControllerInterface flee) {
		idleStateController = idle;
		patrolStateController = patrol;
		chaseStateController = chase;
		attackStateController = attack;
		castStateController = cast;
		fleeStateController = flee;
	}

	@Override
	public Array<Message> generateCommands(float delta, AiCharacter self, PlayerCharacter player) {
		return switch (self.getState()) {
		case ATTACK -> attackStateController.getNextCommands(delta, self, player);
		case CAST -> castStateController.getNextCommands(delta, self, player);
		case CHASE -> chaseStateController.getNextCommands(delta, self, player);
		case FLEE -> fleeStateController.getNextCommands(delta, self, player);
		case IDLE -> idleStateController.getNextCommands(delta, self, player);
		case PATROL -> patrolStateController.getNextCommands(delta, self, player);
		default -> idleStateController.getNextCommands(delta, self, player);
		};
	}

	@Override
	public AI_STATE getNextState(float delta, AiCharacter self, PlayerCharacter player) {
		return switch (self.getState()) {
		case ATTACK -> attackStateController.getNextState(delta, self, player);
		case CAST -> castStateController.getNextState(delta, self, player);
		case CHASE -> chaseStateController.getNextState(delta, self, player);
		case FLEE -> fleeStateController.getNextState(delta, self, player);
		case IDLE -> idleStateController.getNextState(delta, self, player);
		case PATROL -> patrolStateController.getNextState(delta, self, player);
		default -> idleStateController.getNextState(delta, self, player);
		};
	}

}