package jelte.mygame.tests.logic.collisions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.collisions.CollisionDetectionSystemImpl;
import jelte.mygame.logic.collisions.CollisionPair;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlockBot;
import jelte.mygame.logic.collisions.collidable.StaticBlockLeft;
import jelte.mygame.logic.collisions.collidable.StaticBlockPlatform;
import jelte.mygame.logic.collisions.collidable.StaticBlockRight;
import jelte.mygame.logic.collisions.collidable.StaticBlockTop;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.UtilityFunctions;

@RunWith(GdxTestRunner.class)
public class TestCollisionDetectionSystem {
	private CollisionDetectionSystemImpl collisionSystem;
	private PlayerPhysicsComponent characterBody;
	private StaticBlockBot bottomBlock;
	private Set<Collidable> collidables;

	@Before
	public void beforeEverytest() {
		collidables = new HashSet<>();
		collisionSystem = new CollisionDetectionSystemImpl();
		collisionSystem.initSpatialMesh(new Vector2(100, 100));
		bottomBlock = new StaticBlockBot(0, 0, 100, 10);
		collidables.add(bottomBlock);
	}

	private void addPlayer(Vector2 startPos) {
		characterBody = new PlayerPhysicsComponent(UUID.randomUUID(), startPos);
		characterBody.setSize(10, 10);
		collidables.add(characterBody);
		collisionSystem.updateSpatialMesh(collidables);
	}

	@Test
	public void mainBodyMoveRightOnFloor() {
		addPlayer(new Vector2(25, 10));
		characterBody.setVelocity(new Vector2(1, 0));
		characterBody.update(1);
		Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
		Set<CollisionPair> expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
	}

	@Test
	public void mainBodyMoveLeftOnFloor() {
		addPlayer(new Vector2(25, 10));
		characterBody.setVelocity(new Vector2(-1, 0));
		characterBody.update(1);
		Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
		Set<CollisionPair> expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
	}

	@Test
	public void mainBodyMoveUpNoBlock() {
		addPlayer(new Vector2(25, 10));
		characterBody.setVelocity(new Vector2(0, 20));
		characterBody.update(1);
		Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
		Set<CollisionPair> expectedcollidedPairs = new HashSet<>();
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
	}

	@Test
	public void mainBodyMoveDownNoBlock() {
		addPlayer(new Vector2(25, 55));
		characterBody.setVelocity(new Vector2(0, -5));
		characterBody.update(1);
		Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
		Set<CollisionPair> expectedcollidedPairs = new HashSet<>();
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
	}

	@Test
	public void mainBodyMoveLeftIntoWall() {
		addPlayer(new Vector2(25, 10));
		characterBody.setVelocity(new Vector2(-10, 0));
		StaticBlockLeft blockLeft = new StaticBlockLeft(0, 0, 10, 100);
		collidables.add(blockLeft);
		collisionSystem.updateSpatialMesh(collidables);
		Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
		Set<CollisionPair> expectedcollidedPairs = new HashSet<>();
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		expectedcollidedPairs.add(new CollisionPair(characterBody, blockLeft));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		expectedcollidedPairs.add(new CollisionPair(characterBody, blockLeft));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
	}

	@Test
	public void mainBodyStartInWall() {
		addPlayer(new Vector2(0, 0));
		characterBody.setVelocity(new Vector2(15, 0));
		StaticBlockLeft blockLeft = new StaticBlockLeft(0, 0, 50, 100);
		collidables.add(blockLeft);
		collisionSystem.updateSpatialMesh(collidables);
		Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
		Set<CollisionPair> expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		expectedcollidedPairs.add(new CollisionPair(characterBody, blockLeft));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		expectedcollidedPairs.add(new CollisionPair(characterBody, blockLeft));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		expectedcollidedPairs.add(new CollisionPair(characterBody, blockLeft));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		expectedcollidedPairs.add(new CollisionPair(characterBody, blockLeft));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
	}

	@Test
	public void mainBodyMoveRightIntoWall() {
		addPlayer(new Vector2(25, 10));
		characterBody.setVelocity(new Vector2(15, 0));
		StaticBlockRight blockRight = new StaticBlockRight(50, 0, 50, 100);
		collidables.add(blockRight);
		collisionSystem.updateSpatialMesh(collidables);
		Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
		Set<CollisionPair> expectedcollidedPairs = new HashSet<>();
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		expectedcollidedPairs.add(new CollisionPair(characterBody, blockRight));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		expectedcollidedPairs.add(new CollisionPair(characterBody, blockRight));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
	}

	@Test
	public void mainBodyMoveRightIntoWallRealisticTimestep() {
		addPlayer(new Vector2(25, 10));
		characterBody.setVelocity(new Vector2(15, 0));
		collidables.add(new StaticBlockRight(50, 0, 50, 100));
		collisionSystem.updateSpatialMesh(collidables);
		Array<Set<CollisionPair>> positions = new Array<>();
		for (int i = 0; i < 1000; i++) {
			characterBody.update(0.01f);
			collisionSystem.updateSpatialMesh(collidables);
			Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
			positions.add(collidedPairs);
		}
		Set<CollisionPair>[] setsArray = new Set[positions.size];
		for (int i = 0; i < positions.size; i++) {
			setsArray[i] = positions.get(i);
		}
		UtilityFunctions.writeSetsToFile(setsArray, Constants.TEST_FILE_POSITIONS_ACTUAL);

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
		addPlayer(new Vector2(40, 10));
		characterBody.setVelocity(new Vector2(15, 0));
		collidables.add(new StaticBlockRight(50, 0, 10, 100));
		collisionSystem.updateSpatialMesh(collidables);
		Array<Set<CollisionPair>> positions = new Array<>();
		for (int i = 0; i < 500; i++) {
			characterBody.update(0.01f);
			collisionSystem.updateSpatialMesh(collidables);
			Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
			positions.add(collidedPairs);
		}
		characterBody.setVelocity(new Vector2(-15, 0));
		for (int i = 0; i < 500; i++) {
			characterBody.update(0.01f);
			collisionSystem.updateSpatialMesh(collidables);
			Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
			positions.add(collidedPairs);
		}
		Set<CollisionPair>[] setsArray = new Set[positions.size];
		for (int i = 0; i < positions.size; i++) {
			setsArray[i] = positions.get(i);
		}
		UtilityFunctions.writeSetsToFile(setsArray, Constants.TEST_FILE_STUCK_IN_WALL_ACTUAL);

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
		addPlayer(new Vector2(25, 10));
		characterBody.setAcceleration(new Vector2(Constants.WALK_SPEED, 0));
		collidables.add(new StaticBlockRight(50, 0, 10, 100));
		collisionSystem.updateSpatialMesh(collidables);
		Array<Set<CollisionPair>> positions = new Array<>();
		for (int i = 0; i < 1000; i++) {
			characterBody.update(0.01f);
			collisionSystem.updateSpatialMesh(collidables);
			Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
			positions.add(collidedPairs);
		}
		Set<CollisionPair>[] setsArray = new Set[positions.size];
		for (int i = 0; i < positions.size; i++) {
			setsArray[i] = positions.get(i);
		}
		UtilityFunctions.writeSetsToFile(setsArray, Constants.TEST_FILE_ACCELERATION_ACTUAL);

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
		addPlayer(new Vector2(25, 10));
		characterBody.setAcceleration(new Vector2(0, 11));
		characterBody.setVelocity(new Vector2(0, 20));
		collidables.add(new StaticBlockTop(0, 50, 100, 50));
		collisionSystem.updateSpatialMesh(collidables);
		Array<Set<CollisionPair>> positions = new Array<>();
		for (int i = 0; i < 1000; i++) {
			characterBody.update(0.05f);
			collisionSystem.updateSpatialMesh(collidables);
			Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
			positions.add(collidedPairs);
		}
		Set<CollisionPair>[] setsArray = new Set[positions.size];
		for (int i = 0; i < positions.size; i++) {
			setsArray[i] = positions.get(i);
		}
		UtilityFunctions.writeSetsToFile(setsArray, Constants.TEST_FILE_CEILING_ACTUAL);

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
	public void mainBodyStandStillOnPlatform() {
		addPlayer(new Vector2(15, 60));
		StaticBlockPlatform platform = new StaticBlockPlatform(10, 50, 30, 10);
		collidables.add(platform);
		collisionSystem.updateSpatialMesh(collidables);
		Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
		Set<CollisionPair> expectedcollidedPairs = new HashSet<>();
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, platform));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
	}

	@Test
	public void mainBodyMoveRightOnPlatform() {
		addPlayer(new Vector2(15, 60));
		characterBody.setVelocity(new Vector2(15, 0));
		StaticBlockPlatform platform = new StaticBlockPlatform(10, 50, 30, 10);
		collidables.add(platform);
		collisionSystem.updateSpatialMesh(collidables);
		Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
		Set<CollisionPair> expectedcollidedPairs = new HashSet<>();
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, platform));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
	}

	@Test
	public void mainBodyJumpIntoCeiling() {
		addPlayer(new Vector2(25, 10));
		characterBody.setVelocity(new Vector2(0, 40));
		StaticBlockTop blockTop = new StaticBlockTop(0, 50, 100, 50);
		collidables.add(blockTop);
		collisionSystem.updateSpatialMesh(collidables);
		Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
		Set<CollisionPair> expectedcollidedPairs = new HashSet<>();
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(blockTop, characterBody));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
	}

	@Test
	public void mainBodyStandStillOnGround() {
		addPlayer(new Vector2(25, 10));
		Set<CollisionPair> collidedPairs = collisionSystem.getCollidingpairs();
		Set<CollisionPair> expectedcollidedPairs = new HashSet<>();
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
		characterBody.update(1f);
		collisionSystem.updateSpatialMesh(collidables);
		collidedPairs = collisionSystem.getCollidingpairs();
		expectedcollidedPairs = new HashSet<>();
		expectedcollidedPairs.add(new CollisionPair(characterBody, bottomBlock));
		Assert.assertEquals(expectedcollidedPairs, collidedPairs);
	}

	@After
	public void resetCharacter() {
		characterBody.setPosition(new Vector2(0, 0));
		characterBody.setVelocity(new Vector2(0, 0));
		characterBody.setAcceleration(new Vector2(0, 0));
		collisionSystem.reset();
	}

}
