package jelte.mygame.logic.ai.strategy.archer;

import jelte.mygame.logic.ai.strategy.AbstractAiStrategy;
import jelte.mygame.logic.ai.strategy.archer.stateControllers.ArcherAttackStateController;
import jelte.mygame.logic.ai.strategy.archer.stateControllers.ArcherCastStateController;
import jelte.mygame.logic.ai.strategy.archer.stateControllers.ArcherChaseStateController;
import jelte.mygame.logic.ai.strategy.archer.stateControllers.ArcherFleeStateController;
import jelte.mygame.logic.ai.strategy.archer.stateControllers.ArcherIdleStateController;
import jelte.mygame.logic.ai.strategy.archer.stateControllers.ArcherPatrolStateController;

public class ArcherAiStrategy extends AbstractAiStrategy {

	public ArcherAiStrategy() {
		super(new ArcherIdleStateController(), new ArcherPatrolStateController(), new ArcherChaseStateController(), new ArcherAttackStateController(), new ArcherCastStateController(), new ArcherFleeStateController());
	}

}
