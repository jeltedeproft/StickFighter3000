package jelte.mygame.tests.logic.physics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.physics.SpellPhysicsComponent;
import jelte.mygame.logic.spells.SpellsEnum;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestSpellPhysicsComponent {

	@Test
	public void testSpellPhysicsComponent() {
		UUID playerReference = UUID.randomUUID();
		SpellsEnum spellsEnum = SpellsEnum.FIREBALL;
		Vector2 startPosition = new Vector2(10, 10);

		SpellPhysicsComponent spellPhysicsComponent = new SpellPhysicsComponent(playerReference, spellsEnum, startPosition);

		assertEquals(playerReference, spellPhysicsComponent.getOwnerReference());
		assertEquals(spellsEnum, spellPhysicsComponent.getSpellsEnum());
		assertEquals(startPosition, spellPhysicsComponent.getPosition());
		assertEquals(new Vector2(), spellPhysicsComponent.getVelocity());
		assertFalse(spellPhysicsComponent.hasMoved());
	}

	@Test
	public void testUpdate() {
		UUID playerReference = UUID.randomUUID();
		SpellsEnum spellsEnum = SpellsEnum.FIREBALL;
		Vector2 startPosition = new Vector2(10, 10);

		SpellPhysicsComponent spellPhysicsComponent = new SpellPhysicsComponent(playerReference, spellsEnum, startPosition);
		spellPhysicsComponent.update(0.5f);

		assertTrue(spellPhysicsComponent.hasMoved());
		assertEquals(new Vector2(0, 0), spellPhysicsComponent.getAcceleration());
		assertEquals(new Vector2(0, -9.81f), spellPhysicsComponent.getVelocity());
		assertEquals(new Vector2(10, 5), spellPhysicsComponent.getPosition());
	}

	@Test
	public void testSetVelocity() {
		UUID playerReference = UUID.randomUUID();
		SpellsEnum spellsEnum = SpellsEnum.FIREBALL;
		Vector2 startPosition = new Vector2(10, 10);

		SpellPhysicsComponent spellPhysicsComponent = new SpellPhysicsComponent(playerReference, spellsEnum, startPosition);
		spellPhysicsComponent.setVelocityX(5);
		spellPhysicsComponent.setVelocityY(10);

		assertEquals(new Vector2(5, 10), spellPhysicsComponent.getVelocity());
	}

	@Test
	public void testUpdateScenarios() {
		float[][] testInputs = {
				{ 100.0f, 100.0f, 10.0f, 10.0f, 650f, 500f },
				{ 100.0f, 100.0f, 50.0f, 50.0f, 1250, 1100f },
				{ 100.0f, 100.0f, 100.0f, 100.0f, 2000f, 1850f },
				{ 100.0f, 100.0f, 100.0f, 100.0f, 2000f, 1850f },
				{ 100.0f, 100.0f, 150.0f, 150.0f, 2628.2f, 2485.3647f },
				{ 100.0f, 100.0f, 200.0f, 200.0f, 2946.4163f, 2821.26f },
				{ 50.0f, 50.0f, 10.0f, 10.0f, 400f, 250f },
				{ 200.0f, 200.0f, 50.0f, 50.0f, 1750f, 1600f },
				{ 300.0f, 300.0f, 200.0f, 200.0f, 3360.5945f, 3251.3013f }
		};

		UUID playerReference = UUID.randomUUID();
		SpellsEnum spellsEnum = SpellsEnum.FIREBALL;
		Vector2 startPosition = new Vector2(0, 0);

		for (int i = 0; i < testInputs.length; i++) {
			float[] input = testInputs[i];
			float vx = input[0];
			float vy = input[1];
			float ax = input[2];
			float ay = input[3];
			float expectedX = input[4];
			float expectedY = input[5];
			Vector2 velocity = new Vector2(vx, vy);
			Vector2 acceleration = new Vector2(ax, ay);

			SpellPhysicsComponent spellPhysicsComponent = new SpellPhysicsComponent(playerReference, spellsEnum, startPosition);
			spellPhysicsComponent.setVelocity(velocity);
			spellPhysicsComponent.setAcceleration(acceleration);
			spellPhysicsComponent.update(1);
			spellPhysicsComponent.update(1);
			spellPhysicsComponent.update(1);
			spellPhysicsComponent.update(1);
			spellPhysicsComponent.update(1);

			assertEquals(expectedX, spellPhysicsComponent.getPosition().x, 0.1f);
			assertEquals(expectedY, spellPhysicsComponent.getPosition().y, 0.1f);

			System.out.println("Scenario " + (i + 1));
			System.out.println("starting position: " + startPosition);
			System.out.println("velocity: " + velocity);
			System.out.println("acceleration: " + acceleration);
			System.out.println("position after: " + spellPhysicsComponent.getPosition());
			System.out.println("----------------------------------");
		}
	}

}
