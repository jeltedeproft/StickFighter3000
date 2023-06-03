package jelte.mygame.logic.character;

import java.util.UUID;

import jelte.mygame.utility.Constants;

public class PlayerCharacter extends Character {

	public PlayerCharacter(CharacterData data, UUID id) {
		super(data, id);
		physicsComponent.setPosition(Constants.PLAYER_START.cpy());
	}

}
