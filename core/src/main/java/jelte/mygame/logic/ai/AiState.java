package jelte.mygame.logic.ai;

import jelte.mygame.logic.ai.AiStateManager.AI_EVENT;
import jelte.mygame.logic.ai.AiStateManager.AI_STATE;

public interface AiState {
	public void entry();

	public void update(float delta);

	public void handleEvent(AI_EVENT event);

	public void exit();

	public AI_STATE getState();

}
