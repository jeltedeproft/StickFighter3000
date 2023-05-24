package jelte.mygame.utility;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteUtils {
	public static float calculateAttackOffsetForAll(TextureRegion[] animationFrames) {
		float maxAttackOffset = 0;

		for (TextureRegion frame : animationFrames) {
			int frameWidth = frame.getRegionWidth();
			int frameHeight = frame.getRegionHeight();

			Pixmap pixmap = frame.getTexture().getTextureData().consumePixmap();
			float attackOffset = frameWidth;

			for (int x = 0; x < frameWidth; x++) {
				for (int y = 0; y < frameHeight; y++) {
					Color framePixels = new Color(pixmap.getPixel(x, y));

					if (framePixels.a > 0) {
						attackOffset = Math.min(attackOffset, x);
						break;
					}
				}
			}

			maxAttackOffset = Math.max(maxAttackOffset, attackOffset);

			pixmap.dispose(); // Dispose the pixmap to avoid memory leaks
		}

		return maxAttackOffset;
	}

	public static float calculateAttackOffset(TextureRegion animationFrame) {
		float maxAttackOffset = 0;

		int frameWidth = animationFrame.getRegionWidth();
		int frameHeight = animationFrame.getRegionHeight();

		Texture texture = animationFrame.getTexture();
		TextureData textureData = texture.getTextureData();
		if (!textureData.isPrepared()) {
			textureData.prepare();
		}
		Pixmap pixmap = textureData.consumePixmap();

		float attackOffset = 0;

		for (int x = 0; x < frameWidth; x++) {
			for (int y = 0; y < frameHeight; y++) {
				Color framePixels = new Color(pixmap.getPixel(x, y));

				if (framePixels.a > 0) {
					attackOffset = Math.max(attackOffset, x);
					break;
				}
			}
		}

		maxAttackOffset = Math.max(maxAttackOffset, attackOffset);

		pixmap.dispose(); // Dispose the pixmap to avoid memory leaks

		return maxAttackOffset;
	}
}
