package jelte.mygame.logic.spells.spells;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.physics.SpellPhysicsComponent;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellsEnum;

public class BuffSpell extends AbstractSpell {

	public BuffSpell(SpellData spellData, Character caster, Vector2 mousePosition) {
		super(spellData, caster);
		Vector2 casterPosition = caster.getPhysicsComponent().getPosition().cpy();
		Vector2 direction = mousePosition.cpy().sub(casterPosition).nor();
		Vector2 velocity = direction.scl(spellData.getSpeed());

		SpellPhysicsComponent newPhysicsComponent = new SpellPhysicsComponent(id, SpellsEnum.values()[data.getId()], casterPosition);
		newPhysicsComponent.setDirection(direction);
		newPhysicsComponent.setVelocity(velocity);
		physicsComponent = newPhysicsComponent;
	}

	@Override
	public void applyEffect(Character character) {
		character.damage(1f);
	}

	@Override
	protected void updateSpell(float delta, Character caster, Vector2 mousePosition) {
		physicsComponent.update(delta);
	}

}
