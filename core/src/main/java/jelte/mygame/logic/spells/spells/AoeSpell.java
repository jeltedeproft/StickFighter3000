package jelte.mygame.logic.spells.spells;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.physics.SpellPhysicsComponent;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellsEnum;

public class AoeSpell extends AbstractSpell {
	private Vector2 direction;

	public AoeSpell(SpellData spellData, Character caster, Vector2 mousePosition) {
		super(spellData, caster, mousePosition);
		physicsComponent.setDirection(direction);
	}

	@Override
	protected SpellPhysicsComponent createSpellPhysicsComponent(UUID spellId, Vector2 casterPosition, Vector2 mousePosition, float speed, boolean goesTroughObstacles) {
		Vector2 velocity = new Vector2(0, 0);
		direction = caster.getPhysicsComponent().getDirection().equals(Direction.left) ? new Vector2(-1, 0) : new Vector2(1, 0);
		physicsComponent = new SpellPhysicsComponent(id, SpellsEnum.values()[data.getId()], casterPosition.cpy(), data.isGoesTroughObstacles(), direction);
		physicsComponent.setVelocity(velocity);
		return physicsComponent;
	}

	@Override
	public void applyEffect(Character character) {
		if (character.getId() != casterId) {
			character.damage(1f);// TODO add specific behaviour for different aoe spells
		}
	}

	@Override
	protected void updateSpell(Vector2 casterPosition, Vector2 mousePosition) {
		//
	}
}
