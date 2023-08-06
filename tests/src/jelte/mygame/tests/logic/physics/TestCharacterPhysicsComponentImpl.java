package jelte.mygame.tests.logic.physics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.physics.CharacterPhysicsComponentImpl;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestCharacterPhysicsComponentImpl {

	@Test
	public void testUpdate() {
		UUID playerReference = UUID.randomUUID();
		Vector2 startPosition = new Vector2(0, 10);
		CharacterPhysicsComponentImpl characterPhysicsComponent = new PlayerPhysicsComponent(playerReference, startPosition);

		characterPhysicsComponent.update(0.1f);

		assertEquals(0f, characterPhysicsComponent.getAcceleration().x, 0.001f);
		assertEquals(0f, characterPhysicsComponent.getAcceleration().y, 0.001f);
		assertEquals(0f, characterPhysicsComponent.getVelocity().x, 0.001f);
		assertEquals(-10f, characterPhysicsComponent.getVelocity().y, 0.001f);
		assertEquals(new Vector2(0f, 9f), characterPhysicsComponent.getNewPosition());
		assertEquals(new Vector2(0f, 9f), characterPhysicsComponent.getPosition());
		assertTrue(characterPhysicsComponent.hasMoved());
	}

	@Test
	public void testSetVelocityY() {
		UUID playerReference = UUID.randomUUID();
		Vector2 startPosition = new Vector2(0, 0);
		CharacterPhysicsComponentImpl characterPhysicsComponent = new PlayerPhysicsComponent(playerReference, startPosition);

		characterPhysicsComponent.setVelocityY(5.0f);

		assertEquals(5.0f, characterPhysicsComponent.getVelocity().y, 0.001f);
	}

	@Test
	public void testSetVelocityX() {
		UUID playerReference = UUID.randomUUID();
		Vector2 startPosition = new Vector2(0, 0);
		CharacterPhysicsComponentImpl characterPhysicsComponent = new PlayerPhysicsComponent(playerReference, startPosition);

		characterPhysicsComponent.setVelocityX(2.0f);

		assertEquals(2.0f, characterPhysicsComponent.getVelocity().x, 0.001f);
	}

	@Test
	public void testSetPosition() {
		UUID playerReference = UUID.randomUUID();
		Vector2 startPosition = new Vector2(0, 0);
		CharacterPhysicsComponentImpl characterPhysicsComponent = new PlayerPhysicsComponent(playerReference, startPosition);

		Vector2 newPosition = new Vector2(5, 5);
		characterPhysicsComponent.setPosition(newPosition);

		assertEquals(newPosition, characterPhysicsComponent.getPosition());
	}

	@Test
	public void testGoesTroughObjects() {
		UUID playerReference = UUID.randomUUID();
		Vector2 startPosition = new Vector2(0, 0);
		CharacterPhysicsComponentImpl characterPhysicsComponent = new PlayerPhysicsComponent(playerReference, startPosition);

		assertFalse(characterPhysicsComponent.goesTroughWalls());
	}

	@Test
	public void testUpdatePosition() {
		UUID playerReference = UUID.randomUUID();
		Vector2 startPosition = new Vector2(0, 0);
		CharacterPhysicsComponentImpl characterPhysicsComponent = new PlayerPhysicsComponent(playerReference, startPosition);

		float newX = 10f;
		float newY = 5f;
		characterPhysicsComponent.setPosition(new Vector2(newX, newY));

		assertTrue(characterPhysicsComponent.hasMoved());
		assertEquals(newX, characterPhysicsComponent.getPosition().x, 0.001f);
		assertEquals(newY, characterPhysicsComponent.getPosition().y, 0.001f);
		assertEquals(new Rectangle(newX, newY, characterPhysicsComponent.getWidth(), characterPhysicsComponent.getHeight()), characterPhysicsComponent.getRectangle());
		assertEquals(Direction.right, characterPhysicsComponent.getDirection());
	}

	// Add more test cases for other methods as needed

}
