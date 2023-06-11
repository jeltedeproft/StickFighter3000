package jelte.mygame.logic.character;

import java.util.Objects;
import java.util.UUID;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.Message;
import jelte.mygame.logic.character.state.CharacterState;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.physics.CharacterPhysicsComponent;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.utility.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Character {
	protected float currentHp;
	protected CharacterData data;
	protected UUID id;
	protected boolean dead;
	protected CharacterStateManager characterStateManager;
	protected CharacterPhysicsComponent physicsComponent;
	protected Queue<SpellData> spellsPreparedToCast;
	protected Queue<SpellData> spellsreadyToCast;

	public Character(CharacterData data, UUID id) {
		this.id = id;
		this.data = data;
		spellsPreparedToCast = new Queue<>();
		spellsreadyToCast = new Queue<>();
		characterStateManager = new CharacterStateManager(this);
		physicsComponent = new CharacterPhysicsComponent(id, Constants.PLAYER_START.cpy());
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
		case ATTACK_PRESSED:
			characterStateManager.handleEvent(EVENT.ATTACK_PRESSED);
			break;
		case CAST_PRESSED:
			spellsPreparedToCast.addLast(SpellFileReader.getSpellData().get((int) message.getValue()));
			characterStateManager.handleEvent(EVENT.CAST_PRESSED);
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
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("currentHp : ");
		sb.append(currentHp);
		sb.append("\n");

		sb.append("id : ");
		sb.append(id);
		sb.append("\n");

		sb.append("state : ");
		sb.append(characterStateManager.getCurrentCharacterState());
		sb.append("\n");

		sb.append(physicsComponent);
		sb.append("\n");

		return sb.toString();
	}

}
