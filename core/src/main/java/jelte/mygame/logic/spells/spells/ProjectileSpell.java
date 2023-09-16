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
		double speed = spellData.getSpeed();
		Vector2 casterPosition = caster.getPhysicsComponent().getPosition().cpy();
		double xDifference = mousePosition.x - casterPosition.x;
		double yDifference = mousePosition.y - casterPosition.y;
		Vector2 direction = mousePosition.cpy().sub(casterPosition).nor();
		Vector2[] velocities = calculateLaunchVectors(speed, -Constants.GRAVITY.y, xDifference, yDifference);
		Vector2 velocity = velocities[1];
		Gdx.app.error(TAG, "calculated arrow velocity = " + velocity);
		Gdx.app.error(TAG, "calculated arrow velocity alternative = " + velocities[0]);
		if (Float.isNaN(velocity.x) || Float.isNaN(velocity.y)) {
			Gdx.app.error(TAG, "calculating straight path");
			velocity = calculateStraightPath(casterPosition, mousePosition, speed);
			Gdx.app.error(TAG, "straight veloocty = " + velocity);
		}

		SpellPhysicsComponent newPhysicsComponent = new SpellPhysicsComponent(id, SpellsEnum.values()[data.getId()], casterPosition);
		newPhysicsComponent.setDirection(direction);
		newPhysicsComponent.setVelocity(velocity);
		physicsComponent = newPhysicsComponent;
		spellStateManager = new SpellStateManager(this);
	}

	private Vector2 calculateStraightPath(Vector2 casterPosition, Vector2 mousePosition, double speed) {
		float deltaX = mousePosition.x - casterPosition.x;
		float deltaY = mousePosition.y - casterPosition.y;
		return new Vector2(deltaX, deltaY).nor().scl((float) speed);
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
	}

	public Vector2[] calculateLaunchVectors(double v, double g, double x, double y) {
		double[] launchAngles = calculateLaunchAngles(v, g, x, y);
		Vector2[] launchVectors = new Vector2[2];

		for (int i = 0; i < 2; i++) {
			double angle = launchAngles[i];
			double velocityX = v * Math.cos(angle);
			double velocityY = v * Math.sin(angle);

			launchVectors[i] = new Vector2((float) velocityX, (float) velocityY);
		}

		return launchVectors;
	}

	public double[] calculateLaunchAngles(double v, double g, double x, double y) {
		// Calculate the discriminant
		double discriminant = v * v * v * v - g * (g * x * x + 2 * y * v * v);

		// Initialize an array to store the launch angles
		double[] angles = new double[2];

		if (discriminant >= 0) {
			// Calculate the two possible launch angles
			double angle1 = Math.atan((v * v + Math.sqrt(discriminant)) / (g * x));
			double angle2 = Math.atan((v * v - Math.sqrt(discriminant)) / (g * x));

			// Store the angles in the array
			angles[0] = angle1;
			angles[1] = angle2;
		} else {
			// No real solutions, set angles to NaN
			angles[0] = Double.NaN;
			angles[1] = Double.NaN;
		}

		return angles;
	}

}
