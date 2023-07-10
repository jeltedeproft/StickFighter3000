package jelte.mygame.tests.logic.collisions.spatialMesh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlockTop;
import jelte.mygame.logic.collisions.spatialMesh.SpatialMeshCell;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestSpatialMeshCell {

	@Test
	public void testAddCollidable() {
		SpatialMeshCell cell = new SpatialMeshCell();
		Collidable staticCollidable = new StaticBlockTop();
		Collidable dynamicCollidable = new StaticBlockTop();

		cell.addCollidable(staticCollidable);
		cell.addCollidable(dynamicCollidable);

		assertTrue(cell.isContainsStatic());
		assertTrue(cell.isContainsDynamic());
		assertTrue(cell.getStaticCollidables().contains(staticCollidable));
		assertTrue(cell.getDynamicCollidables().contains(dynamicCollidable));
	}

	@Test
	public void testRemoveCollidable() {
		SpatialMeshCell cell = new SpatialMeshCell();
		Collidable staticCollidable = new Collidable(true, false);
		Collidable dynamicCollidable = new Collidable(false, true);

		cell.addCollidable(staticCollidable);
		cell.addCollidable(dynamicCollidable);

		cell.removeCollidable(staticCollidable);

		assertFalse(cell.isContainsStatic());
		assertTrue(cell.isContainsDynamic());
		assertFalse(cell.getStaticCollidables().contains(staticCollidable));
		assertTrue(cell.getDynamicCollidables().contains(dynamicCollidable));
	}

	@Test
	public void testRemoveAll() {
		SpatialMeshCell cell = new SpatialMeshCell();
		Collidable staticCollidable = new Collidable(true, false);
		Collidable dynamicCollidable = new Collidable(false, true);

		cell.addCollidable(staticCollidable);
		cell.addCollidable(dynamicCollidable);

		cell.removeAll();

		assertFalse(cell.isContainsStatic());
		assertFalse(cell.isContainsDynamic());
		assertTrue(cell.getStaticCollidables().isEmpty());
		assertTrue(cell.getDynamicCollidables().isEmpty());
	}

	@Test
	public void testToString() {
		SpatialMeshCell cell = new SpatialMeshCell();
		Collidable staticCollidable = new Collidable(true, false);
		Collidable dynamicCollidable = new Collidable(false, true);

		Set<Collidable> staticCollidables = new HashSet<>();
		staticCollidables.add(staticCollidable);
		Set<Collidable> dynamicCollidables = new HashSet<>();
		dynamicCollidables.add(dynamicCollidable);

		cell.setStaticCollidables(staticCollidables);
		cell.setDynamicCollidables(dynamicCollidables);
		cell.setContainsStatic(true);
		cell.setContainsDynamic(true);

		String expectedString = "contains static : true\n"
				+
				"contains dynamic : true\n"
				+
				"static : \n"
				+
				staticCollidable.toString()
				+ "\n"
				+
				"dynamic : \n"
				+
				dynamicCollidable.toString();

		assertEquals(expectedString, cell.toString());
	}
}
