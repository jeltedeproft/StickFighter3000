package jelte.mygame.utility;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader.TextureAtlasParameter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData;
import com.badlogic.gdx.utils.Array;

import com.crashinvaders.basisu.gdx.BasisuTextureData;

/**
 * {@link AssetLoader} to load {@link TextureAtlas} instances. Passing a {@link TextureAtlasParameter} to
 * {@link AssetManager#load(String, Class, AssetLoaderParameters)} allows to specify whether the atlas regions should be flipped on the y-axis or not.
 *
 * @author mzechner
 */
public class BasisUniversalTextureAtlasLoader extends SynchronousAssetLoader<TextureAtlas, TextureAtlasLoader.TextureAtlasParameter> {
	public BasisUniversalTextureAtlasLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	TextureAtlasData data;

	@Override
	public TextureAtlas load(AssetManager assetManager, String fileName, FileHandle file, TextureAtlasParameter parameter) {
		// First off, load the basis texture.
		FileHandle textureFile = file.parent().child(fileName + ".basis");
		TextureData textureData = new BasisuTextureData(textureFile);
		Texture texture = new Texture(textureData);

		// Load the atlas data.
		TextureAtlas.TextureAtlasData atlasData = new TextureAtlas.TextureAtlasData(file, file.parent(), false);

		// The most important part - manually assign the texture
		// to all the pages before initiating the atlas object.
		// BEWARE: In case your atlas has multiple pages,
		// you should assign the appropriate textures for each page.
		// This example works for single-page atlases only.
		for (TextureAtlas.TextureAtlasData.Page page : atlasData.getPages()) {
			page.texture = texture;
		}

		// Then simply instantiate the atlas object.
		TextureAtlas atlas = new TextureAtlas(atlasData);
		return atlas;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle atlasFile, TextureAtlasParameter parameter) {
		Array<AssetDescriptor> dependencies = new Array();
		return dependencies;
	}

	static public class BasisuTextureAtlasParameter extends AssetLoaderParameters<TextureAtlas> {
		/** whether to flip the texture atlas vertically **/
		public boolean flip = false;

		public BasisuTextureAtlasParameter() {
		}

		public BasisuTextureAtlasParameter(boolean flip) {
			this.flip = flip;
		}
	}

}
