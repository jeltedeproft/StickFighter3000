package jelte.mygame.tests.logic;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.SpatialMesh;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterFileReader;
import jelte.mygame.tests.util.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestSpatialMesh {
	private SpatialMesh spatialMesh;
	private Character testCharacter;

	@BeforeClass
	public static void init() {
		CharacterFileReader.loadUnitStatsInMemory();
	}

	@Before
	public void prepareSpatialMesh() {
		spatialMesh = new SpatialMesh(new Vector2(320, 320));
		testCharacter = new Character(CharacterFileReader.getUnitData().get(4), UUID.randomUUID());
	}

	public void testGetAllCollisions() {
		testCharacter.getPhysicsComponent().setPosition(new Vector2(0, 0));
		spatialMesh.addCollidable(testCharacter);
	}

	@Test
	public void testGetCollidingCells() {
		testCharacter.getPhysicsComponent().setPosition(new Vector2(0, 0));
		spatialMesh.addCollidable(testCharacter);
		Set<Point> collidingCells = spatialMesh.getCollidingCells(testCharacter.getRectangle());
		Set<Point> collidingCellsExpected = new HashSet<>();
		collidingCellsExpected.add(new Point(0, 0));
		Assert.assertEquals(collidingCellsExpected, collidingCells);
	}

	@Test
	public void testGetCollidingCells2() {
		testCharacter.getPhysicsComponent().setPosition(new Vector2(160, 160));
		spatialMesh.addCollidable(testCharacter);
		Set<Point> collidingCells = spatialMesh.getCollidingCells(testCharacter.getRectangle());
		Set<Point> collidingCellsExpected = new HashSet<>();
		collidingCellsExpected.add(new Point(5, 5));
		Assert.assertEquals(collidingCellsExpected, collidingCells);
	}

	@Test
	public void testGetCellAtZeroZero() {
		Assert.assertEquals(0, spatialMesh.getCellX(0));
		Assert.assertEquals(0, spatialMesh.getCellY(0));
	}

	@Test
	public void testGetCellAt32and32() {
		Assert.assertEquals(1, spatialMesh.getCellX(32));
		Assert.assertEquals(1, spatialMesh.getCellY(32));
	}

	@Test
	public void testGetCellAt160and160() {
		Assert.assertEquals(5, spatialMesh.getCellX(160));
		Assert.assertEquals(5, spatialMesh.getCellY(160));
	}

	@Test
	public void testGetCellAt1000and1000() {// TODO should throw out of bounds error?
		Assert.assertEquals(31, spatialMesh.getCellX(1000));
		Assert.assertEquals(31, spatialMesh.getCellY(1000));
	}

}
