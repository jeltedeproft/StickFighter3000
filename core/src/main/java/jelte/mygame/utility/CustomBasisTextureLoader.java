package jelte.mygame.utility;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import com.crashinvaders.basisu.gdx.BasisuData;
import com.crashinvaders.basisu.gdx.BasisuGdxUtils;
import com.crashinvaders.basisu.gdx.BasisuTextureData;
import com.crashinvaders.basisu.gdx.BasisuTextureFormatSelector;
import com.crashinvaders.basisu.gdx.Ktx2Data;
import com.crashinvaders.basisu.wrapper.BasisuTranscoderTextureFormat;

public class CustomBasisTextureLoader extends AsynchronousAssetLoader<Texture, TextureLoader.TextureParameter> {

	BasisuTextureData textureData;

	public CustomBasisTextureLoader(FileHandleResolver resolver) {
		super(resolver);
		// We need to make sure this one is first time called
		// on the main thread and not during async texture loading.
		BasisuGdxUtils.initSupportedGlTextureFormats();
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle fileHandle, TextureLoader.TextureParameter parameter) {
		BasisuTextureData data;
		if (parameter instanceof BasisuTextureParameter basisParameter) {
			data = new BasisuTextureData(fileHandle, basisParameter.imageIndex, basisParameter.mipmapLevel);
			if (basisParameter.formatSelector != null) {
				textureData.setTextureFormatSelector(basisParameter.formatSelector);
			}
		} else {
			data = new BasisuTextureData(fileHandle);
		}
		data.prepare();
		textureData = data;
	}

	@Override
	public Texture loadSync(AssetManager manager, String fileName, FileHandle fileHandle, TextureLoader.TextureParameter parameter) {
		Texture texture = new Texture(this.textureData);
		this.textureData = null;

		if (parameter != null) {
			texture.setFilter(parameter.minFilter, parameter.magFilter);
			texture.setWrap(parameter.wrapU, parameter.wrapV);
		}

		return texture;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle fileHandle, TextureLoader.TextureParameter parameter) {
		return null;
	}

	/**
	 * Parameter class is an optional extension for the standard {@link com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter}.
	 */
	public static class BasisuTextureParameter extends TextureLoader.TextureParameter {
		public int imageIndex = 0;
		public int mipmapLevel = 0;
		public BasisuTextureFormatSelector formatSelector = null;

		public BasisuTextureParameter() {
		}

		/**
		 * Shortcut method to lock the transcoder to the specific texture format. Use it carefully as there's no single texture format to be supported by all
		 * the platforms (that's kinda the whole purpose of Basis Universal dynamic format selector...)
		 */
		public void setTextureFormat(final BasisuTranscoderTextureFormat formatToUse) {
			this.formatSelector = new BasisuTextureFormatSelector() {
				@Override
				public BasisuTranscoderTextureFormat resolveTextureFormat(BasisuData data, int imageIndex) {
					// Your lambda had (data, fileInfo, imageInfo) but this method only provides
					// BasisuData and imageIndex. You need to decide how to use them,
					// or if you even need them if you're always returning a fixed format.
					// If you always want to return the 'formatToUse' passed to setTextureFormat:
					return formatToUse;
				}

				@Override
				public BasisuTranscoderTextureFormat resolveTextureFormat(Ktx2Data data) {
					// Similarly, decide how to handle Ktx2Data, or just return the fixed format.
					return formatToUse;
				}
			};
		}
	}
}
