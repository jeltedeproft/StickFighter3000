package jelte.mygame.tests.utility;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.SpriteUtils;

@RunWith(GdxTestRunner.class)
public class TestSpriteUtils {

	@Test
	public void testCalculateAttackOffsetForAll() {
		TextureRegion[] animationFrames = new TextureRegion[3];
		animationFrames[0] = createMockTextureRegion(100, 100, 0);
		animationFrames[1] = createMockTextureRegion(100, 100, 30);
		animationFrames[2] = createMockTextureRegion(100, 100, 60);

		float maxAttackOffset = SpriteUtils.calculateAttackOffsetForAll(animationFrames);

		assertEquals(0, maxAttackOffset, 0.001f);
	}

	@Test
	public void testCalculateAttackOffset_Left() {
		TextureRegion animationFrame = createMockTextureRegion(100, 100, 30);
		boolean isLeft = true;

		float attackOffset = SpriteUtils.calculateAttackOffset(animationFrame, isLeft);

		assertEquals(70, attackOffset, 0.001f);
	}

	@Test
	public void testCalculateAttackOffset_Right() {
		TextureRegion animationFrame = createMockTextureRegion(100, 100, 70);
		boolean isLeft = false;

		float attackOffset = SpriteUtils.calculateAttackOffset(animationFrame, isLeft);

		assertEquals(30, attackOffset, 0.001f);
	}

	private TextureRegion createMockTextureRegion(int width, int height, int coloredPixelX) {
		MockPixmap pixmap = new MockPixmap(width, height);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x == coloredPixelX && y == height / 2) {
					pixmap.pixels[x][y] = Color.rgba8888(Color.RED); // Set a colored pixel
				} else {
					pixmap.pixels[x][y] = Color.rgba8888(Color.WHITE);
				}
			}
		}

		// Create the Texture object from the custom Pixmap
		Texture texture = new MockTexture(pixmap);

		TextureRegion textureRegion = new TextureRegion(texture);

		return textureRegion;
	}

}
