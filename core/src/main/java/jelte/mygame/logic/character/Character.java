package jelte.mygame.logic.character;

import java.util.UUID;

import com.badlogic.gdx.physics.box2d.Body;

import jelte.mygame.logic.Direction;
import jelte.mygame.logic.character.state.CharacterState;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
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

	public Character(CharacterData data, UUID id, Body characterBox2DBody) {
		this.id = id;
		this.data = data;
		box2DBody = characterBox2DBody;
		characterStateManager = new CharacterStateManager(this);
		currentDirection = Direction.right;
		currentHp = data.getMaxHP();
	}

	public void update(float delta) {
		characterStateManager.update(delta);
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

}
