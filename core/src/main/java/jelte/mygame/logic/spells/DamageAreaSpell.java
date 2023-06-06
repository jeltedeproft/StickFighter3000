package jelte.mygame.logic.spells;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.physics.SpellPhysicsComponent;

public class DamageAreaSpell extends AbstractSpell {

	public DamageAreaSpell(SpellData spellData, Vector2 casterPosition, Vector2 mousePosition, UUID casterId) {
		super(spellData, casterPosition, mousePosition, casterId);
	}

	@Override
	protected SpellPhysicsComponent createSpellPhysicsComponent(UUID spellId, Vector2 casterPosition, Vector2 mousePosition, float speed, boolean goesTroughObstacles) {
		Vector2 direction = mousePosition.cpy().sub(casterPosition).nor();
		Vector2 velocity = direction.scl(speed);

		physicsComponent = new SpellPhysicsComponent(id, SpellsEnum.values()[data.getId()], casterPosition.cpy(), data.isGoesTroughObstacles());
		physicsComponent.setVelocity(velocity);
		return physicsComponent;
	}

	@Override
	public void applyCollisionEffect(Character character) {
		if (character.getId() != casterId) {
			character.damage(1f);
		}
	}
}
