package jelte.mygame.logic.ai;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.ai.AiStateManager.AI_EVENT;
import jelte.mygame.logic.ai.AiStateManager.AI_STATE;

public class AiStatePatrol implements AiState {
	private AiStateManager aiStateManager;
	private AI_STATE state = AI_STATE.PATROL;

	public AiStatePatrol(AiStateManager aiStateManager) {
		this.aiStateManager = aiStateManager;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.forName(String.format("SOUND_PATROL_STATE_%s", aiStateManager.getEnemy().getName().toUpperCase())));
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void handleEvent(AI_EVENT event) {
		switch (event) {
		case PLAYER_SEEN:
			aiStateManager.transition(AI_STATE.CHASE);
			break;
		case PLAYER_IN_ATTACK_RANGE:
			aiStateManager.transition(AI_STATE.ATTACK);
			break;
		case PLAYER_OUT_ATTACK_RANGE, PLAYER_LOST, START_PATROLLING:
			break;
		default:
			break;

		}
	}

	@Override
	public void exit() {

	}

	@Override
	public AI_STATE getState() {
		return state;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(state.name());
		return sb.toString();
	}

}
