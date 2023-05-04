package jelte.mygame.tests.logic;

import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.CollisionSystemImpl;
import jelte.mygame.logic.TypedRectangle;
import jelte.mygame.logic.character.physics.PhysicsComponent;
import jelte.mygame.logic.character.physics.StandardPhysicsComponent;
import jelte.mygame.tests.util.GdxTestRunner;
import jelte.mygame.utility.Constants;

@RunWith(GdxTestRunner.class)
public class TestCollisionSystem {
	private CollisionSystemImpl collisionSystem;
	private Array<PhysicsComponent> testBodies;
	private PhysicsComponent mainBody;

	@Before
	public void prepareMovementSystem() {
		collisionSystem = new CollisionSystemImpl();
		testBodies = new Array<>();
		mainBody = new StandardPhysicsComponent(UUID.randomUUID());
		mainBody.setPosition(new Vector2(0, 0));
		testBodies.add(mainBody);
	}

	@Test
	public void mainBodyMoveRightNoBlock() {
		mainBody.setVelocity(new Vector2(10, 0));
		mainBody.update(1);
		collisionSystem.updateCollisions(testBodies);
		Assert.assertEquals(new Vector2(10, Constants.GRAVITY.y), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveLeftNoBlock() {
		mainBody.setVelocity(new Vector2(-10, 0));
		mainBody.update(1);
		Assert.assertEquals(new Vector2(-10, Constants.GRAVITY.y), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveUpNoBlock() {
		mainBody.setVelocity(new Vector2(0, 10));
		mainBody.update(1);
		Assert.assertEquals(new Vector2(0, Constants.GRAVITY.y + 10), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveDownNoBlock() {
		mainBody.setVelocity(new Vector2(0, -10));
		mainBody.update(1);
		Assert.assertEquals(new Vector2(0, Constants.GRAVITY.y - 10), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveRightIntoWall() {
		System.out.println("testing right into wall");
		mainBody.setVelocity(new Vector2(90, 0));
		Array<TypedRectangle> blockingObjects = new Array<>();
		blockingObjects.add(new TypedRectangle(25, -500, 1000, 1000, Constants.BLOCK_TYPE_RIGHT));
		collisionSystem.setBlockingRectangles(blockingObjects);
		System.out.println("pos = " + mainBody.getPosition());
		mainBody.update(0.1f);
		System.out.println("pos after move = " + mainBody.getPosition());
		collisionSystem.updateCollisions(testBodies);
		System.out.println("pos = " + mainBody.getPosition());
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		System.out.println("pos = " + mainBody.getPosition());
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		Assert.assertEquals(new Vector2(20, Constants.GRAVITY.y * 0.6f), mainBody.getPosition());
	}

	@Test
	public void mainBodyStandStillOnPlatform() {
		System.out.println("testing still on platform");
		Array<TypedRectangle> blockingObjects = new Array<>();
		blockingObjects.add(new TypedRectangle(0, -100, 1000, 100, Constants.BLOCK_TYPE_PLATFORM));
		collisionSystem.setBlockingRectangles(blockingObjects);
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		Assert.assertEquals(new Vector2(0, Constants.PLAYER_HEIGHT / 2), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveRightOnPlatform() {
		System.out.println("testing move right on platform");
		mainBody.setVelocity(new Vector2(90, 0));
		Array<TypedRectangle> blockingObjects = new Array<>();
		blockingObjects.add(new TypedRectangle(0, -100, 1000, 100, Constants.BLOCK_TYPE_PLATFORM));
		collisionSystem.setBlockingRectangles(blockingObjects);
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		Assert.assertEquals(new Vector2(27, Constants.PLAYER_HEIGHT / 2), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveLeftIntoWall() {
		System.out.println("testing left into wall");
		mainBody.setVelocity(new Vector2(-90, 0));
		Array<TypedRectangle> blockingObjects = new Array<>();
		blockingObjects.add(new TypedRectangle(-25, -500, 20, 1000, Constants.BLOCK_TYPE_LEFT));
		collisionSystem.setBlockingRectangles(blockingObjects);
		System.out.println("pos = " + mainBody.getPosition());
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		Assert.assertEquals(new Vector2(0, Constants.GRAVITY.y * 0.6f), mainBody.getPosition());
	}

	@Test
	public void mainBodyStandStillOnGround() {
		System.out.println("testing still on ground");
		Array<TypedRectangle> blockingObjects = new Array<>();
		blockingObjects.add(new TypedRectangle(0, -100, 1000, 100, Constants.BLOCK_TYPE_BOT));
		collisionSystem.setBlockingRectangles(blockingObjects);
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		Assert.assertEquals(new Vector2(0, Constants.PLAYER_HEIGHT / 2), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveInCorner2BlockingObjects() {
		System.out.println("testing 2 blocking objects collision");
		mainBody.setPosition(new Vector2(20, 0));
		mainBody.setVelocity(new Vector2(-90, 0));
		Array<TypedRectangle> blockingObjects = new Array<>();
		blockingObjects.add(new TypedRectangle(-10, 0, 10, 100, Constants.BLOCK_TYPE_LEFT));
		blockingObjects.add(new TypedRectangle(0, -10, 100, 10, Constants.BLOCK_TYPE_BOT));
		collisionSystem.setBlockingRectangles(blockingObjects);
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		mainBody.update(0.1f);
		collisionSystem.updateCollisions(testBodies);
		Assert.assertEquals(new Vector2(Constants.PLAYER_WIDTH / 2, Constants.PLAYER_HEIGHT / 2), mainBody.getPosition());
	}

	@After
	public void resetCharacter() {
		System.out.println("resetting character");
		mainBody.setPosition(new Vector2(0, 0));
		mainBody.setVelocity(new Vector2(0, 0));
		mainBody.setAcceleration(new Vector2(0, 0));
		collisionSystem.setBlockingRectangles(new Array<>());
	}

}
