package jelte.mygame.logic.character;

import java.util.UUID;

import com.badlogic.gdx.math.Vector3;

import jelte.mygame.Message;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.spells.Spell;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.utility.Constants;

public class PlayerCharacter extends Character {
	protected Vector3 lastKnownMouseCoords;

	public PlayerCharacter(CharacterData data, UUID id) {
		super(data, id);
		physicsComponent.setPosition(Constants.PLAYER_START.cpy());
	}

	@Override
	public void update(float delta) {
		super.update(delta);
	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case CAST_PRESSED:
			spellsToCast.addLast(new Spell(SpellFileReader.getSpellData().get((int) message.getValue()), physicsComponent.getPosition(), lastKnownMouseCoords));
			characterStateManager.handleEvent(EVENT.CAST_PRESSED);
			break;
		case SEND_MOUSE_COORDINATES:
			this.lastKnownMouseCoords = (Vector3) message.getValue();
			break;
		default:
			super.receiveMessage(message);
			break;
		}
	}

}
