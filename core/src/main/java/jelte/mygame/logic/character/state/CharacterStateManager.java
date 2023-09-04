package jelte.mygame.logic.character.state;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.ArrayDeque;
import java.util.Deque;

import jelte.mygame.input.InputBox;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterData;
import jelte.mygame.logic.collisions.collidable.Collidable.COLLIDABLE_TYPE;
import jelte.mygame.logic.spells.SpellData;

public class CharacterStateManager {
	private static final String TAG = CharacterStateManager.class.getSimpleName();
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
	private CharacterState characterStatePreCasting;
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
	private CharacterState characterStateLandAttacking;
	private CharacterState characterStateClimbing;
	private CharacterState characterStateHoldingToSliding;
	private CharacterState characterStateJumpToFall;
	private Character character;
	private boolean stateChanged = false;
	private boolean stateChangedLastFrame = false;
	private Deque<CharacterState> stateStack = new ArrayDeque<>(6);// TODO is 3 a good size?

	public enum CHARACTER_STATE {
		ATTACKING,
		DIE,
		HURT,
		IDLE,
		JUMPING,
		JUMPTOFALL,
		FALLING,
		RUNNING,
		APPEARING,
		PRECAST,
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
		HOLDINGTOSLIDING,
		LANDATTACKING;
	}

	public enum EVENT {
		DAMAGE_TAKEN,
		DIED,
		NO_COLLISION,
		TARGET_HIT
	}

	public CharacterStateManager(Character character) {
		this.character = character;
		CharacterData data = character.getData();
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
		characterStatePreCasting = new CharacterStatePreCasting(this, data.getDefaultPreCastFullTime());
		characterStateCasting = new CharacterStateCasting(this, data.getDefaultCastFullTime());
		characterStateIdleCrouch = new CharacterStateIdleCrouch(this);
		characterStateHolding = new CharacterStateHolding(this);
		characterStateBlocking = new CharacterStateBlocking(this, data.getBlockingFullTime());
		characterStateTeleporting = new CharacterStateTeleporting(this, data.getTeleportFullTime());
		characterStateGrabbing = new CharacterStateGrabbing(this);
		characterStateRollAttacking = new CharacterStateRollAttacking(this, data.getRollAttackFullTime());
		characterStateRolling = new CharacterStateRolling(this, data.getRollFullTime());
		characterStateWalking = new CharacterStateWalking(this);
		characterStateSprinting = new CharacterStateSprinting(this);
		characterStateWallSliding = new CharacterStateWallSliding(this);
		characterStateWallSlidingStop = new CharacterStateWallSlidingStop(this, data.getWallSlidingStopFullTime());
		characterStateFallAttacking = new CharacterStateFallAttacking(this);
		characterStateLandAttacking = new CharacterStateLandAttacking(this, data.getLandAttackingFullTime());
		characterStateClimbing = new CharacterStateClimbing(this);
		characterStateHoldingToSliding = new CharacterStateHoldingToSliding(this, data.getHoldToSlideFullTime());
		characterStateJumpToFall = new CharacterStateJumpToFall(this);
		stateStack.addFirst(characterStateAppear);
		characterStateAppear.entry();
	}

	public void update(float delta) {
		if (!stateStack.isEmpty()) {
			CharacterState currentState = stateStack.peek();
			currentState.update(delta);
			updateStateChange();
		}
		character.getPhysicsComponent().getCollidedWith().clear();
	}

	private void updateStateChange() {
		if (!stateChangedLastFrame && stateChanged) {
			stateChangedLastFrame = true;
		} else if (stateChangedLastFrame && stateChanged) {
			stateChanged = false;
			stateChangedLastFrame = false;
		}
	}

	public CharacterState getCurrentCharacterState() {
		return stateStack.getFirst();
	}

	public void pushState(CHARACTER_STATE state) {
		stateChanged = true;
		if (!stateStack.isEmpty()) {
			CharacterState currentState = stateStack.peek();
			currentState.pauze();
		}
		CharacterState newstate = getStateFromEnum(state);
		stateStack.addFirst(newstate);
		newstate.entry();
	}

	public void popState() {
		if (!stateStack.isEmpty()) {
			CharacterState currentState = stateStack.pop();
			currentState.exit();
			if (!stateStack.isEmpty()) {
				CharacterState newTopState = stateStack.peek();
				newTopState.resume();
			}
		}
	}

	private CharacterState getStateFromEnum(CHARACTER_STATE state) {
		return switch (state) {
		case APPEARING -> characterStateAppear;
		case ATTACKING -> characterStateAttack;
		case DIE -> characterStateDie;
		case HURT -> characterStateHurt;
		case IDLE -> characterStateIdle;
		case JUMPING -> characterStateJumping;
		case RUNNING -> characterStateRunning;
		case CROUCHED -> characterStateCrouched;
		case STOPRUNNING -> characterStateStopRunning;
		case LANDING -> characterStateLanding;
		case FALLING -> characterStateFalling;
		case BLOCKING -> characterStateBlocking;
		case PRECAST -> characterStatePreCasting;
		case CAST -> characterStateCasting;
		case CLIMBING -> characterStateClimbing;
		case DASHING -> characterStateDashing;
		case FALLATTACKING -> characterStateFallAttacking;
		case LANDATTACKING -> characterStateLandAttacking;
		case GRABBING -> characterStateGrabbing;
		case HOLDING -> characterStateHolding;
		case IDLECROUCH -> characterStateIdleCrouch;
		case ROLLATTACK -> characterStateRollAttacking;
		case ROLLING -> characterStateRolling;
		case SPRINTING -> characterStateSprinting;
		case TELEPORTING -> characterStateTeleporting;
		case WALKING -> characterStateWalking;
		case WALLSLIDING -> characterStateWallSliding;
		case WALLSLIDINGSTOP -> characterStateWallSlidingStop;
		case HOLDINGTOSLIDING -> characterStateHoldingToSliding;
		case JUMPTOFALL -> characterStateJumpToFall;
		default -> throw new IllegalArgumentException("character state is not expected");
		};
	}

	public void handleInput(InputBox inputBox) {
		stateStack.getFirst().handleInput(inputBox);
	}

	public void handleEvent(EVENT event) {
		stateStack.getFirst().handleEvent(event);
	}

	public void startMovingOnTheGround(float speed) {
		character.getMovementManager().startMovingOnTheGround(speed);
	}

	public void continueMovingOnTheGround(float speed) {
		character.getMovementManager().continueMovingOnTheGround(speed);
	}

	public void stopMovingOnTheGround() {
		character.getMovementManager().stopMovingOnTheGround();
	}

	public void startMovingInTheAir(float speed) {
		character.getMovementManager().startMovingInTheAir(speed);
	}

	public void stopMovingInTheAir() {
		character.getMovementManager().stopMovingInTheAir();
	}

	public void climb(float climbSpeed) {
		character.getMovementManager().climb(climbSpeed);
	}

	public void applyJumpForce() {
		character.getMovementManager().applyJumpForce();
	}

	public void applyHorizontalForce(float distance) {
		character.getMovementManager().applyHorizontalForce(distance);
	}

	public void startJump() {
		character.getMovementManager().startJump();
	}

	public void setFallTrough(boolean fallTrough) {
		character.getMovementManager().setFallTrough(fallTrough);
	}

	public void pullDown(float fallAttackSpeedBoost) {
		character.getMovementManager().pullDown(fallAttackSpeedBoost);
	}

	public void grabLedge() {
		character.getMovementManager().grabLedge();
	}

	public boolean characterIsFalltrough() {
		return character.getMovementManager().characterIsFalltrough();
	}

	public boolean characterHaslanded() {
		return character.getMovementManager().characterHaslanded();
	}

	public boolean characterisFalling() {
		return character.getMovementManager().characterisFalling();
	}

	public boolean characterIsAtHighestPoint() {
		return character.getMovementManager().characterIsAtHighestPoint();
	}

	public boolean characterisStandingStill() {
		return character.getMovementManager().characterisStandingStill();
	}

	public boolean characterisRunning() {
		return character.getMovementManager().characterisRunning();
	}

	public Array<COLLIDABLE_TYPE> getCharacterCollisions() {
		return character.getPhysicsComponent().getCollidedWith();
	}

	public void makeSpellReady(SpellData spellData) {
		character.getSpellsreadyToCast().addLast(spellData);// ;TODO make bounding box size of spell same as chosen attack animation
	}

	public void addNextSpell() {
		if (!character.getSpellsPreparedToCast().isEmpty()) {
			character.getSpellsreadyToCast().addLast(character.getSpellsPreparedToCast().removeFirst());
		}
	}

	public Array<String> seeStateStack() {
		Array<String> stacks = new Array<>();
		for (CharacterState state : stateStack) {
			stacks.add(state.getState().name());
		}
		return stacks;
	}

	public void clearSpells() {
		character.getSpellsPreparedToCast().clear();
		character.getSpellsreadyToCast().clear();
	}

	public Character getCharacter() {
		return character;
	}

	public boolean isStateChanged() {
		return stateChanged;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("currentCharacterState");
		sb.append(" --> ");
		sb.append(stateStack.getFirst());
		return sb.toString();
	}
}