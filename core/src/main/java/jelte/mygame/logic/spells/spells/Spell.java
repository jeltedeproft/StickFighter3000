package jelte.mygame.logic.spells.spells;

import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.physics.PhysicsComponent;

public interface Spell {

	public void update(float delta, Character caster, Vector2 mousePosition);

	public void applyCollisionEffect(Character character);

	public UUID getId();

	public String getName();

	public String getSpriteName();

	public PhysicsComponent getPhysicsComponent();

	public void handleEvent(EVENT event);

	public boolean isComplete();

	public enum SPELL_TYPE {
		PROJECTILE, BUFF, AOE, SUMMON
	}

	public enum AFFECTS {
		FRIENDLY, ENEMY, BOTH, NONE
	}

}
