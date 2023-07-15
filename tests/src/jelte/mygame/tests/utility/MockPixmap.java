package jelte.mygame.tests.utility;

import com.badlogic.gdx.graphics.Pixmap;

public class MockPixmap extends Pixmap {
	public final int[][] pixels;

	public MockPixmap(int width, int height) {
		super(width, height, Pixmap.Format.RGBA8888);
		pixels = new int[width][height];
	}

	@Override
	public int getPixel(int x, int y) {
		return pixels[x][y];
	}

	// Implement other necessary methods of Pixmap (optional)
}
