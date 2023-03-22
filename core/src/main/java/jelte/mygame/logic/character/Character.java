package jelte.mygame.logic.character;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import jelte.mygame.Message;
import jelte.mygame.logic.Direction;
import jelte.mygame.logic.character.state.CharacterState;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.Constants;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Character {
	@EqualsAndHashCode.Exclude
	private float currentHp;
	@EqualsAndHashCode.Exclude
	private Body box2DBody;
	@EqualsAndHashCode.Exclude
	private CharacterData data;
	private UUID id;
	@EqualsAndHashCode.Exclude
	private boolean dead;
	@EqualsAndHashCode.Exclude
	private CharacterStateManager characterStateManager;
	@EqualsAndHashCode.Exclude
	private Direction currentDirection;
	@EqualsAndHashCode.Exclude
	private Vector2 movementVector;

	public Character(CharacterData data, UUID id, Body characterBox2DBody) {
		this.id = id;
		this.data = data;
		movementVector = new Vector2(0, 0);
		box2DBody = characterBox2DBody;
		characterStateManager = new CharacterStateManager(this);
		currentDirection = Direction.right;
		currentHp = data.getMaxHP();
	}

	public void update(float delta) {
		box2DBody.applyLinearImpulse(movementVector, box2DBody.getPosition(), true);
		characterStateManager.update(delta, box2DBody);
	}

	public boolean damage(float amount) {
		characterStateManager.handleEvent(EVENT.DAMAGE_TAKEN);
		if (currentHp <= amount) {
			currentHp = 0;
			dead = true;
			characterStateManager.handleEvent(EVENT.DIED);
			return true;
		}
		currentHp -= amount;
		return false;
	}

	public void heal(float amount) {
		if (data.getMaxHP() <= currentHp + amount) {
			currentHp = data.getMaxHP();
		} else {
			currentHp += amount;
		}
	}

	public String getSpriteName() {
		return data.getEntitySpriteName();
	}

	public String getName() {
		return data.getName();
	}

	public CharacterState getCurrentCharacterState() {
		return characterStateManager.getCurrentCharacterState();
	}

	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case DOWN_PRESSED:
			characterStateManager.handleEvent(EVENT.DOWN_PRESSED);
			break;
		case DOWN_UNPRESSED:
			characterStateManager.handleEvent(EVENT.DOWN_UNPRESSED);
			break;
		case LEFT_PRESSED:
			currentDirection = Direction.left;
			move(-Constants.MOVEMENT_SPEED, 0);
			break;
		case LEFT_UNPRESSED:
			move(Constants.MOVEMENT_SPEED, 0);
			break;
		case RIGHT_PRESSED:
			currentDirection = Direction.right;
			move(Constants.MOVEMENT_SPEED, 0);
			break;
		case RIGHT_UNPRESSED:
			move(-Constants.MOVEMENT_SPEED, 0);
			break;
		case UP_PRESSED:
			// box2DBody.applyLinearImpulse(Constants.JUMP_SPEED, box2DBody.getPosition(), true);
			if (characterStateManager.getCurrentCharacterState().getState() != STATE.JUMPING && characterStateManager.getCurrentCharacterState().getState() != STATE.FALLING) {
				box2DBody.setLinearVelocity(box2DBody.getLinearVelocity().x, 5000);
				characterStateManager.handleEvent(EVENT.JUMP_PRESSED);
			}
			break;
		default:
			break;

		}
	}

	private void move(float x, int y) {
		movementVector.add(x, y);
		if (!movementVector.epsilonEquals(0, 0)) {
			characterStateManager.handleEvent(EVENT.MOVE_PRESSED);
		} else {
			characterStateManager.handleEvent(EVENT.MOVE_UNPRESSED);
		}
	}

}
