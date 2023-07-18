package jelte.mygame.logic.ai.strategy.advanced;

import jelte.mygame.logic.ai.strategy.AbstractAiStrategy;
import jelte.mygame.logic.ai.strategy.advanced.stateControllers.AdvancedAttackStateController;
import jelte.mygame.logic.ai.strategy.advanced.stateControllers.AdvancedCastStateController;
import jelte.mygame.logic.ai.strategy.advanced.stateControllers.AdvancedChaseStateController;
import jelte.mygame.logic.ai.strategy.advanced.stateControllers.AdvancedFleeStateController;
import jelte.mygame.logic.ai.strategy.advanced.stateControllers.AdvancedIdleStateController;
import jelte.mygame.logic.ai.strategy.advanced.stateControllers.AdvancedPatrolStateController;

public class AdvancedAiStrategy extends AbstractAiStrategy {

	public AdvancedAiStrategy() {
		super(new AdvancedIdleStateController(), new AdvancedPatrolStateController(), new AdvancedChaseStateController(), new AdvancedAttackStateController(), new AdvancedCastStateController(), new AdvancedFleeStateController());
	}

}
