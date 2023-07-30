package jelte.mygame.tests.logic.collisions.spatialMesh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.collisions.CellPoint;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlock;
import jelte.mygame.logic.collisions.collidable.StaticBlockBot;
import jelte.mygame.logic.collisions.collidable.StaticBlockLeft;
import jelte.mygame.logic.collisions.collidable.StaticBlockPlatform;
import jelte.mygame.logic.collisions.collidable.StaticBlockRight;
import jelte.mygame.logic.collisions.collidable.StaticBlockTop;
import jelte.mygame.logic.collisions.spatialMesh.SpatialMesh;
import jelte.mygame.logic.physics.PhysicsComponent;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.logic.physics.SpellPhysicsComponent;
import jelte.mygame.logic.spells.SpellsEnum;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.exceptions.OutOfBoundsException;

@RunWith(GdxTestRunner.class)
public class TestSpatialMesh {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;

	private SpatialMesh spatialMesh;
	private PhysicsComponent testPhysicsComponent1;
	private PhysicsComponent testPhysicsComponent2;
	private PhysicsComponent testPhysicsComponent3;
	private PhysicsComponent testPhysicsComponent4;
	private PhysicsComponent testSpellPhysicsComponent1;
	private StaticBlock testBlockBottom10High;
	private StaticBlock testBlockLeft10Width;
	private StaticBlock testBlockRight10Width;
	private StaticBlock testBlockTop10High;
	private StaticBlock testBlockPlatform50Width;

	private Set<Collidable> collidables;
	private Set<Collidable> staticCollidables;

	@Before
	public void beforeEveryTest() {
		collidables = new HashSet<>();
		staticCollidables = new HashSet<>();
		spatialMesh = new SpatialMesh(new Vector2(WIDTH, HEIGHT));
		testPhysicsComponent1 = new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0));
		testPhysicsComponent2 = new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0));
		testPhysicsComponent3 = new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0));
		testPhysicsComponent4 = new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0));
		testSpellPhysicsComponent1 = new SpellPhysicsComponent(testPhysicsComponent1.getId(), SpellsEnum.ATTACK, new Vector2(0, 0));
		testSpellPhysicsComponent1.setDimensions(10, 10);
		testPhysicsComponent1.setDimensions(10, 10);
		testPhysicsComponent2.setDimensions(10, 10);
		testPhysicsComponent3.setDimensions(10, 10);
		testPhysicsComponent4.setDimensions(10, 10);
		testBlockBottom10High = new StaticBlockBot(new Rectangle(0, 0, 320, 10));
		testBlockLeft10Width = new StaticBlockLeft(new Rectangle(0, 0, 10, 320));
		testBlockRight10Width = new StaticBlockRight(new Rectangle(310, 0, 10, 320));
		testBlockTop10High = new StaticBlockTop(new Rectangle(0, 310, 320, 10));
		testBlockPlatform50Width = new StaticBlockPlatform(new Rectangle(160, 160, 50, 10));
	}

	@Test
	public void testUpdateWhileMoving() {
		testPhysicsComponent1.update(0f);
		collidables.add(testPhysicsComponent1);
		spatialMesh.updateCollidables(collidables);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				if (i == 0 && j == 0) {
					Assert.assertEquals(1, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				} else {
					Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				}
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}

		testPhysicsComponent1.setAcceleration(new Vector2(0, 10));
		testPhysicsComponent1.setVelocity(new Vector2(20, 0));
		testPhysicsComponent1.update(1f);
		spatialMesh.updateCollidables(collidables);
		testPhysicsComponent1.update(1f);
		spatialMesh.updateCollidables(collidables);
		testPhysicsComponent1.update(1f);
		spatialMesh.updateCollidables(collidables);
		Map<CellPoint, Integer> expectedDynamicArraySizes = new HashMap<>();
		expectedDynamicArraySizes.put(new CellPoint(1, 0), 1);
		expectedDynamicArraySizes.put(new CellPoint(2, 0), 1);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				CellPoint testPoint = new CellPoint(i, j);
				if (expectedDynamicArraySizes.containsKey(testPoint)) {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), (int) expectedDynamicArraySizes.get(testPoint), spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				} else {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				}
				Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}

	}

	@Test
	public void test2Characters1Spell1Static() {
		collidables.add(testPhysicsComponent1);
		collidables.add(testPhysicsComponent2);
		collidables.add(testSpellPhysicsComponent1);
		staticCollidables.add(testBlockBottom10High);
		spatialMesh.addStatickCollidables(staticCollidables);
		spatialMesh.updateCollidables(collidables);
		Map<CellPoint, Integer> expectedStaticArraySizes = new HashMap<>();
		expectedStaticArraySizes.put(new CellPoint(0, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(1, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(2, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(3, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(4, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(5, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(6, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(7, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(8, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(9, 0), 1);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				CellPoint testPoint = new CellPoint(i, j);
				if (i == 0 && j == 0) {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), 3, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
					Assert.assertEquals(String.format("for (%d,%d)", i, j), 1, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
				} else {
					if (expectedStaticArraySizes.containsKey(testPoint)) {
						Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
						Assert.assertEquals(String.format("for (%d,%d)", i, j), (int) expectedStaticArraySizes.get(testPoint), spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
					} else {
						Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
						Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
					}
				}

			}
		}

		testPhysicsComponent1.setPosition(new Vector2(160, 160));
		testPhysicsComponent2.setPosition(new Vector2(160, 160));
		spatialMesh.updateCollidables(collidables);
		Map<CellPoint, Integer> expectedDynamicArraySizes = new HashMap<>();
		expectedDynamicArraySizes.put(new CellPoint(0, 0), 1);
		expectedDynamicArraySizes.put(new CellPoint(5, 5), 2);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				CellPoint testPoint = new CellPoint(i, j);
				if (expectedDynamicArraySizes.containsKey(testPoint)) {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), (int) expectedDynamicArraySizes.get(testPoint), spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				} else {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				}
				if (expectedStaticArraySizes.containsKey(testPoint)) {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), (int) expectedStaticArraySizes.get(testPoint), spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
				} else {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
				}
			}
		}
	}

	@Test
	public void testUpdate2CharactersSameCell() {
		testPhysicsComponent1.update(0f);
		testPhysicsComponent2.update(0f);
		collidables.add(testPhysicsComponent1);
		collidables.add(testPhysicsComponent2);
		spatialMesh.updateCollidables(collidables);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				if (i == 0 && j == 0) {
					Assert.assertEquals(2, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				} else {
					Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				}
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}

		testPhysicsComponent1.setPosition(new Vector2(160, 160));
		testPhysicsComponent2.setPosition(new Vector2(160, 160));
		spatialMesh.updateCollidables(collidables);
		Map<CellPoint, Integer> expectedDynamicArraySizes = new HashMap<>();
		expectedDynamicArraySizes.put(new CellPoint(5, 5), 2);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				CellPoint testPoint = new CellPoint(i, j);
				if (expectedDynamicArraySizes.containsKey(testPoint)) {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), (int) expectedDynamicArraySizes.get(testPoint), spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				} else {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				}
				Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}
	}

	@Test
	public void testUpdateCellCharacter() {
		testPhysicsComponent1.update(0f);
		collidables.add(testPhysicsComponent1);
		spatialMesh.updateCollidables(collidables);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				if (i == 0 && j == 0) {
					Assert.assertEquals(1, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				} else {
					Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				}
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}

		testPhysicsComponent1.setPosition(new Vector2(160, 160));
		spatialMesh.updateCollidables(collidables);
		Map<CellPoint, Integer> expectedDynamicArraySizes = new HashMap<>();
		expectedDynamicArraySizes.put(new CellPoint(5, 5), 1);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				CellPoint testPoint = new CellPoint(i, j);
				if (expectedDynamicArraySizes.containsKey(testPoint)) {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), (int) expectedDynamicArraySizes.get(testPoint), spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				} else {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				}
				Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}

	}

	@Test
	public void testUpdateCellCharacterNotMoved() {
		testPhysicsComponent1.update(0f);
		collidables.add(testPhysicsComponent1);
		spatialMesh.updateCollidables(collidables);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				if (i == 0 && j == 0) {
					Assert.assertEquals(1, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				} else {
					Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				}
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}

		testPhysicsComponent1.update(0f);
		spatialMesh.updateCollidables(collidables);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				if (i == 0 && j == 0) {
					Assert.assertEquals(1, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				} else {
					Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				}
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}
	}

	@Test
	public void testAddMultipleTimesSameCharacter() {
		collidables.add(testPhysicsComponent1);
		collidables.add(testPhysicsComponent1);
		collidables.add(testPhysicsComponent1);
		collidables.add(testPhysicsComponent1);
		collidables.add(testPhysicsComponent1);
		collidables.add(testPhysicsComponent1);
		collidables.add(testPhysicsComponent1);
		spatialMesh.updateCollidables(collidables);
		Set<CellPoint> cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		Set<CellPoint> cellsWithDynamicExpected = new HashSet<>();
		cellsWithDynamicExpected.add(new CellPoint(0, 0));
		Assert.assertEquals(cellsWithDynamicExpected, cellsWithDynamic);

		collidables.remove(testPhysicsComponent1);
		spatialMesh.updateCollidables(collidables);
		cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		cellsWithDynamicExpected = new HashSet<>();
		Assert.assertEquals(cellsWithDynamicExpected, cellsWithDynamic);
	}

	@Test
	public void testRemoveMultipleTimesCharacter() {
		collidables.add(testPhysicsComponent1);
		spatialMesh.updateCollidables(collidables);
		Set<CellPoint> cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		Set<CellPoint> cellsWithDynamicExpected = new HashSet<>();
		cellsWithDynamicExpected.add(new CellPoint(0, 0));
		Assert.assertEquals(cellsWithDynamicExpected, cellsWithDynamic);

		collidables.remove(testPhysicsComponent1);
		collidables.remove(testPhysicsComponent1);
		collidables.remove(testPhysicsComponent1);
		collidables.remove(testPhysicsComponent1);
		collidables.remove(testPhysicsComponent1);
		collidables.remove(testPhysicsComponent1);
		collidables.remove(testPhysicsComponent1);
		collidables.remove(testPhysicsComponent1);
		spatialMesh.updateCollidables(collidables);
		cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		cellsWithDynamicExpected = new HashSet<>();
		Assert.assertEquals(cellsWithDynamicExpected, cellsWithDynamic);
	}

	@Test
	public void testRemoveCellsDynamicCollidable() {
		collidables.add(testPhysicsComponent1);
		spatialMesh.updateCollidables(collidables);
		Set<CellPoint> cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		Set<CellPoint> cellsWithDynamicExpected = new HashSet<>();
		cellsWithDynamicExpected.add(new CellPoint(0, 0));
		Assert.assertEquals(cellsWithDynamicExpected, cellsWithDynamic);

		collidables.remove(testPhysicsComponent1);
		spatialMesh.updateCollidables(collidables);
		cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		cellsWithDynamicExpected = new HashSet<>();
		Assert.assertEquals(cellsWithDynamicExpected, cellsWithDynamic);
	}

	@Test
	public void testRemoveDynamicArraysFilled1Character() {
		collidables.add(testPhysicsComponent1);
		spatialMesh.updateCollidables(collidables);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				if (i == 0 && j == 0) {
					Assert.assertEquals(1, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				} else {
					Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				}
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}
		collidables.remove(testPhysicsComponent1);
		spatialMesh.updateCollidables(collidables);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}
	}

	@Test
	public void testRemoveDynamicArraysFilled4Characters() {
		testPhysicsComponent2.setPosition(new Vector2(50, 40));
		testPhysicsComponent3.setPosition(new Vector2(100, 0));
		testPhysicsComponent4.setPosition(new Vector2(200, 205));
		collidables.add(testPhysicsComponent1);
		collidables.add(testPhysicsComponent2);
		collidables.add(testPhysicsComponent3);
		collidables.add(testPhysicsComponent4);
		spatialMesh.updateCollidables(collidables);
		Map<CellPoint, Integer> expectedDynamicArraySizes = new HashMap<>();
		expectedDynamicArraySizes.put(new CellPoint(0, 0), 1);
		expectedDynamicArraySizes.put(new CellPoint(1, 1), 1);
		expectedDynamicArraySizes.put(new CellPoint(3, 0), 1);
		expectedDynamicArraySizes.put(new CellPoint(6, 6), 1);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				CellPoint testPoint = new CellPoint(i, j);
				if (expectedDynamicArraySizes.containsKey(testPoint)) {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), (int) expectedDynamicArraySizes.get(testPoint), spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				} else {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				}
				Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}

		collidables.remove(testPhysicsComponent1);
		collidables.remove(testPhysicsComponent2);
		collidables.remove(testPhysicsComponent3);
		collidables.remove(testPhysicsComponent4);
		spatialMesh.updateCollidables(collidables);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}
	}

	@Test
	public void testAddStaticBlockMultipleTimes() {
		staticCollidables.add(testBlockPlatform50Width);
		staticCollidables.add(testBlockPlatform50Width);
		staticCollidables.add(testBlockPlatform50Width);
		staticCollidables.add(testBlockPlatform50Width);
		staticCollidables.add(testBlockPlatform50Width);
		staticCollidables.add(testBlockPlatform50Width);
		staticCollidables.add(testBlockPlatform50Width);
		spatialMesh.addStatickCollidables(staticCollidables);
		spatialMesh.updateCollidables(collidables);
		Map<CellPoint, Integer> expectedStaticArraySizes = new HashMap<>();
		expectedStaticArraySizes.put(new CellPoint(5, 5), 1);
		expectedStaticArraySizes.put(new CellPoint(6, 5), 1);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				CellPoint testPoint = new CellPoint(i, j);
				if (expectedStaticArraySizes.containsKey(testPoint)) {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), (int) expectedStaticArraySizes.get(testPoint), spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
				} else {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
				}
				Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
			}
		}

	}

	@Test
	public void testStaticArraysFilledBlock() {
		staticCollidables.add(testBlockPlatform50Width);
		spatialMesh.addStatickCollidables(staticCollidables);
		spatialMesh.updateCollidables(collidables);
		Map<CellPoint, Integer> expectedStaticArraySizes = new HashMap<>();
		expectedStaticArraySizes.put(new CellPoint(5, 5), 1);
		expectedStaticArraySizes.put(new CellPoint(6, 5), 1);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				CellPoint testPoint = new CellPoint(i, j);
				if (expectedStaticArraySizes.containsKey(testPoint)) {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), (int) expectedStaticArraySizes.get(testPoint), spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
				} else {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
				}
				Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
			}
		}
	}

	@Test
	public void testStaticArraysFilled5BlocksDifferentPlaces() {
		staticCollidables.add(testBlockBottom10High);
		staticCollidables.add(testBlockLeft10Width);
		staticCollidables.add(testBlockRight10Width);
		staticCollidables.add(testBlockTop10High);
		staticCollidables.add(testBlockPlatform50Width);
		spatialMesh.addStatickCollidables(staticCollidables);
		spatialMesh.updateCollidables(collidables);
		Map<CellPoint, Integer> expectedStaticArraySizes = new HashMap<>();
		expectedStaticArraySizes.put(new CellPoint(0, 0), 2);
		expectedStaticArraySizes.put(new CellPoint(0, 1), 1);
		expectedStaticArraySizes.put(new CellPoint(0, 2), 1);
		expectedStaticArraySizes.put(new CellPoint(0, 3), 1);
		expectedStaticArraySizes.put(new CellPoint(0, 4), 1);
		expectedStaticArraySizes.put(new CellPoint(0, 5), 1);
		expectedStaticArraySizes.put(new CellPoint(0, 6), 1);
		expectedStaticArraySizes.put(new CellPoint(0, 7), 1);
		expectedStaticArraySizes.put(new CellPoint(0, 8), 1);
		expectedStaticArraySizes.put(new CellPoint(0, 9), 2);
		expectedStaticArraySizes.put(new CellPoint(1, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(2, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(3, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(4, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(5, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(6, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(7, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(8, 0), 1);
		expectedStaticArraySizes.put(new CellPoint(9, 0), 2);
		expectedStaticArraySizes.put(new CellPoint(9, 1), 1);
		expectedStaticArraySizes.put(new CellPoint(9, 2), 1);
		expectedStaticArraySizes.put(new CellPoint(9, 3), 1);
		expectedStaticArraySizes.put(new CellPoint(9, 4), 1);
		expectedStaticArraySizes.put(new CellPoint(9, 5), 1);
		expectedStaticArraySizes.put(new CellPoint(9, 6), 1);
		expectedStaticArraySizes.put(new CellPoint(9, 7), 1);
		expectedStaticArraySizes.put(new CellPoint(9, 8), 1);
		expectedStaticArraySizes.put(new CellPoint(9, 9), 2);
		expectedStaticArraySizes.put(new CellPoint(1, 9), 1);
		expectedStaticArraySizes.put(new CellPoint(2, 9), 1);
		expectedStaticArraySizes.put(new CellPoint(3, 9), 1);
		expectedStaticArraySizes.put(new CellPoint(4, 9), 1);
		expectedStaticArraySizes.put(new CellPoint(5, 9), 1);
		expectedStaticArraySizes.put(new CellPoint(6, 9), 1);
		expectedStaticArraySizes.put(new CellPoint(7, 9), 1);
		expectedStaticArraySizes.put(new CellPoint(8, 9), 1);
		expectedStaticArraySizes.put(new CellPoint(5, 5), 1);
		expectedStaticArraySizes.put(new CellPoint(6, 5), 1);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				CellPoint testPoint = new CellPoint(i, j);
				if (expectedStaticArraySizes.containsKey(testPoint)) {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), (int) expectedStaticArraySizes.get(testPoint), spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
				} else {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
				}
				Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
			}
		}
	}

	@Test
	public void testDynamicArraysFilled4CharactersDifferentPlaces() {
		testPhysicsComponent1.setPosition(new Vector2(0, 0));
		testPhysicsComponent2.setPosition(new Vector2(50, 40));
		testPhysicsComponent3.setPosition(new Vector2(100, 0));
		testPhysicsComponent4.setPosition(new Vector2(200, 205));
		collidables.add(testPhysicsComponent1);
		collidables.add(testPhysicsComponent2);
		collidables.add(testPhysicsComponent3);
		collidables.add(testPhysicsComponent4);
		spatialMesh.updateCollidables(collidables);
		Map<CellPoint, Integer> expectedDynamicArraySizes = new HashMap<>();
		expectedDynamicArraySizes.put(new CellPoint(0, 0), 1);
		expectedDynamicArraySizes.put(new CellPoint(1, 1), 1);
		expectedDynamicArraySizes.put(new CellPoint(3, 0), 1);
		expectedDynamicArraySizes.put(new CellPoint(6, 6), 1);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				CellPoint testPoint = new CellPoint(i, j);
				if (expectedDynamicArraySizes.containsKey(testPoint)) {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), (int) expectedDynamicArraySizes.get(testPoint), spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				} else {
					Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				}
				Assert.assertEquals(String.format("for (%d,%d)", i, j), 0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}
	}

	@Test
	public void testDynamicArraysFilled4CharactersSamePlace() {
		collidables.add(testPhysicsComponent1);
		collidables.add(testPhysicsComponent2);
		collidables.add(testPhysicsComponent3);
		collidables.add(testPhysicsComponent4);
		spatialMesh.updateCollidables(collidables);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				if (i == 0 && j == 0) {
					Assert.assertEquals(4, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				} else {
					Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				}
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}
	}

	@Test
	public void testDynamicArraysFilled1Character() {
		collidables.add(testPhysicsComponent1);
		spatialMesh.updateCollidables(collidables);
		for (int i = 0; i < spatialMesh.getNumberofCellsX(); i++) {
			for (int j = 0; j < spatialMesh.getNumberofCellsY(); j++) {
				if (i == 0 && j == 0) {
					Assert.assertEquals(1, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				} else {
					Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getDynamicCollidables().size());
				}
				Assert.assertEquals(0, spatialMesh.getSpatialMesh()[i][j].getStaticCollidables().size());
			}
		}
	}

	@Test
	public void testcellsWithDynamicCollidablesNoCharacter() {
		Set<CellPoint> cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		Set<CellPoint> cellsWithDynamicExpected = new HashSet<>();
		Assert.assertEquals(cellsWithDynamic, cellsWithDynamicExpected);
	}

	@Test
	public void testcellsWithDynamicCollidables1Character() {
		collidables.add(testPhysicsComponent1);
		spatialMesh.updateCollidables(collidables);
		Set<CellPoint> cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		Set<CellPoint> cellsWithDynamicExpected = new HashSet<>();
		cellsWithDynamicExpected.add(new CellPoint(0, 0));
		Assert.assertEquals(cellsWithDynamicExpected, cellsWithDynamic);
	}

	@Test
	public void testcellsWithDynamicCollidables2CharactersDifferentCell() {
		testPhysicsComponent2.setPosition(new Vector2(100, 0));
		collidables.add(testPhysicsComponent1);
		collidables.add(testPhysicsComponent2);
		spatialMesh.updateCollidables(collidables);
		Set<CellPoint> cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		Set<CellPoint> cellsWithDynamicExpected = new HashSet<>();
		cellsWithDynamicExpected.add(new CellPoint(0, 0));
		cellsWithDynamicExpected.add(new CellPoint(3, 0));
		Assert.assertEquals(cellsWithDynamicExpected, cellsWithDynamic);
	}

	@Test
	public void testcellsWithDynamicCollidables2CharactersSameCell() {
		collidables.add(testPhysicsComponent1);
		collidables.add(testPhysicsComponent2);
		spatialMesh.updateCollidables(collidables);
		Set<CellPoint> cellsWithDynamic = spatialMesh.getCellsWithDynamicCollidables();
		Set<CellPoint> cellsWithDynamicExpected = new HashSet<>();
		cellsWithDynamicExpected.add(new CellPoint(0, 0));
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
		Set<CellPoint> collidingCells = spatialMesh.getCollidingCells(testBlockBottom10High.getRectangle());
		Set<CellPoint> collidingCellsExpected = new HashSet<>();
		collidingCellsExpected.add(new CellPoint(0, 0));
		collidingCellsExpected.add(new CellPoint(1, 0));
		collidingCellsExpected.add(new CellPoint(2, 0));
		collidingCellsExpected.add(new CellPoint(3, 0));
		collidingCellsExpected.add(new CellPoint(4, 0));
		collidingCellsExpected.add(new CellPoint(5, 0));
		collidingCellsExpected.add(new CellPoint(6, 0));
		collidingCellsExpected.add(new CellPoint(7, 0));
		collidingCellsExpected.add(new CellPoint(8, 0));
		collidingCellsExpected.add(new CellPoint(9, 0));
		Assert.assertEquals(collidingCellsExpected, collidingCells);
	}

	@Test
	public void testGetCollidingCellsCharacter() {
		testPhysicsComponent1.setPosition(new Vector2(0, 0));
		Set<CellPoint> collidingCells = spatialMesh.getCollidingCells(testPhysicsComponent1.getRectangle());
		Set<CellPoint> collidingCellsExpected = new HashSet<>();
		collidingCellsExpected.add(new CellPoint(0, 0));
		Assert.assertEquals(collidingCellsExpected, collidingCells);
	}

	@Test
	public void testGetCollidingCellsCharacter160and160() {
		testPhysicsComponent1.setPosition(new Vector2(160, 160));
		Set<CellPoint> collidingCells = spatialMesh.getCollidingCells(testPhysicsComponent1.getRectangle());
		Set<CellPoint> collidingCellsExpected = new HashSet<>();
		collidingCellsExpected.add(new CellPoint(5, 5));
		Assert.assertEquals(collidingCellsExpected, collidingCells);
	}

	@Test
	public void testGetCollidingCellsCharacter100and11() {
		testPhysicsComponent1.setPosition(new Vector2(100.0f, 11.771829f));
		Set<CellPoint> collidingCells = spatialMesh.getCollidingCells(testPhysicsComponent1.getRectangle());
		Set<CellPoint> collidingCellsExpected = new HashSet<>();
		collidingCellsExpected.add(new CellPoint(3, 0));
		Assert.assertEquals(collidingCellsExpected, collidingCells);
	}

	@Test
	public void testGetCellAtNegativeXShouldThrowException() throws OutOfBoundsException {
		try {
			Assert.assertEquals(0, spatialMesh.getCellX(-50));
			fail("getCellX should throw an exception for a negative number");
		} catch (OutOfBoundsException expected) {
			assertEquals("-50 is a negative number, can't get spatialMeshCell", expected.getMessage());
		}
	}

	@Test
	public void testGetCellAtNegativeYShouldThrowException() throws OutOfBoundsException {
		try {
			Assert.assertEquals(0, spatialMesh.getCellY(-50));
			fail("getCellY should throw an exception for a negative number");
		} catch (OutOfBoundsException expected) {
			assertEquals("-50 is a negative number, can't get spatialMeshCell", expected.getMessage());
		}
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
	public void testGetCollidingCells() {
		Rectangle rectangle = new Rectangle();
		rectangle.x = 326.31165f;
		rectangle.y = 319.88217f;
		rectangle.width = 0f;
		rectangle.height = 0f;
		Set<CellPoint> cells = spatialMesh.getCollidingCells(rectangle);
		Assert.assertTrue(cells.size() == 1);
		Assert.assertTrue(cells.contains(new CellPoint(10, 9)));
	}

	@Test
	public void testGetCollidingCellsOldrectangle() {
		Rectangle rectangle = new Rectangle();
		rectangle.x = 357.15674f;
		rectangle.y = 315.64005f;
		rectangle.width = 0f;
		rectangle.height = 0f;
		Set<CellPoint> cells = spatialMesh.getCollidingCells(rectangle);
		Assert.assertTrue(cells.size() == 1);
		Assert.assertTrue(cells.contains(new CellPoint(11, 9)));
	}

	@Test
	public void testGetCellXAt10000ShouldThrowException() throws OutOfBoundsException {
		try {
			Assert.assertEquals(0, spatialMesh.getCellX(10000));
			fail("getCellX should throw an exception for a negative number");
		} catch (OutOfBoundsException expected) {
			assertEquals("10000 is bigger than map size 800,0, can't get spatialMeshCell", expected.getMessage());
		}
	}

	@Test
	public void testGetCellYAt10000ShouldThrowException() throws OutOfBoundsException {
		try {
			Assert.assertEquals(0, spatialMesh.getCellY(10000));
			fail("getCellY should throw an exception for a negative number");
		} catch (OutOfBoundsException expected) {
			assertEquals("10000 is bigger than map size 800,0, can't get spatialMeshCell", expected.getMessage());
		}
	}

}
