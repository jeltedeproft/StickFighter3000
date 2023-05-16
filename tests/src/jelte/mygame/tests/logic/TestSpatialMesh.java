package jelte.mygame.tests.logic;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterFileReader;
import jelte.mygame.logic.collisions.SpatialMesh;
import jelte.mygame.logic.collisions.StaticBlock;
import jelte.mygame.logic.collisions.StaticBlockBot;
import jelte.mygame.logic.collisions.StaticBlockLeft;
import jelte.mygame.logic.collisions.StaticBlockPlatform;
import jelte.mygame.logic.collisions.StaticBlockRight;
import jelte.mygame.logic.collisions.StaticBlockTop;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;

@RunWith(GdxTestRunner.class)
public class TestSpatialMesh {
	private static final int WIDTH = 320;
	private static final int HEIGHT = 320;

	private SpatialMesh spatialMesh;
	private Character testCharacter;
	private Character testCharacter2;
	private Character testCharacter3;
	private Character testCharacter4;
	private StaticBlock testBlockBottom10High;
	private StaticBlock testBlockLeft10Width;
	private StaticBlock testBlockRight10Width;
	private StaticBlock testBlockTop10High;
	private StaticBlock testBlockPlatform50Width;

	@BeforeClass
	public static void init() {
		CharacterFileReader.loadUnitStatsInMemory();
	}

	@Before
	public void beforeEveryTest() {
		spatialMesh = new SpatialMesh(new Vector2(WIDTH, HEIGHT));
		testCharacter = new Character(CharacterFileReader.getUnitData().get(4), UUID.randomUUID());
		testCharacter2 = new Character(CharacterFileReader.getUnitData().get(4), UUID.randomUUID());
		testCharacter3 = new Character(CharacterFileReader.getUnitData().get(4), UUID.randomUUID());
		testCharacter4 = new Character(CharacterFileReader.getUnitData().get(4), UUID.randomUUID());
		testBlockBottom10High = new StaticBlockBot(new Rectangle(0, 0, 320, 10));
		testBlockLeft10Width = new StaticBlockLeft(new Rectangle(0, 0, 10, 320));
		testBlockRight10Width = new StaticBlockRight(new Rectangle(310, 0, 10, 320));
		testBlockTop10High = new StaticBlockTop(new Rectangle(0, 310, 320, 10));
		testBlockPlatform50Width = new StaticBlockPlatform(new Rectangle(160, 160, 50, 10));
	}

	public void testGetAllCollisions() {
		testCharacter.getPhysicsComponent().setPosition(new Vector2(0, 0));
		spatialMesh.addCollidable(testCharacter);
	}

	@Test
	public void testRemoveCellsDynamicCollidable() {
		testCharacter.getPhysicsComponent().setPosition(new Vector2(0, 0));
		spatialMesh.addCollidable(testCharacter);
		Set<Point> cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		Set<Point> cellsWithDynamicExpected = new HashSet<>();
		cellsWithDynamicExpected.add(new Point(0, 0));
		Assert.assertEquals(cellsWithDynamicExpected, cellsWithDynamic);

		spatialMesh.removeDynamicCollidable(testCharacter);
		cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		cellsWithDynamicExpected = new HashSet<>();
		Assert.assertEquals(cellsWithDynamicExpected, cellsWithDynamic);
	}

	@Test
	public void testRemoveDynamicArraysFilled1Character() {
		testCharacter.getPhysicsComponent().setPosition(new Vector2(0, 0));
		spatialMesh.addCollidable(testCharacter);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				if (i == 0 && j == 0) {
					Assert.assertEquals(1, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size);
				} else {
					Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size);
				}
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size);
			}
		}
		spatialMesh.removeDynamicCollidable(testCharacter);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size);
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size);
			}
		}
	}

	@Test
	public void testStaticArraysFilled5BlocksDifferentPlaces() {
		spatialMesh.addCollidable(testBlockBottom10High);
		spatialMesh.addCollidable(testBlockLeft10Width);
		spatialMesh.addCollidable(testBlockRight10Width);
		spatialMesh.addCollidable(testBlockTop10High);
		spatialMesh.addCollidable(testBlockPlatform50Width);
		Map<Point, Integer> expectedStaticArraySizes = new HashMap<>();
		expectedStaticArraySizes.put(new Point(0, 0), 2);
		expectedStaticArraySizes.put(new Point(0, 1), 1);
		expectedStaticArraySizes.put(new Point(0, 2), 1);
		expectedStaticArraySizes.put(new Point(0, 3), 1);
		expectedStaticArraySizes.put(new Point(0, 4), 1);
		expectedStaticArraySizes.put(new Point(0, 5), 1);
		expectedStaticArraySizes.put(new Point(0, 6), 1);
		expectedStaticArraySizes.put(new Point(0, 7), 1);
		expectedStaticArraySizes.put(new Point(0, 8), 1);
		expectedStaticArraySizes.put(new Point(0, 9), 2);
		expectedStaticArraySizes.put(new Point(1, 0), 1);
		expectedStaticArraySizes.put(new Point(2, 0), 1);
		expectedStaticArraySizes.put(new Point(3, 0), 1);
		expectedStaticArraySizes.put(new Point(4, 0), 1);
		expectedStaticArraySizes.put(new Point(5, 0), 1);
		expectedStaticArraySizes.put(new Point(6, 0), 1);
		expectedStaticArraySizes.put(new Point(7, 0), 1);
		expectedStaticArraySizes.put(new Point(8, 0), 1);
		expectedStaticArraySizes.put(new Point(9, 0), 2);
		expectedStaticArraySizes.put(new Point(9, 1), 1);
		expectedStaticArraySizes.put(new Point(9, 2), 1);
		expectedStaticArraySizes.put(new Point(9, 3), 1);
		expectedStaticArraySizes.put(new Point(9, 4), 1);
		expectedStaticArraySizes.put(new Point(9, 5), 1);
		expectedStaticArraySizes.put(new Point(9, 6), 1);
		expectedStaticArraySizes.put(new Point(9, 7), 1);
		expectedStaticArraySizes.put(new Point(9, 8), 1);
		expectedStaticArraySizes.put(new Point(9, 9), 2);
		expectedStaticArraySizes.put(new Point(1, 9), 1);
		expectedStaticArraySizes.put(new Point(2, 9), 1);
		expectedStaticArraySizes.put(new Point(3, 9), 1);
		expectedStaticArraySizes.put(new Point(4, 9), 1);
		expectedStaticArraySizes.put(new Point(5, 9), 1);
		expectedStaticArraySizes.put(new Point(6, 9), 1);
		expectedStaticArraySizes.put(new Point(7, 9), 1);
		expectedStaticArraySizes.put(new Point(8, 9), 1);
		expectedStaticArraySizes.put(new Point(5, 5), 1);
		expectedStaticArraySizes.put(new Point(6, 5), 1);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				Point testPoint = new Point(i, j);
				if (expectedStaticArraySizes.containsKey(testPoint)) {
					Assert.assertEquals("for (" + i + "," + j + ")", (int) expectedStaticArraySizes.get(testPoint), spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size);
				} else {
					Assert.assertEquals("for (" + i + "," + j + ")", 0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size);
				}
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size);
			}
		}
	}

	@Test
	public void testDynamicArraysFilled4CharactersDifferentPlaces() {
		testCharacter.getPhysicsComponent().setPosition(new Vector2(0, 0));
		testCharacter2.getPhysicsComponent().setPosition(new Vector2(50, 40));
		testCharacter3.getPhysicsComponent().setPosition(new Vector2(100, 0));
		testCharacter4.getPhysicsComponent().setPosition(new Vector2(200, 205));
		spatialMesh.addCollidable(testCharacter);
		spatialMesh.addCollidable(testCharacter2);
		spatialMesh.addCollidable(testCharacter3);
		spatialMesh.addCollidable(testCharacter4);
		Map<Point, Integer> expectedDynamicArraySizes = new HashMap<>();
		expectedDynamicArraySizes.put(new Point(0, 0), 1);
		expectedDynamicArraySizes.put(new Point(1, 1), 1);
		expectedDynamicArraySizes.put(new Point(2, 0), 1);
		expectedDynamicArraySizes.put(new Point(3, 0), 1);
		expectedDynamicArraySizes.put(new Point(6, 6), 1);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				Point testPoint = new Point(i, j);
				if (expectedDynamicArraySizes.containsKey(testPoint)) {
					Assert.assertEquals((int) expectedDynamicArraySizes.get(testPoint), spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size);
				} else {
					Assert.assertEquals("for (" + i + "," + j + ")", 0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size);
				}
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size);
			}
		}
	}

	@Test
	public void testDynamicArraysFilled4CharactersSamePlace() {
		testCharacter.getPhysicsComponent().setPosition(new Vector2(0, 0));
		testCharacter2.getPhysicsComponent().setPosition(new Vector2(0, 0));
		testCharacter3.getPhysicsComponent().setPosition(new Vector2(0, 0));
		testCharacter4.getPhysicsComponent().setPosition(new Vector2(0, 0));
		spatialMesh.addCollidable(testCharacter);
		spatialMesh.addCollidable(testCharacter2);
		spatialMesh.addCollidable(testCharacter3);
		spatialMesh.addCollidable(testCharacter4);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				if (i == 0 && j == 0) {
					Assert.assertEquals(4, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size);
				} else {
					Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size);
				}
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size);
			}
		}
	}

	@Test
	public void testDynamicArraysFilled1Character() {
		testCharacter.getPhysicsComponent().setPosition(new Vector2(0, 0));
		spatialMesh.addCollidable(testCharacter);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				if (i == 0 && j == 0) {
					Assert.assertEquals(1, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size);
				} else {
					Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size);
				}
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size);
			}
		}
	}

	@Test
	public void testcellsWithDynamicCollidablesNoCharacter() {
		Set<Point> cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		Set<Point> cellsWithDynamicExpected = new HashSet<>();
		Assert.assertEquals(cellsWithDynamic, cellsWithDynamicExpected);
	}

	@Test
	public void testcellsWithDynamicCollidables1Character() {
		testCharacter.getPhysicsComponent().setPosition(new Vector2(0, 0));
		spatialMesh.addCollidable(testCharacter);
		Set<Point> cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		Set<Point> cellsWithDynamicExpected = new HashSet<>();
		cellsWithDynamicExpected.add(new Point(0, 0));
		Assert.assertEquals(cellsWithDynamicExpected, cellsWithDynamic);
	}

	@Test
	public void testcellsWithDynamicCollidables2CharactersDifferentCell() {
		testCharacter.getPhysicsComponent().setPosition(new Vector2(0, 0));
		testCharacter2.getPhysicsComponent().setPosition(new Vector2(100, 0));
		spatialMesh.addCollidable(testCharacter);
		spatialMesh.addCollidable(testCharacter2);
		Set<Point> cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		Set<Point> cellsWithDynamicExpected = new HashSet<>();
		cellsWithDynamicExpected.add(new Point(0, 0));
		cellsWithDynamicExpected.add(new Point(2, 0));
		cellsWithDynamicExpected.add(new Point(3, 0));
		Assert.assertEquals(cellsWithDynamicExpected, cellsWithDynamic);
	}

	@Test
	public void testcellsWithDynamicCollidables2CharactersSameCell() {
		testCharacter.getPhysicsComponent().setPosition(new Vector2(0, 0));
		testCharacter2.getPhysicsComponent().setPosition(new Vector2(0, 0));
		spatialMesh.addCollidable(testCharacter);
		spatialMesh.addCollidable(testCharacter2);
		Set<Point> cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		Set<Point> cellsWithDynamicExpected = new HashSet<>();
		cellsWithDynamicExpected.add(new Point(0, 0));
		Assert.assertEquals(cellsWithDynamicExpected, cellsWithDynamic);
	}

	@Test
	public void testSizeInitialized() {
		Assert.assertEquals(spatialMesh.getNumberofCellsX(), WIDTH / Constants.SPATIAL_MESH_CELL_SIZE);
		Assert.assertEquals(spatialMesh.getNumberofCellsY(), HEIGHT / Constants.SPATIAL_MESH_CELL_SIZE);
	}

	@Test
	public void testSpatialMeshCellsIntitialized() {
		for (int x = 0; x < spatialMesh.getNumberofCellsX(); x++) {
			for (int y = 0; y < spatialMesh.getNumberofCellsY(); y++) {
				Assert.assertEquals(spatialMesh.getSpatialMesh()[x][y].getClass().getSimpleName(), "SpatialMeshCell");
			}
		}
	}

	@Test
	public void testGetCollidingCellsStaticBlock() {
		Set<Point> collidingCells = spatialMesh.getCollidingCells(testBlockBottom10High.getRectangle());
		Set<Point> collidingCellsExpected = new HashSet<>();
		collidingCellsExpected.add(new Point(0, 0));
		collidingCellsExpected.add(new Point(1, 0));
		collidingCellsExpected.add(new Point(2, 0));
		collidingCellsExpected.add(new Point(3, 0));
		collidingCellsExpected.add(new Point(4, 0));
		collidingCellsExpected.add(new Point(5, 0));
		collidingCellsExpected.add(new Point(6, 0));
		collidingCellsExpected.add(new Point(7, 0));
		collidingCellsExpected.add(new Point(8, 0));
		collidingCellsExpected.add(new Point(9, 0));
		Assert.assertEquals(collidingCellsExpected, collidingCells);
	}

	@Test
	public void testGetCollidingCellsCharacter() {
		testCharacter.getPhysicsComponent().setPosition(new Vector2(0, 0));
		Set<Point> collidingCells = spatialMesh.getCollidingCells(testCharacter.getRectangle());
		Set<Point> collidingCellsExpected = new HashSet<>();
		collidingCellsExpected.add(new Point(0, 0));
		Assert.assertEquals(collidingCellsExpected, collidingCells);
	}

	@Test
	public void testGetCollidingCellsCharacter160and160() {
		testCharacter.getPhysicsComponent().setPosition(new Vector2(160, 160));
		Set<Point> collidingCells = spatialMesh.getCollidingCells(testCharacter.getRectangle());
		Set<Point> collidingCellsExpected = new HashSet<>();
		collidingCellsExpected.add(new Point(4, 5));
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
