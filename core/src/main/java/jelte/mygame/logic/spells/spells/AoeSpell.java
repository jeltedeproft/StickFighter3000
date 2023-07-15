package jelte.mygame.logic.spells.spells;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.physics.SpellPhysicsComponent;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellsEnum;

public class AoeSpell extends AbstractSpell {
	private Vector2 direction;
	private Vector2 startingPosition;
	private boolean followsCaster;

	public AoeSpell(SpellData spellData, Character caster, Vector2 startingPosition, boolean followsCaster) {
		super(spellData, caster);
		this.startingPosition = startingPosition.cpy();
		this.followsCaster = followsCaster;
		SpellPhysicsComponent newPhysicsComponent = new SpellPhysicsComponent(id, SpellsEnum.values()[data.getId()], startingPosition);
		direction = caster.getPhysicsComponent().getDirection().equals(Direction.left) ? new Vector2(-1, 0) : new Vector2(1, 0);
		newPhysicsComponent.setDirection(direction);
		physicsComponent = newPhysicsComponent;
	}

	@Override
	public void applyCollisionEffect(Character character) {
		if (character.getId() != casterId) {
			character.damage(data.getDamage());// TODO add specific behaviour for different aoe spells
		}
	}

	@Override
	protected void updateSpell(float delta, Character character, Vector2 mousePosition) {
		if (followsCaster) {
			physicsComponent.setPosition(character.getPhysicsComponent().getPosition().cpy());
		}
	}
}
