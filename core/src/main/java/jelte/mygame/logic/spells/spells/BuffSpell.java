package jelte.mygame.logic.spells.spells;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.physics.SpellPhysicsComponent;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellsEnum;
import jelte.mygame.logic.spells.state.SpellStateManager;

public class BuffSpell extends AbstractSpell {
	private Character target;

	public BuffSpell(SpellData spellData, Character caster, Character target) {
		super(spellData, caster);
		this.target = target;

		SpellPhysicsComponent newPhysicsComponent = new SpellPhysicsComponent(id, SpellsEnum.values()[data.getId()], target.getPhysicsComponent().getPosition());
		newPhysicsComponent.setDirection(new Vector2(0, 0));
		newPhysicsComponent.setVelocity(new Vector2(0, 0));
		physicsComponent = newPhysicsComponent;

		for (String modifierName : spellData.getModifiers()) {
			target.getModifiersreadyToApply().addLast(modifierName);
		}
		spellStateManager = new SpellStateManager(this);
	}

	@Override
	public void applyCollisionEffect(Character target) {
		//
	}

	@Override
	protected void updateSpell(float delta, Character caster, Vector2 mousePosition) {
		physicsComponent.getPosition().x = target.getPhysicsComponent().getPosition().x;
		physicsComponent.getPosition().y = target.getPhysicsComponent().getPosition().y;
		physicsComponent.update(delta);
	}

}
