package jelte.mygame.logic.character;

import java.util.UUID;

import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.utility.Constants;

public class PlayerCharacter extends Character {
	private PlayerData data;

	public PlayerCharacter(PlayerData data, UUID id) {
		super(id);
		this.data = data;
		physicsComponent = new PlayerPhysicsComponent(id, Constants.PLAYER_START.cpy());
		physicsComponent.setPosition(Constants.PLAYER_START.cpy());
	}

}
