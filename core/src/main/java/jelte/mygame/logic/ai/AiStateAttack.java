package jelte.mygame.logic.ai;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.ai.AiStateManager.AI_EVENT;
import jelte.mygame.logic.ai.AiStateManager.AI_STATE;
import jelte.mygame.logic.character.NpcCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.spells.SpellFileReader;

public class AiStateAttack implements AiState {
	private AiStateManager aiStateManager;
	private AI_STATE state = AI_STATE.ATTACK;

	public AiStateAttack(AiStateManager aiStateManager) {
		this.aiStateManager = aiStateManager;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.forName(String.format("SOUND_ATTACK_STATE_%s", aiStateManager.getEnemy().getName().toUpperCase())));
		aiStateManager.getEnemy().getSpellsreadyToCast().addLast(SpellFileReader.getSpellData().get(0));
	}

	@Override
	public void update(NpcCharacter self, PlayerCharacter player, float delta) {

	}

	@Override
	public void handleEvent(AI_EVENT event) {
		switch (event) {
		case PLAYER_LOST, START_PATROLLING:
			aiStateManager.transition(AI_STATE.PATROL);
			break;
		case PLAYER_OUT_ATTACK_RANGE, ATTACKED_PLAYER:
			aiStateManager.transition(AI_STATE.CHASE);
			break;
		case PLAYER_IN_ATTACK_RANGE, PLAYER_SEEN:
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
