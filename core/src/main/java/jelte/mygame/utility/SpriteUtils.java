package jelte.mygame.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.File;

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

	public static float calculateLeftAttackOffset(TextureRegion animationFrame) {
		int frameWidth = animationFrame.getRegionWidth();
		int frameHeight = animationFrame.getRegionHeight();

		Texture texture = animationFrame.getTexture();
		TextureData textureData = texture.getTextureData();
		if (!textureData.isPrepared()) {
			textureData.prepare();
		}
		Pixmap pixmap = textureData.consumePixmap();

		float attackOffset = 0; // Initialize with maximum value for left offset

		for (int x = 0; x < frameWidth; x++) {
			for (int y = 0; y < frameHeight; y++) {
				Color framePixels = new Color(pixmap.getPixel(x, y));

				if (framePixels.a > 0) {
					attackOffset = Math.max(attackOffset, x); // Update the maximum for left offset
					break;
				}
			}
		}

		pixmap.dispose(); // Dispose the pixmap to avoid memory leaks

		return frameWidth - attackOffset;
	}

	public static float calculateRightAttackOffset(TextureRegion animationFrame) {
		int frameWidth = animationFrame.getRegionWidth();
		int frameHeight = animationFrame.getRegionHeight();

		Texture texture = animationFrame.getTexture();
		TextureData textureData = texture.getTextureData();
		if (!textureData.isPrepared()) {
			textureData.prepare();
		}
		Pixmap pixmap = textureData.consumePixmap();

		float attackOffset = frameWidth; // Initialize with maximum value for left offset

		for (int x = 0; x < frameWidth; x++) {
			for (int y = 0; y < frameHeight; y++) {
				Color framePixels = new Color(pixmap.getPixel(x, y));

				if (framePixels.a > 0) {
					attackOffset = Math.min(attackOffset, x); // Update the minimum for right offset
					break;
				}
			}
		}

		pixmap.dispose(); // Dispose the pixmap to avoid memory leaks

		return frameWidth - attackOffset;
	}

	public static TextureRegion convertToTextureRegion(String absolutePath) {
		FileHandle fileHandle = Gdx.files.absolute(absolutePath);
		Texture texture = new Texture(Gdx.files.internal(fileHandle.path()));
		TextureRegion textureRegion = new TextureRegion(texture);
		return textureRegion;
	}

	public static String imageNameFromAbsolutePath(String absolutePath) {
		File file = new File(absolutePath);
		String fileName = file.getName();
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
			return fileName.substring(0, dotIndex);
		}
		return fileName;
	}
}
