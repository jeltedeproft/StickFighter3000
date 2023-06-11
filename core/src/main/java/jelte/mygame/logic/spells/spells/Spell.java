package jelte.mygame.logic.spells.spells;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.physics.PhysicsComponent;

public interface Spell {

	public enum SPELL_TYPE {
		LASER, ON_MOUSE, PROJECTILE, OBJECT, SHIELD, EFFECT, SUMMON
	}

	public enum AFFECTS {
		FRIENDLY, ENEMY, BOTH, NONE
	}

	public void update(float delta, Character caster, Vector2 mousePosition);

	public UUID getId();

	public String getName();

	public String getSpriteName();

	public PhysicsComponent getPhysicsComponent();

	public boolean isComplete();

	public void applyCollisionEffect(Character character);

}
