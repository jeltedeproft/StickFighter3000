package jelte.mygame.logic.character;

import java.util.UUID;

import jelte.mygame.utility.Constants;

public class NpcCharacter extends Character {
	private PassiveAiStrategy aiStrategy;

	public NpcCharacter(CharacterData data, UUID id) {
		super(data, id);
		physicsComponent.setPosition(Constants.ENEMY_START.cpy());
		aiStrategy = new PassiveAiStrategy(this);
	}

	public void update(float delta, Character player) {
		super.update(delta);
		aiStrategy.update(delta, player);
	}

}
