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
		Vector2 velocity = new Vector2();
//		if (!solveBallisticArc2D(casterPosition, spellData.getSpeed(), mousePosition, 50f, velocity, -Constants.GRAVITY.y)) {
//			velocity = calculateInitialVelocity(casterPosition, mousePosition);
//		}

		Vector2 acceleration = new Vector2();
		calculateProjectile(casterPosition, mousePosition, -Constants.GRAVITY.y, 0f, velocity, acceleration);

		SpellPhysicsComponent newPhysicsComponent = new SpellPhysicsComponent(id, SpellsEnum.values()[data.getId()], casterPosition);
		newPhysicsComponent.setDirection(direction);
		newPhysicsComponent.setVelocity(velocity);
		newPhysicsComponent.setAcceleration(acceleration);
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

	public Vector2 calculateInitialVelocity(Vector2 startPosition, Vector2 endPosition) {
		float horizontalDistance = endPosition.x - startPosition.x;
		float verticalDistance = endPosition.y - startPosition.y;
		float timeOfFlight = 2 * verticalDistance / Constants.GRAVITY.y;

		float initialVelocityX = horizontalDistance / timeOfFlight;
		float initialVelocityY = (verticalDistance + 0.5f * Constants.GRAVITY.y * timeOfFlight * timeOfFlight) / timeOfFlight;

		return new Vector2(initialVelocityX, initialVelocityY);
	}

	public static boolean solveBallisticArc2D(Vector2 projPos, float lateralSpeed, Vector2 targetPos, float maxHeight, Vector2 fireVelocity, float gravity) {
		// Handling these cases is up to your project's coding standards
		assert !projPos.equals(targetPos) && lateralSpeed > 0 && maxHeight > projPos.y : "solveBallisticArc2D called with invalid data";

		Vector2 diff = targetPos.sub(projPos);
		float lateralDist = diff.x;

		if (lateralDist == 0) {
			return false;
		}

		float time = lateralDist / lateralSpeed;

		fireVelocity.set(lateralSpeed, 0f); // 2D velocity only along the X-axis

		// System of equations for 2D. Hit maxHeight at t=.5*time. Hit target at t=time.
		// Simplified equations compared to the 3D version.
		float a = projPos.y; // initial
		float b = maxHeight; // peak
		float c = targetPos.y; // final

		gravity = -4 * (a - 2 * b + c) / (time * time);
		fireVelocity.y = -(3 * a - 4 * b + c) / time;

		return true;
	}

	public static void calculateProjectile(Vector2 startPos, Vector2 landingPos, float gravity, float timeStep, Vector2 initialVelocity, Vector2 initialAcceleration) {
		float displacementX = landingPos.x - startPos.x;
		float displacementY = landingPos.y - startPos.y;

		// Calculate the time of flight using quadratic formula
		float a = 0.5f * gravity;
		float b = -initialVelocity.y;
		float c = -displacementY;

		float discriminant = b * b - 4 * a * c;

		if (discriminant < 0) {
			Gdx.app.error(TAG, "no valid solution for ballistic trajectory");
			return;
		}

		float timeOfFlight = (-b + (float) Math.sqrt(discriminant)) / (2 * a);

		float velocityX = displacementX / timeOfFlight;
		float velocityY = displacementY / timeOfFlight;
		float accelerationX = 2 * (displacementX - initialVelocity.x * timeOfFlight) / (timeOfFlight * timeOfFlight);
		float accelerationY = gravity;

		initialVelocity.set(velocityX, velocityY);
		initialAcceleration.set(accelerationX, accelerationY);
	}

}
