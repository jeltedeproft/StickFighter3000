package jelte.mygame.tests.logic.collisions.spatialMesh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector2;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlockBot;
import jelte.mygame.logic.collisions.spatialMesh.SpatialMeshCell;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestSpatialMeshCell {

	@Test
	public void testAddCollidable() {
		SpatialMeshCell cell = new SpatialMeshCell();
		int x = 10;
		int y = 20;
		int width = 30;
		int height = 40;

		Collidable staticCollidable = new StaticBlockBot(x, y, width, height);
		Collidable dynamicCollidable = new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0));

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
		int x = 10;
		int y = 20;
		int width = 30;
		int height = 40;

		Collidable staticCollidable = new StaticBlockBot(x, y, width, height);
		Collidable dynamicCollidable = new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0));

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
		int x = 10;
		int y = 20;
		int width = 30;
		int height = 40;

		Collidable staticCollidable = new StaticBlockBot(x, y, width, height);
		Collidable dynamicCollidable = new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0));

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
		int x = 10;
		int y = 20;
		int width = 30;
		int height = 40;

		Collidable staticCollidable = new StaticBlockBot(x, y, width, height);
		Collidable dynamicCollidable = new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0));

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
				+
				"dynamic : \n"
				+
				dynamicCollidable.toString();

		assertEquals(expectedString, cell.toString());
	}
}
