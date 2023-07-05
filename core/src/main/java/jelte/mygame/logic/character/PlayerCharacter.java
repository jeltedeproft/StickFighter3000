package jelte.mygame.logic.character;

import java.util.UUID;

import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.utility.Constants;

public class PlayerCharacter extends Character {
	public PlayerCharacter(PlayerData data, UUID id) {
		super(id);
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

}
