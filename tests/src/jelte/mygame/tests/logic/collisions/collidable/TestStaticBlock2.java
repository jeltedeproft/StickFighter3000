package jelte.mygame.tests.logic.collisions.collidable;

import com.badlogic.gdx.math.Rectangle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.collisions.collidable.StaticBlock;
import jelte.mygame.logic.collisions.collidable.StaticBlockBot;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestStaticBlock2 {
	private StaticBlock staticBlock;
	private Rectangle playerRect;

	@Before
	public void prepareSpatialMesh() {
		staticBlock = new StaticBlockBot(new Rectangle(0, 0, 10, 10));
	}

	@Test
	public void testGetCollidingCells() {
		playerRect = new Rectangle(0, 0, 10, 10);
		staticBlock.calculateOverlapPlayer(playerRect);
		Assert.assertEquals(staticBlock.getOverlapX(), 10, 0.0001);
		Assert.assertEquals(staticBlock.getOverlapY(), 10, 0.0001);
	}

	@Test
	public void testGetCollidingCells2() {
		playerRect = new Rectangle(0, 5, 10, 10);
		staticBlock.calculateOverlapPlayer(playerRect);
		Assert.assertEquals(staticBlock.getOverlapX(), 10, 0.0001);
		Assert.assertEquals(staticBlock.getOverlapY(), 5, 0.0001);
	}

	@Test
	public void testGetCollidingCells3() {
		playerRect = new Rectangle(100, 500, 10, 10);
		staticBlock.calculateOverlapPlayer(playerRect);
		Assert.assertEquals(staticBlock.getOverlapX(), 0, 0.0001);
		Assert.assertEquals(staticBlock.getOverlapY(), 0, 0.0001);
	}

	@Test
	public void testGetCollidingCellsNoSize() {
		playerRect = new Rectangle(0, 0, 0, 0);
		staticBlock.calculateOverlapPlayer(playerRect);
		Assert.assertEquals(staticBlock.getOverlapX(), 0, 0.0001);
		Assert.assertEquals(staticBlock.getOverlapY(), 0, 0.0001);
	}

}
