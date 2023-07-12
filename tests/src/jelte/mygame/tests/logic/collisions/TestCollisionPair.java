package jelte.mygame.tests.logic.collisions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.collisions.CollisionPair;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlockBot;
import jelte.mygame.logic.physics.EnemyPhysicsComponent;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestCollisionPair {

	@Test
	public void testCollisionPair() {
		Collidable collidable1 = new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0));
		Collidable collidable2 = new EnemyPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0), 10, 10);

		CollisionPair collisionPair = new CollisionPair(collidable1, collidable2);

		assertSame(collidable1, collisionPair.getCollidable1());
		assertSame(collidable2, collisionPair.getCollidable2());
	}

	@Test
	public void testEquals() {
		Collidable collidable1 = new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0));
		Collidable collidable2 = new EnemyPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0), 10, 10);

		CollisionPair pair1 = new CollisionPair(collidable1, collidable2);
		CollisionPair pair2 = new CollisionPair(collidable2, collidable1);
		CollisionPair pair3 = new CollisionPair(collidable1, collidable1);

		assertTrue(pair1.equals(pair2));
		assertFalse(pair1.equals(pair3));
	}

	@Test
	public void testHashCode() {
		Collidable collidable1 = new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0));
		Collidable collidable2 = new EnemyPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0), 10, 10);

		CollisionPair pair1 = new CollisionPair(collidable1, collidable2);
		CollisionPair pair2 = new CollisionPair(collidable2, collidable1);

		assertEquals(pair1.hashCode(), pair2.hashCode());
	}

	@Test
	public void testToString() {
		Collidable collidable1 = new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0));
		Collidable collidable2 = new EnemyPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0), 10, 10);

		CollisionPair collisionPair = new CollisionPair(collidable1, collidable2);

		String expectedToString = "collidable 1 : PLAYER\n"
				+
				"collidable 2 : ENEMY\n";

		assertEquals(expectedToString, collisionPair.toString());
	}

	@Test
	public void testCompareTo() {
		Collidable collidable1 = new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0));
		Collidable collidable2 = new EnemyPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0), 10, 10);
		int x = 0;
		int y = 0;
		int width = 30;
		int height = 40;

		Collidable collidable3 = new StaticBlockBot(x, y, width, height);

		CollisionPair pair1 = new CollisionPair(collidable1, collidable2);
		CollisionPair pair2 = new CollisionPair(collidable2, collidable1);
		CollisionPair pair3 = new CollisionPair(collidable1, collidable3);

		assertEquals(-1, pair1.compareTo(pair2));
		assertTrue(pair1.compareTo(pair3) > 0);
	}
}
