package jelte.mygame.logic.spells.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.spells.state.SpellStateManager.SPELL_STATE;

public class SpellStateEnd implements SpellState {
	private static final String TAG = SpellStateEnd.class.getSimpleName();
	private SpellStateManager spellStateManager;
	private float timer = 0f;
	private SPELL_STATE state = SPELL_STATE.END;
	private float duration;
	private AudioEnum audioEnum;

	public SpellStateEnd(SpellStateManager spellStateManager, float duration) {
		this.spellStateManager = spellStateManager;
		timer = duration;
		this.duration = duration;
		audioEnum = AudioEnum.forName(String.format("SOUND_%s_%s", spellStateManager.getSpell().getName().toUpperCase(), state));

	}

	@Override
	public void entry() {
		spellStateManager.getSpell().getPhysicsComponent().setVelocity(new Vector2(0, 0));
		spellStateManager.getSpell().getPhysicsComponent().setAcceleration(new Vector2(0, 10));
		if (audioEnum != null) {
			Gdx.app.error(TAG, "playing sound", null);
			MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, audioEnum, spellStateManager.getSpell().getPhysicsComponent());
		}
	}

	@Override
	public void update(float delta) {
		timer -= delta;
		if (timer <= 0) {
			timer = duration;
			spellStateManager.transition(SPELL_STATE.DEAD);
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
