package jelte.mygame.logic.ai.strategy.basic;

import jelte.mygame.logic.ai.strategy.AbstractAiStrategy;
import jelte.mygame.logic.ai.strategy.basic.stateControllers.BasicAttackStateController;
import jelte.mygame.logic.ai.strategy.basic.stateControllers.BasicCastStateController;
import jelte.mygame.logic.ai.strategy.basic.stateControllers.BasicChaseStateController;
import jelte.mygame.logic.ai.strategy.basic.stateControllers.BasicFleeStateController;
import jelte.mygame.logic.ai.strategy.basic.stateControllers.BasicIdleStateController;
import jelte.mygame.logic.ai.strategy.basic.stateControllers.BasicPatrolStateController;

public class BasicAiStrategy extends AbstractAiStrategy {

	public BasicAiStrategy() {
		super(new BasicIdleStateController(), new BasicPatrolStateController(), new BasicChaseStateController(), new BasicAttackStateController(), new BasicCastStateController(), new BasicFleeStateController());
	}

}
