package jelte.mygame.logic.character;

import com.badlogic.gdx.utils.Array;

import java.util.UUID;

import jelte.mygame.Message;
import jelte.mygame.input.InputBox;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.logic.spells.SpellsEnum;
import jelte.mygame.utility.Constants;

public class PlayerCharacter extends Character {
	private Array<SpellsEnum> unlockedSpells;
	private boolean spellActivated;

	public PlayerCharacter(PlayerData data, UUID id) {
		super(id);
		unlockedSpells = new Array<>();
		this.data = data;
		characterStateManager = new CharacterStateManager(this);
		currentHp = data.getMaxHP();
		physicsComponent = new PlayerPhysicsComponent(id, Constants.PLAYER_START.cpy());
		physicsComponent.setPosition(Constants.PLAYER_START.cpy());
	}

	@Override
	public PlayerData getData() {
		return (PlayerData) data;
	}

	public Array<SpellsEnum> getUnlockedSpells() {
		return unlockedSpells;
	}

	public void unlockSpell(SpellsEnum spellsEnum) {
		unlockedSpells.add(spellsEnum);
		spellActivated = true;
	}

	@Override
	public void receiveMessage(Message message) {
		InputBox inputBox = (InputBox) message.getValue();
		switch (inputBox.getLastUsedButton()) {
		case SPELL0:
			SpellData data = SpellFileReader.getSpellData().get(0);
			if (isSpellUnlocked(data)) {
				super.receiveMessage(message);
			}
			break;
		default:
			super.receiveMessage(message);
		}
	}

	private boolean isSpellUnlocked(SpellData data) {
		return unlockedSpells.contains(SpellsEnum.valueOf(data.getName().toUpperCase()), false);
	}

	public boolean isSpellActivated() {
		return spellActivated;
	}

	public void setSpellActivated(boolean spellActivated) {
		this.spellActivated = spellActivated;
	}

}
