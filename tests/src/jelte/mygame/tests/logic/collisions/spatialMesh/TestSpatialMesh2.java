package jelte.mygame.tests.logic.collisions.spatialMesh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.collisions.CellPoint;
import jelte.mygame.logic.collisions.CollisionData;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlockTop;
import jelte.mygame.logic.collisions.spatialMesh.SpatialMesh;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestSpatialMesh2 {

	private SpatialMesh spatialMesh;
	private Set<Collidable> collidables;

	@Before
	public void setup() {
		Vector2 mapBoundaries = new Vector2(800, 600);
		spatialMesh = new SpatialMesh(mapBoundaries);

		// Initialize collidables for testing
		collidables = new HashSet<>();
		collidables.add(new StaticBlockTop(new Rectangle(100, 100, 50, 50)));
		collidables.add(new StaticBlockTop(new Rectangle(200, 200, 60, 60)));
		collidables.add(new StaticBlockTop(new Rectangle(300, 300, 70, 70)));
		collidables.add(new StaticBlockTop(new Rectangle(400, 400, 80, 80)));
	}

	@Test
	public void testInitializeStaticCollidables() {
		spatialMesh.addStatickCollidables(collidables);

		for (Collidable collidable : collidables) {
			Set<Collidable> staticCollidables = spatialMesh.getStaticCollidables((int) collidable.getRectangle().x, (int) collidable.getRectangle().y);
			assertTrue(staticCollidables.contains(collidable));
		}
	}

	@Test
	public void testGetCollidingCells() {
		Rectangle rect = new Rectangle(150, 150, 100, 100);
		Set<CellPoint> collidingCells = spatialMesh.getCollidingCells(rect);

		assertEquals(16, collidingCells.size());
		assertTrue(collidingCells.contains(new CellPoint(4, 4)));
		assertTrue(collidingCells.contains(new CellPoint(4, 5)));
		assertTrue(collidingCells.contains(new CellPoint(4, 6)));
		assertTrue(collidingCells.contains(new CellPoint(4, 7)));
		assertTrue(collidingCells.contains(new CellPoint(5, 5)));
		assertTrue(collidingCells.contains(new CellPoint(5, 6)));
		assertTrue(collidingCells.contains(new CellPoint(5, 7)));
		assertTrue(collidingCells.contains(new CellPoint(6, 5)));
		assertTrue(collidingCells.contains(new CellPoint(6, 6)));
		assertTrue(collidingCells.contains(new CellPoint(6, 7)));
		assertTrue(collidingCells.contains(new CellPoint(7, 5)));
		assertTrue(collidingCells.contains(new CellPoint(7, 6)));
		assertTrue(collidingCells.contains(new CellPoint(7, 7)));
	}

	@Test
	public void testRemoveAllCollidables() {
		spatialMesh.addStatickCollidables(collidables);

		spatialMesh.removeAllCollidables();

		for (int x = 0; x < spatialMesh.getNumberofCellsX(); x++) {
			for (int y = 0; y < spatialMesh.getNumberofCellsY(); y++) {
				Set<Collidable> staticCollidables = spatialMesh.getStaticCollidables(x, y);
				assertTrue(staticCollidables.isEmpty());
				Set<Collidable> dynamicCollidables = spatialMesh.getDynamicCollidables(x, y);
				assertTrue(dynamicCollidables.isEmpty());
			}
		}
	}

	@Test
	public void testUpdateCollidables() {
		spatialMesh.addStatickCollidables(collidables);

		// Update collidables by moving them
		for (Collidable collidable : collidables) {
			collidable.getRectangle().x += 10;
			collidable.getRectangle().y += 10;
		}

		spatialMesh.updateCollidables(collidables);

		for (Collidable collidable : collidables) {
			Set<Collidable> staticCollidables = spatialMesh.getStaticCollidables((int) collidable.getRectangle().x, (int) collidable.getRectangle().y);
			assertTrue(staticCollidables.contains(collidable));
		}
	}

	@Test
	public void testGetAllPossibleCollisions() {
		spatialMesh.addStatickCollidables(collidables);

		Array<CollisionData> collisionDatas = spatialMesh.getAllPossibleCollisions();

		assertEquals(0, collisionDatas.size); // Since the collidables are not overlapping, there should be no collisions
	}

}
