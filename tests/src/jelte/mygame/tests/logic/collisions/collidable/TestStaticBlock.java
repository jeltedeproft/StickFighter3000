package jelte.mygame.tests.logic.collisions.collidable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Rectangle;

import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.collisions.collidable.StaticBlock;
import jelte.mygame.logic.collisions.collidable.StaticBlockBot;
import jelte.mygame.logic.collisions.collidable.StaticBlockTop;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestStaticBlock {

	@Test
	public void testStaticBlock() {
		int x = 10;
		int y = 20;
		int width = 30;
		int height = 40;

		StaticBlock staticBlock = new StaticBlockBot(x, y, width, height);

		assertEquals(x, staticBlock.x, 0.001);
		assertEquals(y, staticBlock.y, 0.001);
		assertEquals(width, staticBlock.width, 0.001);
		assertEquals(height, staticBlock.height, 0.001);
	}

	@Test
	public void testCalculateOverlapPlayer() {
		int x = 10;
		int y = 20;
		int width = 30;
		int height = 40;

		StaticBlock staticBlock = new StaticBlockTop(x, y, width, height);

		// Create a player rectangle
		int playerX = 15;
		int playerY = 25;
		int playerWidth = 20;
		int playerHeight = 30;
		Rectangle playerRect = new Rectangle(playerX, playerY, playerWidth, playerHeight);

		staticBlock.calculateOverlapPlayer(playerRect);

		// Check if the overlapX and overlapY values are calculated correctly
		assertEquals(20, staticBlock.getOverlapX(), 0.001);
		assertEquals(30, staticBlock.getOverlapY(), 0.001);
	}

	@Test
	public void testCompareTo() {
		int x1 = 10;
		int y1 = 20;
		int width1 = 30;
		int height1 = 40;

		int x2 = 5;
		int y2 = 15;
		int width2 = 20;
		int height2 = 25;

		StaticBlock staticBlock1 = new StaticBlockTop(x1, y1, width1, height1);
		StaticBlock staticBlock2 = new StaticBlockTop(x2, y2, width2, height2);

		// Create a player rectangle
		int playerX = 15;
		int playerY = 25;
		int playerWidth = 20;
		int playerHeight = 30;
		Rectangle playerRect = new Rectangle(playerX, playerY, playerWidth, playerHeight);

		staticBlock1.calculateOverlapPlayer(playerRect);
		staticBlock2.calculateOverlapPlayer(playerRect);

		// Compare the two static blocks
		int result = staticBlock1.compareTo(staticBlock2);

		// The result should be negative since staticBlock1 has a smaller total overlap
		assertTrue(result < 0);
	}

}
