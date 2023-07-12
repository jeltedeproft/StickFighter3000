package jelte.mygame.tests.logic.physics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

		assertFalse(spellPhysicsComponent.hasMoved());
		assertEquals(new Vector2(0, 0), spellPhysicsComponent.getAcceleration());
		assertEquals(new Vector2(0, 0), spellPhysicsComponent.getVelocity());
		assertEquals(new Vector2(10, 10), spellPhysicsComponent.getPosition());
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

	// Add more test cases for other methods as needed

}
