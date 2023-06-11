package jelte.mygame.logic.spells.spells;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.physics.SpellPhysicsComponent;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellsEnum;

public class DefaultSpell extends AbstractSpell {

	public DefaultSpell(SpellData spellData, Character caster, Vector2 mousePosition) {
		super(spellData, caster, mousePosition);
	}

	@Override
	protected SpellPhysicsComponent createSpellPhysicsComponent(UUID spellId, Vector2 casterPosition, Vector2 mousePosition, float speed, boolean goesTroughObstacles) {
		Vector2 direction = mousePosition.cpy().sub(casterPosition).nor();
		Vector2 velocity = direction.scl(speed);

		physicsComponent = new SpellPhysicsComponent(id, SpellsEnum.values()[data.getId()], casterPosition.cpy(), data.isGoesTroughObstacles());
		physicsComponent.setVelocity(velocity);
		physicsComponent.setDirection(direction);
		return physicsComponent;
	}

	@Override
	public void applyCollisionEffect(Character character) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateSpell(Vector2 casterPosition, Vector2 mousePosition) {
		// TODO Auto-generated method stub

	}

}
