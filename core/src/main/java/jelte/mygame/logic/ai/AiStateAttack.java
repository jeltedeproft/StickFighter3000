package jelte.mygame.logic.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.ai.AiStateManager.AI_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.spells.SpellFileReader;

public class AiStateAttack implements AiState {
	private AiStateManager aiStateManager;
	private float timer = 0f;
	private float duration;
	private AI_STATE state = AI_STATE.ATTACK;

	public AiStateAttack(AiStateManager characterStateManager, float duration) {
		this.aiStateManager = characterStateManager;
		timer = duration;
		this.duration = duration;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_ATTACK);
		aiStateManager.getCharacter().getSpellsreadyToCast().addLast(SpellFileReader.getSpellData().get(0));// ;TODO make bounding box size of spell same as chosen attack animation
	}

	@Override
	public void update(float delta) {
		timer -= delta;
		if (timer <= 0) {
			timer = duration;
			aiStateManager.transition(CHARACTER_STATE.IDLE);
		}

	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case DAMAGE_TAKEN:
			aiStateManager.transition(CHARACTER_STATE.HURT);
			break;
		case RIGHT_UNPRESSED, LEFT_UNPRESSED:
			aiStateManager.getCharacter().getPhysicsComponent().getAcceleration().x = 0;
			aiStateManager.getCharacter().getPhysicsComponent().setVelocity(new Vector2(0, 0));
			break;
		default:
			break;

		}
	}

	@Override
	public void exit() {
		timer = duration;
	}

	@Override
	public AI_STATE getState() {
		return state;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("in ");
		sb.append(state.name());
		sb.append(" for ");
		sb.append(timer);
		sb.append(" more seconds ");

		return sb.toString();
	}

}
