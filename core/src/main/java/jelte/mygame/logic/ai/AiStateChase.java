package jelte.mygame.logic.ai;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.ai.AiStateManager.AI_EVENT;
import jelte.mygame.logic.ai.AiStateManager.AI_STATE;
import jelte.mygame.logic.character.NpcCharacter;
import jelte.mygame.logic.character.PlayerCharacter;

public class AiStateChase implements AiState {
	private AiStateManager aiStateManager;
	private AI_STATE state = AI_STATE.CHASE;

	public AiStateChase(AiStateManager aiStateManager) {
		this.aiStateManager = aiStateManager;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.forName(String.format("SOUND_CHASE_STATE_%s", aiStateManager.getEnemy().getName().toUpperCase())));
	}

	@Override
	public void update(NpcCharacter self, PlayerCharacter player, float delta) {

	}

	@Override
	public void handleEvent(AI_EVENT event) {
		switch (event) {
		case PLAYER_SEEN, PLAYER_OUT_ATTACK_RANGE, ATTACKED_PLAYER:
			break;
		case PLAYER_IN_ATTACK_RANGE:
			aiStateManager.transition(AI_STATE.ATTACK);
			break;
		case PLAYER_IN_CAST_RANGE:
			aiStateManager.transition(AI_STATE.CAST);
			break;
		case PLAYER_LOST, START_PATROLLING:
			aiStateManager.transition(AI_STATE.PATROL);
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
