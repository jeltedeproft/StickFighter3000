package jelte.mygame.tests.logic;

import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.physics.PhysicsComponent;
import jelte.mygame.logic.character.physics.StandardPhysicsComponent;
import jelte.mygame.logic.collisions.Collidable;
import jelte.mygame.logic.collisions.CollisionSystemImpl;
import jelte.mygame.logic.collisions.StaticBlockBot;
import jelte.mygame.logic.collisions.StaticBlockLeft;
import jelte.mygame.logic.collisions.StaticBlockPlatform;
import jelte.mygame.logic.collisions.StaticBlockRight;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestCollisionSystem {
	private CollisionSystemImpl collisionSystem;
	private PhysicsComponent mainBody;

	@Before
	public void beforeEverytest() {
		collisionSystem = new CollisionSystemImpl();
		collisionSystem.initSpatialMesh(new Vector2(100, 100));
		collisionSystem.addToSpatialMesh(new StaticBlockBot(0, 0, 100, 10));
	}

	private void initPlayerBody(Vector2 startPos) {
		mainBody = new StandardPhysicsComponent(UUID.randomUUID(), startPos);
		collisionSystem.addToSpatialMesh(mainBody);
	}

	@Test
	public void mainBodyMoveRightOnFloor() {
		initPlayerBody(new Vector2(25, 10));
		mainBody.setVelocity(new Vector2(1, 0));
		mainBody.update(1);
		collisionSystem.executeCollisions();
		Assert.assertEquals(new Vector2(26, 10), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveLeftOnFloor() {
		initPlayerBody(new Vector2(25, 10));
		mainBody.setVelocity(new Vector2(-1, 0));
		mainBody.update(1);
		collisionSystem.executeCollisions();
		Assert.assertEquals(new Vector2(24, 10), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveUpNoBlock() {
		initPlayerBody(new Vector2(25, 10));
		mainBody.setVelocity(new Vector2(0, 20));
		mainBody.update(1);
		collisionSystem.executeCollisions();
		Assert.assertEquals(new Vector2(25, 20), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveDownNoBlock() {
		initPlayerBody(new Vector2(25, 10));
		mainBody.setPosition(new Vector2(25, 55));
		mainBody.setVelocity(new Vector2(0, -5));
		mainBody.update(1);
		collisionSystem.executeCollisions();
		Assert.assertEquals(new Vector2(25, 40), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveRightIntoWall() {
		initPlayerBody(new Vector2(25, 10));
		mainBody.setVelocity(new Vector2(15, 0));
		Array<Collidable> blockingObjects = new Array<>();
		blockingObjects.add(new StaticBlockRight(50, 0, 10, 100));
		collisionSystem.addToSpatialMesh(blockingObjects);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		Assert.assertEquals(new Vector2(45, 10), mainBody.getPosition());
	}

	@Test
	public void mainBodyStartInWall() {
		initPlayerBody(new Vector2(0, 0));
		mainBody.setVelocity(new Vector2(15, 0));
		Array<Collidable> blockingObjects = new Array<>();
		blockingObjects.add(new StaticBlockLeft(0, 0, 50, 100));
		collisionSystem.addToSpatialMesh(blockingObjects);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		System.out.println("position = " + mainBody.getPosition());
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		System.out.println("position = " + mainBody.getPosition());
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		System.out.println("position = " + mainBody.getPosition());
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		Assert.assertEquals(new Vector2(45, 10), mainBody.getPosition());
	}

	@Test
	public void mainBodyStandStillOnPlatform() {
		initPlayerBody(new Vector2(15, 60));
		Array<Collidable> blockingObjects = new Array<>();
		blockingObjects.add(new StaticBlockPlatform(10, 50, 30, 10));
		collisionSystem.addToSpatialMesh(blockingObjects);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		Assert.assertEquals(new Vector2(15, 60), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveRightOnPlatform() {
		initPlayerBody(new Vector2(15, 60));
		mainBody.setVelocity(new Vector2(15, 0));
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.addToSpatialMesh(new StaticBlockPlatform(10, 50, 30, 10));
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		Assert.assertEquals(new Vector2(60, 30), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveLeftIntoWall() {
		initPlayerBody(new Vector2(25, 10));
		mainBody.setVelocity(new Vector2(-10, 0));
		Array<Collidable> blockingObjects = new Array<>();
		blockingObjects.add(new StaticBlockLeft(0, 0, 10, 100));
		collisionSystem.addToSpatialMesh(blockingObjects);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		Assert.assertEquals(new Vector2(15, 10), mainBody.getPosition());
	}

	@Test
	public void mainBodyStandStillOnGround() {
		initPlayerBody(new Vector2(25, 10));
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		Assert.assertEquals(new Vector2(25, 10), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveInCorner2BlockingObjects() {
		initPlayerBody(new Vector2(25, 10));
		mainBody.setVelocity(new Vector2(-10, 0));
		Array<Collidable> blockingObjects = new Array<>();
		blockingObjects.add(new StaticBlockLeft(0, 0, 10, 100));
		collisionSystem.addToSpatialMesh(blockingObjects);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		Assert.assertEquals(new Vector2(15, 10), mainBody.getPosition());
	}

	@After
	public void resetCharacter() {
		mainBody.setPosition(new Vector2(0, 0));
		mainBody.setVelocity(new Vector2(0, 0));
		mainBody.setAcceleration(new Vector2(0, 0));
		collisionSystem.reset();
	}

}
