package jelte.mygame.logic.ai.strategy;

import jelte.mygame.logic.ai.stateControllers.AttackStateController;
import jelte.mygame.logic.ai.stateControllers.CastStateController;
import jelte.mygame.logic.ai.stateControllers.ChaseStateController;
import jelte.mygame.logic.ai.stateControllers.FleeStateController;
import jelte.mygame.logic.ai.stateControllers.IdleStateController;
import jelte.mygame.logic.ai.stateControllers.PatrolStateController;

public class BasicAiStrategy extends AbstractAiStrategy {

	public BasicAiStrategy() {
		super(new IdleStateController(), new PatrolStateController(), new ChaseStateController(), new AttackStateController(), new CastStateController(), new FleeStateController());
	}

}
