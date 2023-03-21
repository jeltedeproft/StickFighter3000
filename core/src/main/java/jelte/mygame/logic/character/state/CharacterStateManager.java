package jelte.mygame.logic.character.state;

import jelte.mygame.logic.character.Character;

public class CharacterStateManager {
	private CharacterState currentCharacterState;
	private CharacterState characterStateAttack;
	private CharacterState characterStateDie;
	private CharacterState characterStateHurt;
	private CharacterState characterStateIdle;
	private CharacterState characterStateJumping;
	private CharacterState characterStateRunning;
	private CharacterState characterStateAppear;
	private Character character;

	public enum STATE {
		ATTACK, DIE, HURT, IDLE, JUMPING, RUNNING, APPEARING, CAST
	}

	public enum EVENT {
		DAMAGE_TAKEN, JUMP_PRESSED, MOVE_PRESSED, MOVE_UNPRESSED, ATTACK_PRESSED, LANDED, DIED
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
		case ATTACK:
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
		default:
			break;

		}
		currentCharacterState.entry();
	}

	public void handleEvent(EVENT event) {
		currentCharacterState.handleEvent(event);
	}

}
