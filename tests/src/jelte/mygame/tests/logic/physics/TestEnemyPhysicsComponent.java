package jelte.mygame.tests.logic.physics;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.collisions.collidable.Collidable.COLLIDABLE_TYPE;
import jelte.mygame.logic.physics.EnemyPhysicsComponent;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestEnemyPhysicsComponent {

	@Test
	public void testUpdatePosition() {
		UUID playerReference = UUID.randomUUID();
		Vector2 startPosition = new Vector2(0f, 0f);
		int visionWidth = 100;
		int visionHeight = 50;

		EnemyPhysicsComponent enemyPhysicsComponent = new EnemyPhysicsComponent(playerReference, startPosition, visionWidth, visionHeight);

		float newX = 10f;
		float newY = 20f;

		enemyPhysicsComponent.setPosition(new Vector2(newX, newY));

		Rectangle visionRectangle = enemyPhysicsComponent.getVisionRectangle();

		assertEquals(newX, visionRectangle.x, 0.001f);
		assertEquals(newY, visionRectangle.y, 0.001f);
		assertEquals(visionWidth, visionRectangle.width, 0.001f);
		assertEquals(visionHeight, visionRectangle.height, 0.001f);
	}

	@Test
	public void testGetType() {
		UUID playerReference = UUID.randomUUID();
		Vector2 startPosition = new Vector2(0f, 0f);
		int visionWidth = 100;
		int visionHeight = 50;

		EnemyPhysicsComponent enemyPhysicsComponent = new EnemyPhysicsComponent(playerReference, startPosition, visionWidth, visionHeight);

		assertEquals(COLLIDABLE_TYPE.ENEMY, enemyPhysicsComponent.getType());
	}
}
