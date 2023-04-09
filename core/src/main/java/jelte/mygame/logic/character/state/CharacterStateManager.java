package jelte.mygame.logic.character.state;

import jelte.mygame.logic.character.Character;

public class CharacterStateManager {
	private CharacterState currentCharacterState;
	private CharacterState characterStateAttack;
	private CharacterState characterStateDie;
	private CharacterState characterStateHurt;
	private CharacterState characterStateIdle;
	private CharacterState characterStateJumping;
	private CharacterState characterStateFalling;
	private CharacterState characterStateRunning;
	private CharacterState characterStateStopRunning;
	private CharacterState characterStateAppear;
	private CharacterState characterStateCrouched;
	private CharacterState characterStateLanding;
	private CharacterState characterStateDashing;
	private Character character;

	public enum STATE {
		ATTACKING, DIE, HURT, IDLE, JUMPING, FALLING, RUNNING, APPEARING, CAST, CROUCHED, LANDING, STOPRUNNING, DASHING, CLIMBING, IDLECROUCH, HOLDING
	}

	public enum EVENT {
		DAMAGE_TAKEN, JUMP_PRESSED, ATTACK_PRESSED, DIED, DOWN_PRESSED, DOWN_UNPRESSED, LEFT_PRESSED, LEFT_UNPRESSED, RIGHT_PRESSED, RIGHT_UNPRESSED, JUMP_UNPRESSED, NO_COLLISION
	}

	public CharacterStateManager(Character character) {
		this.character = character;
		jelte.mygame.logic.character.CharacterData data = character.getData();
		characterStateAttack = new CharacterStateAttack(this, data.getAttackFullTime());
		characterStateDie = new CharacterStateDie(this);
		characterStateHurt = new CharacterStateHurt(this, data.getHurtFullTime());
		characterStateIdle = new CharacterStateIdle(this);
		characterStateJumping = new CharacterStateJumping(this);
		characterStateRunning = new CharacterStateRunning(this);
		characterStateStopRunning = new CharacterStateStopRunning(this, data.getStopRunningFullTime());
		characterStateDashing = new CharacterStateDashing(this, data.getDashingFullTime());
		characterStateCrouched = new CharacterStateCrouched(this);
		characterStateFalling = new CharacterStateFalling(this);
		characterStateLanding = new CharacterStateLanding(this, data.getLandingFullTime());
		characterStateAppear = new CharacterStateAppear(this, data.getAppearFullTime());
		currentCharacterState = characterStateAppear;
	}

	public void update(float delta) {
		currentCharacterState.update(delta);
	}

	public CharacterState getCurrentCharacterState() {
		return currentCharacterState;
	}

	public void transition(STATE state) {
		currentCharacterState.exit();
		switch (state) {
		case APPEARING:
			currentCharacterState = characterStateAppear;
			break;
		case ATTACKING:
			currentCharacterState = characterStateAttack;
			break;
		case DIE:
			currentCharacterState = characterStateDie;
			break;
		case HURT:
			currentCharacterState = characterStateHurt;
			break;
		case IDLE:
			currentCharacterState = characterStateIdle;
			break;
		case JUMPING:
			currentCharacterState = characterStateJumping;
			break;
		case RUNNING:
			currentCharacterState = characterStateRunning;
			break;
		case CROUCHED:
			currentCharacterState = characterStateCrouched;
			break;
		case STOPRUNNING:
			currentCharacterState = characterStateStopRunning;
			break;
		case LANDING:
			currentCharacterState = characterStateLanding;
			break;
		case FALLING:
			currentCharacterState = characterStateFalling;
			break;
		default:
			break;

		}
		currentCharacterState.entry();
	}

	public void handleEvent(EVENT event) {
		currentCharacterState.handleEvent(event);
	}

	public Character getCharacter() {
		return character;

	}

}
