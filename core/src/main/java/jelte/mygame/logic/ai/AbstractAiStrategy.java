package jelte.mygame.logic.ai;

import com.badlogic.gdx.Gdx;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.spells.SpellFileReader;

public abstract class AbstractAiStrategy implements AiStrategy {
	private static final String TAG = AbstractAiStrategy.class.getSimpleName();

	protected float timeSinceLastAttack = 0f;
	private AiMovementController movementController;
	private AiVisionController visionController;

	protected abstract AI_STATE updatePatrolState(float delta, PlayerCharacter player);

	protected abstract AI_STATE updateIdleState(float delta, PlayerCharacter player);

	protected abstract AI_STATE updateChaseState(float delta, PlayerCharacter player);

	protected abstract AI_STATE updateCastState(float delta, PlayerCharacter player);

	protected abstract AI_STATE updateAttackState(float delta, PlayerCharacter player);

	@Override
	public AI_STATE update(float delta, PlayerCharacter player, AI_STATE state) {
		Gdx.app.log(TAG, "UPDATING " + state);
		timeSinceLastAttack += delta;
		return switch (state) {
		case ATTACK -> updateAttackState(delta, player);
		case CAST -> updateCastState(delta, player);
		case CHASE -> updateChaseState(delta, player);
		case IDLE -> updateIdleState(delta, player);
		case PATROL -> updatePatrolState(delta, player);
		default -> state;
		};

	}

	protected void changeState(AI_STATE state) {
		switch (state) {
		case ATTACK:
			Gdx.app.log(TAG, "time since last attack : " + timeSinceLastAttack);
			Gdx.app.log(TAG, "attack cooldown : " + self.getData().getAttackCooldown());
			if (timeSinceLastAttack >= self.getData().getAttackCooldown()) {
				MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.forName(String.format("SOUND_ATTACK_STATE_%s", self.getName().toUpperCase())));
				self.getSpellsreadyToCast().addLast(SpellFileReader.getSpellData().get(0));
				timeSinceLastAttack = 0f;
				self.setState(AI_STATE.ATTACK);
			} else {
				self.setState(AI_STATE.PATROL);
			}
			break;
		case CAST:
			MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.forName(String.format("SOUND_CAST_STATE_%s", self.getName().toUpperCase())));
			self.setState(AI_STATE.CAST);
			break;
		case CHASE:
			MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.forName(String.format("SOUND_CHASE_STATE_%s", self.getName().toUpperCase())));
			self.setState(AI_STATE.CHASE);
			break;
		case IDLE:
			self.setState(AI_STATE.IDLE);
			break;
		case PATROL:
			self.setState(AI_STATE.PATROL);
			break;
		default:
			break;
		}
	}

}
