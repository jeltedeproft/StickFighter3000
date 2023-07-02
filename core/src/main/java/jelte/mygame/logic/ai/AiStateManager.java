package jelte.mygame.logic.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.character.NpcCharacter;
import jelte.mygame.utility.logging.MultiFileLogger;

public class AiStateManager {
	private static final String TAG = AiStateManager.class.getSimpleName();
	private AiState currentAiState;
	private AiState aiStateAttack;
	private AiState aiStatepatrol;
	private AiState aiStateIdle;
	private AiState aiStateChase;
	private AiState aiStateCast;

	private NpcCharacter enemy;
	private boolean stateChanged = false;
	private boolean stateChangedLastFrame = false;
	private AI_STATE previousAiState;

	public enum AI_STATE {
		IDLE,
		PATROL,
		CHASE,
		ATTACK,
		CAST;
	}

	public enum AI_EVENT {
		PLAYER_SEEN,
		PLAYER_IN_ATTACK_RANGE,
		PLAYER_OUT_ATTACK_RANGE,
		PLAYER_IN_CAST_RANGE,
		PLAYER_OUT_CAST_RANGE,
		PLAYER_LOST,
		START_PATROLLING;

	}

	public AiStateManager(NpcCharacter enemy) {
		this.enemy = enemy;
		aiStateAttack = new AiStateAttack(this);
		aiStatepatrol = new AiStatePatrol(this);
		aiStateIdle = new AiStateIdle(this);
		aiStateChase = new AiStateChase(this);
		aiStateCast = new AiStateCast(this);
		currentAiState = aiStateIdle;
		previousAiState = AI_STATE.IDLE;
	}

	public void update(float delta) {
		currentAiState.update(delta);
		updateStateChange();
		enemy.getPhysicsComponent().getCollidedWith().clear();
	}

	private void updateStateChange() {
		if (!stateChangedLastFrame && stateChanged) {
			stateChangedLastFrame = true;
		} else if (stateChangedLastFrame && stateChanged) {
			stateChanged = false;
			stateChangedLastFrame = false;
		}
	}

	public AiState getCurrentAiState() {
		return currentAiState;
	}

	public void transition(AI_STATE state) {
		MultiFileLogger logger = (MultiFileLogger) Gdx.app.getApplicationLogger();
		logger.log(TAG, "TRANSITION FROM " + currentAiState.getState() + " TO " + state);
		stateChanged = true;
		previousAiState = currentAiState.getState();
		currentAiState.exit();
		switch (state) {
		case ATTACK:
			currentAiState = aiStateAttack;
			break;
		case CAST:
			currentAiState = aiStateCast;
			break;
		case CHASE:
			currentAiState = aiStateChase;
			break;
		case IDLE:
			currentAiState = aiStateIdle;
			break;
		case PATROL:
			currentAiState = aiStatepatrol;
			break;
		default:
			break;

		}
		currentAiState.entry();
	}

	public void handleEvent(AI_EVENT event) {
		currentAiState.handleEvent(event);
	}

	public NpcCharacter getEnemy() {
		return enemy;
	}

	public boolean isStateChanged() {
		return stateChanged;
	}

	public AI_STATE getPreviousAiState() {
		return previousAiState;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("currentCharacterState : ");
		sb.append(currentAiState);

		return sb.toString();
	}

}
