package jelte.mygame.logic.spells.state;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.spells.state.SpellStateManager.SPELL_STATE;

public class SpellStateLoop implements SpellState {
	private SpellStateManager spellStateManager;
	private float timer = 0f;
	private SPELL_STATE state = SPELL_STATE.LOOP;
	private float duration;
	private AudioEnum audioEnum;

	public SpellStateLoop(SpellStateManager spellStateManager, float duration) {
		this.spellStateManager = spellStateManager;
		timer = duration;
		this.duration = duration;
		audioEnum = AudioEnum.forName(String.format("SOUND_%s_%s", spellStateManager.getSpell().getName().toUpperCase(), state));
	}

	@Override
	public void entry() {
		if (audioEnum != null) {
			MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_LOOP, audioEnum, spellStateManager.getSpell().getPhysicsComponent());
		}
	}

	@Override
	public void update(float delta) {
		timer -= delta;
		if (timer <= 0) {
			timer = duration;
			spellStateManager.transition(SPELL_STATE.END);
		}
	}

	@Override
	public void exit() {
		timer = duration;
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_STOP, audioEnum);
	}

	@Override
	public SPELL_STATE getState() {
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

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case DAMAGE_TAKEN:
			break;
		case DIED:
			break;
		case NO_COLLISION:
			break;
		case TARGET_HIT:
			spellStateManager.transition(SPELL_STATE.END);
			break;
		default:
			break;

		}

	}

}
