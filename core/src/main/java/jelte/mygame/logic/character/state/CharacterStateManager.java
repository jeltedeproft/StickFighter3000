package jelte.mygame.logic.character.state;

import java.util.Stack;

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
	private CharacterState characterStateCasting;
	private CharacterState characterStateIdleCrouch;
	private CharacterState characterStateHolding;
	private CharacterState characterStateBlocking;
	private CharacterState characterStateTeleporting;
	private CharacterState characterStateGrabbing;
	private CharacterState characterStateRollAttacking;
	private CharacterState characterStateRolling;
	private CharacterState characterStateWalking;
	private CharacterState characterStateSprinting;
	private CharacterState characterStateWallSliding;
	private CharacterState characterStateWallSlidingStop;
	private CharacterState characterStateFallAttacking;
	private CharacterState characterStateClimbing;
	private CharacterState characterStateHoldingToSliding;
	private CharacterState characterStateJumpToFall;
	private Character character;
	private Stack<EVENT> pressedKeysBuffer = new Stack<>();

	public enum STATE {
		ATTACKING,
		DIE,
		HURT,
		IDLE,
		JUMPING,
		JUMPTOFALL,
		FALLING,
		RUNNING,
		APPEARING,
		CAST,
		CROUCHED,
		LANDING,
		STOPRUNNING,
		CLIMBING,
		DASHING,
		IDLECROUCH,
		HOLDING,
		BLOCKING,
		TELEPORTING,
		GRABBING,
		ROLLATTACK,
		ROLLING,
		WALKING,
		SPRINTING,
		WALLSLIDING,
		WALLSLIDINGSTOP,
		FALLATTACKING,
		HOLDINGTOSLIDING;
	}

	public enum EVENT {
		DAMAGE_TAKEN,
		JUMP_PRESSED,
		ATTACK_PRESSED,
		DIED,
		DOWN_PRESSED,
		DOWN_UNPRESSED,
		LEFT_PRESSED,
		LEFT_UNPRESSED,
		RIGHT_PRESSED,
		RIGHT_UNPRESSED,
		JUMP_UNPRESSED,
		NO_COLLISION,
		DASH_PRESSED,
		ROLL_PRESSED,
		TELEPORT_PRESSED,
		BLOCK_PRESSED,
		BLOCK_UNPRESSED,
		SPRINT_PRESSED,
		SPRINT_UNPRESSED;
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
		characterStateCasting = new CharacterStateCasting(this, data.getDefaultCastFullTime());
		characterStateIdleCrouch = new CharacterStateIdleCrouch(this);
		characterStateHolding = new CharacterStateHolding(this);
		characterStateBlocking = new CharacterStateBlocking(this);
		characterStateTeleporting = new CharacterStateTeleporting(this, data.getTeleportFullTime());
		characterStateGrabbing = new CharacterStateGrabbing(this);
		characterStateRollAttacking = new CharacterStateRollAttacking(this, data.getRollAttackFullTime());
		characterStateRolling = new CharacterStateRolling(this, data.getRollFullTime());
		characterStateWalking = new CharacterStateWalking(this);
		characterStateSprinting = new CharacterStateSprinting(this);
		characterStateWallSliding = new CharacterStateWallSliding(this);
		characterStateWallSlidingStop = new CharacterStateWallSlidingStop(this, data.getWallSlidingStopFullTime());
		characterStateFallAttacking = new CharacterStateFallAttacking(this, data.getFallAttackingFullTime());
		characterStateClimbing = new CharacterStateClimbing(this);
		characterStateHoldingToSliding = new CharacterStateHoldingToSliding(this, data.getHoldToSlideFullTime());
		characterStateJumpToFall = new CharacterStateJumpToFall(this);
		currentCharacterState = characterStateAppear;
	}

	public void update(float delta) {
		currentCharacterState.update(delta);
	}

	public CharacterState getCurrentCharacterState() {
		return currentCharacterState;
	}

	public void transition(STATE state) {
		System.out.println("changing from " + currentCharacterState.getState() + " to " + state);
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
		case BLOCKING:
			currentCharacterState = characterStateBlocking;
			break;
		case CAST:
			currentCharacterState = characterStateCasting;
			break;
		case CLIMBING:
			currentCharacterState = characterStateClimbing;
			break;
		case DASHING:
			currentCharacterState = characterStateDashing;
			break;
		case FALLATTACKING:
			currentCharacterState = characterStateFallAttacking;
			break;
		case GRABBING:
			currentCharacterState = characterStateGrabbing;
			break;
		case HOLDING:
			currentCharacterState = characterStateHolding;
			break;
		case IDLECROUCH:
			currentCharacterState = characterStateIdleCrouch;
			break;
		case ROLLATTACK:
			currentCharacterState = characterStateRollAttacking;
			break;
		case ROLLING:
			currentCharacterState = characterStateRolling;
			break;
		case SPRINTING:
			currentCharacterState = characterStateSprinting;
			break;
		case TELEPORTING:
			currentCharacterState = characterStateTeleporting;
			break;
		case WALKING:
			currentCharacterState = characterStateWalking;
			break;
		case WALLSLIDING:
			currentCharacterState = characterStateWallSliding;
			break;
		case WALLSLIDINGSTOP:
			currentCharacterState = characterStateWallSlidingStop;
			break;
		case HOLDINGTOSLIDING:
			currentCharacterState = characterStateHoldingToSliding;
			break;
		case JUMPTOFALL:
			currentCharacterState = characterStateJumpToFall;
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

	public Stack<EVENT> getStack() {
		return pressedKeysBuffer;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("currentCharacterState");
		sb.append(" --> ");
		sb.append(currentCharacterState);

		return sb.toString();
	}

}
