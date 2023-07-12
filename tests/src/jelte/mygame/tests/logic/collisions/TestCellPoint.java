package jelte.mygame.tests.logic.collisions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.collisions.CellPoint;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestCellPoint {

	@Test
	public void testConstructorAndGetters() {
		CellPoint cellPoint = new CellPoint(5, 10);

		assertEquals(5, cellPoint.x);
		assertEquals(10, cellPoint.y);
		assertEquals(5.0, cellPoint.getX(), 0.001);
		assertEquals(10.0, cellPoint.getY(), 0.001);
	}

	@Test
	public void testDefaultConstructor() {
		CellPoint cellPoint = new CellPoint();

		assertEquals(0, cellPoint.x);
		assertEquals(0, cellPoint.y);
		assertEquals(0.0, cellPoint.getX(), 0.001);
		assertEquals(0.0, cellPoint.getY(), 0.001);
	}

	@Test
	public void testMove() {
		CellPoint cellPoint = new CellPoint();

		cellPoint.move(3, 7);

		assertEquals(3, cellPoint.x);
		assertEquals(7, cellPoint.y);
	}

	@Test
	public void testTranslate() {
		CellPoint cellPoint = new CellPoint(2, 4);

		cellPoint.translate(3, -1);

		assertEquals(5, cellPoint.x);
		assertEquals(3, cellPoint.y);
	}

	@Test
	public void testEquals() {
		CellPoint cellPoint1 = new CellPoint(2, 4);
		CellPoint cellPoint2 = new CellPoint(2, 4);
		CellPoint cellPoint3 = new CellPoint(5, 4);

		assertTrue(cellPoint1.equals(cellPoint2));
		assertFalse(cellPoint1.equals(cellPoint3));
	}
}
