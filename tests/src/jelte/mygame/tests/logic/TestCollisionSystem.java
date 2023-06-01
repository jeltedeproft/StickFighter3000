package jelte.mygame.tests.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.collisions.CollisionSystemImpl;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlockBot;
import jelte.mygame.logic.collisions.collidable.StaticBlockLeft;
import jelte.mygame.logic.collisions.collidable.StaticBlockPlatform;
import jelte.mygame.logic.collisions.collidable.StaticBlockRight;
import jelte.mygame.logic.collisions.collidable.StaticBlockTop;
import jelte.mygame.logic.physics.CharacterPhysicsComponent;
import jelte.mygame.logic.physics.PhysicsComponent;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.UtilityFunctions;

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
		mainBody = new CharacterPhysicsComponent(UUID.randomUUID(), startPos);
		mainBody.setDimensions(10, 10);
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
		Assert.assertEquals(new Vector2(40, 10), mainBody.getPosition());
	}

	@Test
	public void mainBodyMoveRightIntoWallRealisticTimestep() {
		initPlayerBody(new Vector2(25, 10));
		mainBody.setVelocity(new Vector2(15, 0));
		Array<Collidable> blockingObjects = new Array<>();
		blockingObjects.add(new StaticBlockRight(50, 0, 10, 100));
		collisionSystem.addToSpatialMesh(blockingObjects);
		Array<Vector2> positions = new Array<>();
		for (int i = 0; i < 1000; i++) {
			mainBody.update(0.01f);
			collisionSystem.updateSpatialMesh(mainBody);
			collisionSystem.executeCollisions();
			positions.add(mainBody.getPosition().cpy());
		}
		UtilityFunctions.writeArrayToFile(positions, Constants.TEST_FILE_POSITIONS_ACTUAL);

		String file1Path = Constants.TEST_FILE_POSITIONS_EXPECTED;
		String file2Path = Constants.TEST_FILE_POSITIONS_ACTUAL;

		try {
			byte[] file1Bytes = Files.readAllBytes(Path.of(file1Path));
			byte[] file2Bytes = Files.readAllBytes(Path.of(file2Path));
			Assert.assertArrayEquals(file1Bytes, file2Bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void mainStuckInWall() {
		initPlayerBody(new Vector2(40, 10));
		mainBody.setVelocity(new Vector2(15, 0));
		Array<Collidable> blockingObjects = new Array<>();
		blockingObjects.add(new StaticBlockRight(50, 0, 10, 100));
		collisionSystem.addToSpatialMesh(blockingObjects);
		Array<Vector2> positions = new Array<>();
		for (int i = 0; i < 500; i++) {
			mainBody.update(0.01f);
			collisionSystem.updateSpatialMesh(mainBody);
			collisionSystem.executeCollisions();
			positions.add(mainBody.getPosition().cpy());
		}
		mainBody.setVelocity(new Vector2(-15, 0));
		for (int i = 0; i < 500; i++) {
			mainBody.update(0.01f);
			collisionSystem.updateSpatialMesh(mainBody);
			collisionSystem.executeCollisions();
			positions.add(mainBody.getPosition().cpy());
		}
		UtilityFunctions.writeArrayToFile(positions, Constants.TEST_FILE_STUCK_IN_WALL_ACTUAL);

		String file1Path = Constants.TEST_FILE_STUCK_IN_WALL_EXPECTED;
		String file2Path = Constants.TEST_FILE_STUCK_IN_WALL_ACTUAL;

		try {
			byte[] file1Bytes = Files.readAllBytes(Path.of(file1Path));
			byte[] file2Bytes = Files.readAllBytes(Path.of(file2Path));
			Assert.assertArrayEquals(file1Bytes, file2Bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void mainBodyAccelerateRightIntoWallRealisticTimestep() {
		initPlayerBody(new Vector2(25, 10));
		mainBody.setAcceleration(new Vector2(Constants.MOVEMENT_SPEED, 0));
		Array<Collidable> blockingObjects = new Array<>();
		blockingObjects.add(new StaticBlockRight(50, 0, 10, 100));
		collisionSystem.addToSpatialMesh(blockingObjects);
		Array<Vector2> positions = new Array<>();
		for (int i = 0; i < 1000; i++) {
			mainBody.update(0.01f);
			collisionSystem.updateSpatialMesh(mainBody);
			collisionSystem.executeCollisions();
			positions.add(mainBody.getPosition().cpy());
		}
		UtilityFunctions.writeArrayToFile(positions, Constants.TEST_FILE_ACCELERATION_ACTUAL);

		String file1Path = Constants.TEST_FILE_ACCELERATION_EXPECTED;
		String file2Path = Constants.TEST_FILE_ACCELERATION_ACTUAL;

		try {
			byte[] file1Bytes = Files.readAllBytes(Path.of(file1Path));
			byte[] file2Bytes = Files.readAllBytes(Path.of(file2Path));
			Assert.assertArrayEquals(file1Bytes, file2Bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void mainBodyAccelerateIntoCeilingRealisticTimestep() {
		initPlayerBody(new Vector2(25, 10));
		mainBody.setAcceleration(new Vector2(0, 11));
		mainBody.setVelocity(new Vector2(0, 20));
		Array<Collidable> blockingObjects = new Array<>();
		blockingObjects.add(new StaticBlockTop(0, 50, 100, 50));
		collisionSystem.addToSpatialMesh(blockingObjects);
		Array<Vector2> positions = new Array<>();
		for (int i = 0; i < 1000; i++) {
			mainBody.update(0.05f);
			collisionSystem.updateSpatialMesh(mainBody);
			collisionSystem.executeCollisions();
			positions.add(mainBody.getPosition().cpy());
		}
		UtilityFunctions.writeArrayToFile(positions, Constants.TEST_FILE_CEILING_ACTUAL);

		String file1Path = Constants.TEST_FILE_CEILING_EXPECTED;
		String file2Path = Constants.TEST_FILE_CEILING_ACTUAL;

		try {
			byte[] file1Bytes = Files.readAllBytes(Path.of(file1Path));
			byte[] file2Bytes = Files.readAllBytes(Path.of(file2Path));
			Assert.assertArrayEquals(file1Bytes, file2Bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		Assert.assertEquals(new Vector2(40, 10), mainBody.getPosition());// velocity is 0 after exiting wall
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
		Assert.assertEquals(new Vector2(10, 10), mainBody.getPosition());
	}

	@Test
	public void mainBodyJumpIntoCeiling() {
		initPlayerBody(new Vector2(25, 10));
		mainBody.setVelocity(new Vector2(0, 40));
		Array<Collidable> blockingObjects = new Array<>();
		blockingObjects.add(new StaticBlockTop(0, 50, 100, 50));
		collisionSystem.addToSpatialMesh(blockingObjects);
		collisionSystem.executeCollisions();
		mainBody.update(1f);
		collisionSystem.updateSpatialMesh(mainBody);
		collisionSystem.executeCollisions();
		Assert.assertEquals(new Vector2(25, 40), mainBody.getPosition());
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

	@After
	public void resetCharacter() {
		mainBody.setPosition(new Vector2(0, 0));
		mainBody.setVelocity(new Vector2(0, 0));
		mainBody.setAcceleration(new Vector2(0, 0));
		collisionSystem.reset();
	}

}
