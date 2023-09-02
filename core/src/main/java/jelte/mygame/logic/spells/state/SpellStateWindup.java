package jelte.mygame.logic.spells.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.spells.state.SpellStateManager.SPELL_STATE;

public class SpellStateWindup implements SpellState {
	private static final String TAG = SpellStateWindup.class.getSimpleName();
	private SpellStateManager spellStateManager;
	private float timer = 0f;
	private SPELL_STATE state = SPELL_STATE.WINDUP;
	private float duration;
	private AudioEnum audioEnum;

	public SpellStateWindup(SpellStateManager spellStateManager, float duration) {
		this.spellStateManager = spellStateManager;
		timer = duration;
		this.duration = duration;
		audioEnum = AudioEnum.forName(String.format("SOUND_%s_%s", spellStateManager.getSpell().getName().toUpperCase(), state));

	}

	@Override
	public void entry() {
		if (audioEnum != null) {
			MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, audioEnum, spellStateManager.getSpell().getPhysicsComponent());
		}

	}

	@Override
	public void update(float delta) {
		timer -= delta;
		Gdx.app.debug(TAG, "timer = " + timer, null);
		if (timer <= 0) {
			timer = duration;
			spellStateManager.transition(SPELL_STATE.LOOP);
		}
	}

	@Override
	public void exit() {
		timer = duration;
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
		// TODO Auto-generated method stub

	}

}
