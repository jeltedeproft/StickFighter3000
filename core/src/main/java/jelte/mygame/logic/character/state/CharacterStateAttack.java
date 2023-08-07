package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.spells.SpellFileReader;

public class CharacterStateAttack implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float timer = 0f;
	private float duration;
	private CHARACTER_STATE state = CHARACTER_STATE.ATTACKING;

	public CharacterStateAttack(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		timer = duration;
		this.duration = duration;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_ATTACK);
		characterStateManager.makeSpellReady(SpellFileReader.getSpellData().get(0));// ;TODO make bounding box size of spell same as chosen attack animation
	}

	@Override
	public void update(float delta) {
		timer -= delta;
		if (timer <= 0) {
			timer = duration;
			characterStateManager.popState();
		}
	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case DAMAGE_TAKEN:
			characterStateManager.popState();
			characterStateManager.pushState(CHARACTER_STATE.HURT);
			break;
		case RIGHT_UNPRESSED, LEFT_UNPRESSED:
			characterStateManager.stopCharacter();
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
	public CHARACTER_STATE getState() {
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
