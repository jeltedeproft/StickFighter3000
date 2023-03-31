package jelte.mygame.tests.logic;

import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.MovementSystem;
import jelte.mygame.logic.TypedRectangle;
import jelte.mygame.logic.TypedRectangle.BLOCKING_TYPE;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterData;
import jelte.mygame.logic.character.CharacterFileReader;
import jelte.mygame.tests.util.GdxTestRunner;
import jelte.mygame.utility.Constants;

@RunWith(GdxTestRunner.class)
public class testMovementSystem {
	private MovementSystem movementSystem;
	private Character testCharacter;
	private CharacterData characterData;

	@Before
	public void prepareMovementSystem() {
		movementSystem = new MovementSystem();
		CharacterFileReader.loadUnitStatsInMemory();
		characterData = CharacterFileReader.getUnitData().get(2);
		testCharacter = new Character(characterData, UUID.randomUUID());
		testCharacter.setPositionVector(new Vector2(0, 0));
	}

	@Test
	public void testCharacterMoveRightNoBlock() {
		testCharacter.setMovementVector(new Vector2(10, 0));
		movementSystem.update(1, testCharacter);
		Assert.assertEquals(new Vector2(10, Constants.GRAVITY.y), testCharacter.getPositionVector());
	}

	@Test
	public void testCharacterMoveLeftNoBlock() {
		testCharacter.setMovementVector(new Vector2(-10, 0));
		movementSystem.update(1, testCharacter);
		Assert.assertEquals(new Vector2(-10, Constants.GRAVITY.y), testCharacter.getPositionVector());
	}

	@Test
	public void testCharacterMoveUpNoBlock() {
		testCharacter.setMovementVector(new Vector2(0, 10));
		movementSystem.update(1, testCharacter);
		Assert.assertEquals(new Vector2(0, Constants.GRAVITY.y + 10), testCharacter.getPositionVector());
	}

	@Test
	public void testCharacterMoveDownNoBlock() {
		testCharacter.setMovementVector(new Vector2(0, -10));
		movementSystem.update(1, testCharacter);
		Assert.assertEquals(new Vector2(0, Constants.GRAVITY.y - 10), testCharacter.getPositionVector());
	}

	@Test
	public void testCharacterMoveRightIntoWall() {
		System.out.println("testing right into wall");
		testCharacter.setMovementVector(new Vector2(90, 0));
		Array<TypedRectangle> blockingObjects = new Array<>();
		blockingObjects.add(new TypedRectangle(25, -500, 1000, 1000, BLOCKING_TYPE.WALL));
		movementSystem.setBlockingRectangles(blockingObjects);
		System.out.println("pos = " + testCharacter.getPositionVector());
		movementSystem.update(0.1f, testCharacter);
		System.out.println("pos = " + testCharacter.getPositionVector());
		movementSystem.update(0.1f, testCharacter);
		System.out.println("pos = " + testCharacter.getPositionVector());
		movementSystem.update(0.1f, testCharacter);
		Assert.assertEquals(new Vector2(15, Constants.GRAVITY.y * 0.4f), testCharacter.getPositionVector());
	}

	@Test
	public void testCharacterStandStillOnPlatform() {
		System.out.println("testing still on platform");
		Array<TypedRectangle> blockingObjects = new Array<>();
		blockingObjects.add(new TypedRectangle(0, -100, 1000, 100, BLOCKING_TYPE.PLATFORM));
		movementSystem.setBlockingRectangles(blockingObjects);
		movementSystem.update(0.1f, testCharacter);
		movementSystem.update(0.1f, testCharacter);
		movementSystem.update(0.1f, testCharacter);
		Assert.assertEquals(new Vector2(0, 0), testCharacter.getPositionVector());
	}

	@Test
	public void testCharacterMoveRightOnPlatform() {
		System.out.println("testing move right on platform");
		testCharacter.setMovementVector(new Vector2(90, 0));
		Array<TypedRectangle> blockingObjects = new Array<>();
		blockingObjects.add(new TypedRectangle(0, -100, 1000, 100, BLOCKING_TYPE.PLATFORM));
		movementSystem.setBlockingRectangles(blockingObjects);
		movementSystem.update(0.1f, testCharacter);
		movementSystem.update(0.1f, testCharacter);
		movementSystem.update(0.1f, testCharacter);
		Assert.assertEquals(new Vector2(27, 0), testCharacter.getPositionVector());
	}

	@Test
	public void testCharacterMoveLeftIntoWall() {
		System.out.println("testing left into wall");
		testCharacter.setMovementVector(new Vector2(-90, 0));
		Array<TypedRectangle> blockingObjects = new Array<>();
		blockingObjects.add(new TypedRectangle(-25, -500, 20, 1000, BLOCKING_TYPE.WALL));
		movementSystem.setBlockingRectangles(blockingObjects);
		System.out.println("pos = " + testCharacter.getPositionVector());
		movementSystem.update(0.1f, testCharacter);
		System.out.println("pos = " + testCharacter.getPositionVector());
		movementSystem.update(0.1f, testCharacter);
		System.out.println("pos = " + testCharacter.getPositionVector());
		movementSystem.update(0.1f, testCharacter);
		Assert.assertEquals(new Vector2(-5, Constants.GRAVITY.y * 0.3f), testCharacter.getPositionVector());
	}

	@Test
	public void testCharacterStandStillOnGround() {
		System.out.println("testing still on ground");
		Array<TypedRectangle> blockingObjects = new Array<>();
		blockingObjects.add(new TypedRectangle(0, -100, 1000, 100, BLOCKING_TYPE.GROUND));
		movementSystem.setBlockingRectangles(blockingObjects);
		movementSystem.update(0.1f, testCharacter);
		movementSystem.update(0.1f, testCharacter);
		movementSystem.update(0.1f, testCharacter);
		Assert.assertEquals(new Vector2(0, 0), testCharacter.getPositionVector());
	}

	@Test
	public void testCharacterMoveInCorner2BlockingObjects() {
		System.out.println("testing 2 blocking objects collision");
		testCharacter.setPositionVector(new Vector2(20, 0));
		testCharacter.setMovementVector(new Vector2(-90, 0));
		Array<TypedRectangle> blockingObjects = new Array<>();
		blockingObjects.add(new TypedRectangle(-10, 0, 10, 100, BLOCKING_TYPE.WALL));
		blockingObjects.add(new TypedRectangle(0, -10, 100, 10, BLOCKING_TYPE.GROUND));
		movementSystem.setBlockingRectangles(blockingObjects);
		movementSystem.update(0.1f, testCharacter);
		movementSystem.update(0.1f, testCharacter);
		movementSystem.update(0.1f, testCharacter);
		Assert.assertEquals(new Vector2(0, 0), testCharacter.getPositionVector());
	}

	@After
	public void resetCharacter() {
		System.out.println("resetting character");
		testCharacter.setPositionVector(new Vector2(0, 0));
		testCharacter.setMovementVector(new Vector2(0, 0));
		testCharacter.setAccelerationVector(new Vector2(0, 0));
		movementSystem.setBlockingRectangles(new Array<>());
	}

}
