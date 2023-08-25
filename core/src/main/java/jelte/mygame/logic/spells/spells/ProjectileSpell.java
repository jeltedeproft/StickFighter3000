package jelte.mygame.logic.spells.spells;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.physics.SpellPhysicsComponent;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellsEnum;

public class ProjectileSpell extends AbstractSpell {

	public ProjectileSpell(SpellData spellData, Character caster, Vector2 mousePosition) {
		super(spellData, caster);
		Vector2 casterPosition = caster.getPhysicsComponent().getPosition().cpy();
		Vector2 direction = mousePosition.cpy().sub(casterPosition).nor();
		Vector2 velocity = direction.scl(spellData.getSpeed());
		velocity.add(spellData.getVelocityX(), spellData.getVelocityY());

		SpellPhysicsComponent newPhysicsComponent = new SpellPhysicsComponent(id, SpellsEnum.values()[data.getId()], casterPosition);
		newPhysicsComponent.setDirection(direction);
		newPhysicsComponent.setVelocity(velocity);
		newPhysicsComponent.setAcceleration(new Vector2(spellData.getAccelerationX(), spellData.getAccelerationY()));
		physicsComponent = newPhysicsComponent;
	}

	@Override
	public void applyCollisionEffect(Character character) {
		if (character.getId() != casterId) {
			character.damage(data.getDamage());
		}
	}

	@Override
	protected void updateSpell(float delta, Character caster, Vector2 mousePosition) {
		physicsComponent.update(delta);
	}

}
