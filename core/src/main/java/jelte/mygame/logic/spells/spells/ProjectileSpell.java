package jelte.mygame.logic.spells.spells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.physics.SpellPhysicsComponent;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellsEnum;
import jelte.mygame.logic.spells.state.SpellStateManager;
import jelte.mygame.utility.Constants;

public class ProjectileSpell extends AbstractSpell {
	private static final String TAG = ProjectileSpell.class.getSimpleName();

	public ProjectileSpell(SpellData spellData, Character caster, Vector2 mousePosition) {
		super(spellData, caster);
		Vector2 casterPosition = caster.getPhysicsComponent().getPosition().cpy();
		Vector2 direction = mousePosition.cpy().sub(casterPosition).nor();
		Vector2 velocity = calculateInitialVelocity(casterPosition, mousePosition, spellData.getDuration());
		Vector2 acceleration = calculateAcceleration(casterPosition, mousePosition, spellData.getDuration());

		SpellPhysicsComponent newPhysicsComponent = new SpellPhysicsComponent(id, SpellsEnum.values()[data.getId()], casterPosition);
		newPhysicsComponent.setDirection(direction);
		newPhysicsComponent.setVelocity(velocity);
		newPhysicsComponent.setAcceleration(acceleration);
		Gdx.app.error(TAG, "arrow direction = " + direction);
		Gdx.app.error(TAG, "arrow position = " + casterPosition);
		Gdx.app.error(TAG, "arrow velocity = " + velocity);
		Gdx.app.error(TAG, "arrow acceleration = " + acceleration);
		physicsComponent = newPhysicsComponent;
		spellStateManager = new SpellStateManager(this);
	}

	@Override
	public void applyCollisionEffect(Character character) {
		spellStateManager.getCurrentSpellState().handleEvent(EVENT.TARGET_HIT);
		if (character.getId() != casterId) {
			character.damage(data.getDamage());
			complete = true;
		}
	}

	@Override
	protected void updateSpell(float delta, Character caster, Vector2 endPosition) {
		physicsComponent.update(delta);
		Gdx.app.error(TAG, "arrow position = " + physicsComponent.getPosition());
		Gdx.app.error(TAG, "arrow velocity = " + physicsComponent.getVelocity());
		Gdx.app.error(TAG, "arrow acceleration = " + physicsComponent.getAcceleration());
	}

	private Vector2 calculateInitialVelocity(Vector2 startPosition, Vector2 endPosition, float time) {
		float xDistance = endPosition.x - startPosition.x;
		float yDistance = endPosition.y - startPosition.y;

		float initialVelocityX = xDistance / time;
		float initialVelocityY = yDistance / time;

		return new Vector2(initialVelocityX, initialVelocityY);
	}

	private Vector2 calculateAcceleration(Vector2 startPosition, Vector2 endPosition, float time) {
		float xDistance = endPosition.x - startPosition.x;
		float yDistance = endPosition.y - startPosition.y;

		float accelerationX = 2 * xDistance / (time * time);
		float accelerationY = 2 * yDistance / (time * time) - Constants.GRAVITY.y;

		return new Vector2(accelerationX, accelerationY);
	}

}
