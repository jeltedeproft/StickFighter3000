package jelte.mygame.logic.character;

import java.util.Objects;
import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;

import jelte.mygame.Message;
import jelte.mygame.logic.Collidable;
import jelte.mygame.logic.character.physics.PhysicsComponent;
import jelte.mygame.logic.character.physics.StandardPhysicsComponent;
import jelte.mygame.logic.character.state.CharacterState;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Character implements Collidable {
	protected float currentHp;
	protected CharacterData data;
	protected UUID id;
	protected boolean dead;
	protected CharacterStateManager characterStateManager;
	protected PhysicsComponent physicsComponent;

	public Character(CharacterData data, UUID id) {
		this.id = id;
		this.data = data;
		characterStateManager = new CharacterStateManager(this);
		physicsComponent = new StandardPhysicsComponent(id);
		currentHp = data.getMaxHP();
	}

	public void update(float delta) {
		characterStateManager.update(delta);
		physicsComponent.update(delta);
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
			characterStateManager.handleEvent(EVENT.LEFT_PRESSED);
			break;
		case LEFT_UNPRESSED:
			characterStateManager.handleEvent(EVENT.LEFT_UNPRESSED);
			break;
		case RIGHT_PRESSED:
			characterStateManager.handleEvent(EVENT.RIGHT_PRESSED);
			break;
		case RIGHT_UNPRESSED:
			characterStateManager.handleEvent(EVENT.RIGHT_UNPRESSED);
			break;
		case UP_PRESSED:
			characterStateManager.handleEvent(EVENT.JUMP_PRESSED);
			break;
		case ATTACK_PRESSED:
			characterStateManager.handleEvent(EVENT.ATTACK_PRESSED);
			break;
		case BLOCK_PRESSED:
			characterStateManager.handleEvent(EVENT.BLOCK_PRESSED);
			break;
		case BLOCK_UNPRESSED:
			characterStateManager.handleEvent(EVENT.BLOCK_UNPRESSED);
			break;
		case DASH_PRESSED:
			characterStateManager.handleEvent(EVENT.DASH_PRESSED);
			break;
		case ROLL_PRESSED:
			characterStateManager.handleEvent(EVENT.ROLL_PRESSED);
			break;
		case SPRINT_PRESSED:
			characterStateManager.handleEvent(EVENT.SPRINT_PRESSED);
			break;
		case SPRINT_UNPRESSED:
			characterStateManager.handleEvent(EVENT.SPRINT_UNPRESSED);
			break;
		case TELEPORT_PRESSED:
			characterStateManager.handleEvent(EVENT.TELEPORT_PRESSED);
			break;
		default:
			break;

		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Character other = (Character) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public Rectangle getRectangle() {
		return physicsComponent.getRectangle();
	}

}
