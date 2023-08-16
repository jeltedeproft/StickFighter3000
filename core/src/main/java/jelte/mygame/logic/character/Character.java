package jelte.mygame.logic.character;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.Objects;
import java.util.UUID;

import jelte.mygame.Message;
import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.character.input.CharacterInputHandler;
import jelte.mygame.logic.character.movement.MovementManagerImpl;
import jelte.mygame.logic.character.movement.MovementManagerInterface;
import jelte.mygame.logic.character.state.CharacterState;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.physics.CharacterPhysicsComponentImpl;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellFileReader;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Character {
	protected float currentHp;
	protected CharacterData data;
	protected UUID id;
	protected boolean dead;
	protected CharacterStateManager characterStateManager;
	protected CharacterPhysicsComponentImpl physicsComponent;
	protected MovementManagerInterface movementManager;
	protected CharacterInputHandler characterInputHandler;
	protected Queue<SpellData> spellsPreparedToCast;
	protected Queue<SpellData> spellsreadyToCast;
	protected Queue<String> modifiersreadyToApply;

	protected Character(UUID id) {
		this.id = id;
		characterInputHandler = new CharacterInputHandler();
		movementManager = new MovementManagerImpl(this);
		spellsPreparedToCast = new Queue<>();
		spellsreadyToCast = new Queue<>();
		modifiersreadyToApply = new Queue<>();
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
		InputBox inputBox = (InputBox) message.getValue();
		if (inputBox.getLastUsedButton() == BUTTONS.SPELL0) {
			spellsPreparedToCast.addLast(SpellFileReader.getSpellData().get(2));// TODO change to spell2
		}
		characterInputHandler.setInputBox(inputBox);
		characterStateManager.handleInput(inputBox);
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
