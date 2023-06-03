package jelte.mygame.logic.spells;

import java.util.UUID;

import jelte.mygame.logic.physics.PhysicsComponent;

public interface Spell {

	public enum SPELL_TYPE {
		LASER, ON_MOUSE, PROJECTILE, OBJECT, SHIELD, EFFECT, SUMMON
	}

	public enum AFFECTS {
		FRIENDLY, ENEMY, BOTH, NONE
	}

	public void update(float delta);

	public UUID getId();

	public String getName();

	public String getSpriteName();

	public PhysicsComponent getPhysicsComponent();

	public boolean isComplete();

}
