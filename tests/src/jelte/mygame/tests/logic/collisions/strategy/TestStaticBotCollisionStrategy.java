package jelte.mygame.tests.logic.collisions.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.collisions.collidable.Collidable.COLLIDABLE_TYPE;
import jelte.mygame.logic.collisions.collidable.StaticBlockBot;
import jelte.mygame.logic.collisions.strategy.StaticBotCollisionStrategy;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestStaticBotCollisionStrategy {

	private StaticBotCollisionStrategy strategy;

	@Before
	public void beforeEverytest() {
		strategy = new StaticBotCollisionStrategy();
	}

	@Test
	public void testResolvePossibleCollision() {
		int x = 0;
		int y = 0;
		int width = 30;
		int height = 40;

		StaticBlockBot staticCollidable = new StaticBlockBot(x, y, width, height);
		PlayerPhysicsComponent dynamicCollidable = new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0));
		dynamicCollidable.setDimensions(10, 10);
		dynamicCollidable.setVelocity(new Vector2(-50, -50));

		strategy.resolvePossibleCollision(staticCollidable, dynamicCollidable);

		assertEquals(10, staticCollidable.getOverlapX(), 0.001f);
		assertEquals(10, staticCollidable.getOverlapY(), 0.001f);
		assertTrue(dynamicCollidable.isCollided());
		assertTrue(dynamicCollidable.getCollidedWith().size == 1);
		assertEquals(COLLIDABLE_TYPE.STATIC_BOT.name(), dynamicCollidable.getCollidedWith().get(0).name());
		assertTrue(dynamicCollidable.getPosition().x == 0);
		assertTrue(dynamicCollidable.getPosition().y == 10);
		assertTrue(dynamicCollidable.getVelocity().x == -50);
		assertTrue(dynamicCollidable.getVelocity().y == 0);
		assertTrue(dynamicCollidable.getAcceleration().x == 0);
		assertTrue(dynamicCollidable.getAcceleration().y == 0);

	}

}
