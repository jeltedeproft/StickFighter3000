package jelte.mygame.logic.character.state;

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManager.AudioCommand;
import jelte.mygame.graphical.audio.MusicManager.AudioEnum;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;

public class CharacterStateFallAttacking implements CharacterState {
	private CharacterStateManager characterStateManager;
	private float timer = 0f;
	private float duration;
	private STATE state = STATE.FALLATTACKING;

	public CharacterStateFallAttacking(CharacterStateManager characterStateManager, float duration) {
		this.characterStateManager = characterStateManager;
		timer = duration;
		this.duration = duration;
	}

	@Override
	public void entry() {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_ATTACK1);
	}

	@Override
	public void update(float delta) {
		timer -= delta;
		if (timer <= 0) {
			timer = duration;
			characterStateManager.transition(STATE.FALLING);
		}
	}

	@Override
	public void handleEvent(EVENT event) {
		switch (event) {
		case DAMAGE_TAKEN:
			characterStateManager.transition(STATE.HURT);
			break;
		default:
			break;

		}
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

	@Override
	public STATE getState() {
		return state;
	}

}
