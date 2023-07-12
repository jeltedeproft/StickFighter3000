package jelte.mygame.tests.logic.physics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.physics.PhysicsComponentImpl;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestPhysicsComponentImpl {

	@Test
	public void testPhysicsComponentImpl() {
		UUID ownerReference = UUID.randomUUID();
		Vector2 startPosition = new Vector2(0, 0);

		PhysicsComponentImpl physicsComponent = new PlayerPhysicsComponent(ownerReference, startPosition);

		assertEquals(ownerReference, physicsComponent.getOwnerReference());
		assertEquals(startPosition, physicsComponent.getPosition());
		assertEquals(new Vector2(), physicsComponent.getVelocity());
		assertEquals(new Vector2(), physicsComponent.getAcceleration());
		assertEquals(new Rectangle(0, 0, 0, 0), physicsComponent.getRectangle());
		assertFalse(physicsComponent.isCollided());
		assertFalse(physicsComponent.hasMoved());
	}

}
