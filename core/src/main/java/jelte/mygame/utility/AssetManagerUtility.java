package jelte.mygame.utility;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class AssetManagerUtility implements Disposable {
	private static final String TAG = AssetManagerUtility.class.getSimpleName();

	private static boolean loadersSet = false;

	public static final AssetManager assetManager = new AssetManager();
	private static InternalFileHandleResolver filePathResolver = new InternalFileHandleResolver();

	private static TmxMapLoader tmxMapLoader = new TmxMapLoader(filePathResolver);
	private static TextureLoader textureLoader = new TextureLoader(filePathResolver);
	private static ParticleEffectLoader particleEffectLoader = new ParticleEffectLoader(filePathResolver);
	private static SoundLoader soundLoader = new SoundLoader(filePathResolver);
	private static MusicLoader musicLoader = new MusicLoader(filePathResolver);
	private static TextureAtlasLoader textureAtlasLoader = new TextureAtlasLoader(filePathResolver);

	private static Map<String, Array<Sprite>> cachedAnimations = new HashMap<>();

	public static void loadMapAsset(final String mapFilenamePath) {
		loadAsset(mapFilenamePath, TiledMap.class);
	}

	public static TiledMap getMapAsset(final String mapFilenamePath) {
		return (TiledMap) getAsset(mapFilenamePath, TiledMap.class);
	}

	public static void loadTextureAsset(final String textureFilenamePath) {
		loadAsset(textureFilenamePath, Texture.class);
	}

	public static Texture getTextureAsset(final String textureFilenamePath) {
		return (Texture) getAsset(textureFilenamePath, Texture.class);
	}

	public static void loadParticleAsset(final String particleFilenamePath) {
		final ParticleEffectParameter pep = new ParticleEffectParameter();
		pep.atlasFile = Constants.SPRITES_ATLAS_PATH;
		assetManager.load(particleFilenamePath, ParticleEffect.class, pep);
		assetManager.finishLoadingAsset(particleFilenamePath);
	}

	public static ParticleEffect getParticleAsset(final String particleFilenamePath) {
		return (ParticleEffect) getAsset(particleFilenamePath, ParticleEffect.class);
	}

	public static void loadSoundAsset(final String soundFilenamePath) {
		loadAsset(soundFilenamePath, Sound.class);
	}

	public static Sound getSoundAsset(final String soundFilenamePath) {
		return (Sound) getAsset(soundFilenamePath, Sound.class);
	}

	public static void loadMusicAsset(final String musicFilenamePath) {
		loadAsset(musicFilenamePath, Music.class);
	}

	public static Music getMusicAsset(final String musicFilenamePath) {
		return (Music) getAsset(musicFilenamePath, Music.class);
	}

	public static void loadTextureAtlas(final String textureAtlasFilenamePath) {
		loadAsset(textureAtlasFilenamePath, TextureAtlas.class);
	}

	public static TextureAtlas getTextureAtlas(final String textureAtlasFilenamePath) {
		return (TextureAtlas) getAsset(textureAtlasFilenamePath, TextureAtlas.class);
	}

	public static void loadSkin(final String skinFilenamePath) {
		loadAsset(skinFilenamePath, Skin.class);
	}

	public static Skin getSkin(final String skinFilenamePath) {
		return (Skin) getAsset(skinFilenamePath, Skin.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void loadAsset(final String assetName, final Class className) {
		if (!loadersSet) {
			setLoaders();
		}

		if (!isAssetLoaded(assetName) && checkValidString(assetName)) {
			if (filePathResolver.resolve(assetName).exists()) {
				assetManager.load(assetName, className);
				assetManager.finishLoadingAsset(assetName);// block
				Gdx.app.debug(TAG, className.getSimpleName() + " loaded: " + assetName);
			} else {
				Gdx.app.debug(TAG, className.getSimpleName() + " doesn't exist: " + assetName);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getAsset(final String filenamePath, final Class className) {
		Object object = null;

		if (assetManager.isLoaded(filenamePath)) {
			object = assetManager.get(filenamePath, className);
		} else {
			Gdx.app.debug(TAG, className.getSimpleName() + " is not loaded: " + filenamePath);
		}

		return object;
	}

	public static void unloadAsset(final String assetFilenamePath) {
		if (assetManager.isLoaded(assetFilenamePath)) {
			assetManager.unload(assetFilenamePath);
		} else {
			Gdx.app.debug(TAG, "Asset is not loaded; Nothing to unload: " + assetFilenamePath);
		}
	}

	public static Sprite getSprite(String spriteName) {
		final TextureAtlas atlas = getTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		if (atlas != null) {
			return atlas.createSprite(spriteName);
		}
		Gdx.app.debug(TAG, "can't create sprite, texture atlas is null: " + Constants.SPRITES_ATLAS_PATH);
		return null;
	}

	public static AtlasRegion getAtlasRegion(String regionName) {
		final TextureAtlas atlas = getTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		if (atlas != null) {
			return atlas.findRegion(regionName);
		}
		Gdx.app.debug(TAG, "can't create sprite, texture atlas is null: " + Constants.SPRITES_ATLAS_PATH);
		return null;
	}

	public static Array<TextureAtlas.AtlasRegion> getAllRegionsWhichContainName(String spriteName) {
		final TextureAtlas atlas = getTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		if (atlas == null) {
			Gdx.app.debug(TAG, "can't get animation, texture atlas is null: " + Constants.SPRITES_ATLAS_PATH);
			return null;
		}
		final Array<AtlasRegion> allRegions = atlas.getRegions();
		final Array<AtlasRegion> matched = new Array(AtlasRegion.class);

		for (int i = 0, n = allRegions.size; i < n; i++) {
			final AtlasRegion region = allRegions.get(i);
			if (region.name.contains(spriteName)) {
				matched.add(new AtlasRegion(region));
			}
		}
		return matched;
	}

	public static Animation<Sprite> getAnimation(String animationName, float animationSpeed, PlayMode playMode) {
		final TextureAtlas atlas = getTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		if (atlas == null) {
			Gdx.app.debug(TAG, "can't get animation, texture atlas is null: " + Constants.SPRITES_ATLAS_PATH);
			return null;
		}

		Array<Sprite> cachedAnimation = cachedAnimations.get(animationName);

		if (cachedAnimation == null) {
			cachedAnimation = atlas.createSprites(animationName);
		}

		if ((cachedAnimation == null) || (cachedAnimation.size == 0)) {
			Gdx.app.debug(TAG, "can't get animation, no region found in atlas: " + animationName);
			return null;
		}

		return new Animation<>(animationSpeed, cachedAnimation, playMode);
	}

	public static Animation<Sprite> getBackgroundAnimation(String animationName, float animationSpeed, PlayMode playMode) {
		final TextureAtlas atlas = getTextureAtlas(Constants.SPRITES_BACKGROUND_ATLAS_PATH);
		if (atlas == null) {
			Gdx.app.debug(TAG, "can't get animation, texture atlas is null: " + Constants.SPRITES_BACKGROUND_ATLAS_PATH);
			return null;
		}

		Array<Sprite> cachedAnimation = cachedAnimations.get(animationName);

		if (cachedAnimation == null) {
			cachedAnimation = atlas.createSprites(animationName);
		}

		if ((cachedAnimation == null) || (cachedAnimation.size == 0)) {
			Gdx.app.debug(TAG, "can't get animation, no region found in atlas: " + animationName);
			return null;
		}

		return new Animation<>(animationSpeed, cachedAnimation, playMode);
	}

	public static float loadCompleted() {
		return assetManager.getProgress();
	}

	public static int numberAssetsQueued() {
		return assetManager.getQueuedAssets();
	}

	public static boolean updateAssetLoading() {
		return assetManager.update();
	}

	public static boolean isAssetLoaded(final String fileName) {
		return assetManager.isLoaded(fileName);
	}

	private static boolean checkValidString(final String string) {
		return !((string == null) || string.isEmpty());
	}

	private AssetManagerUtility() {

	}

	private static void setLoaders() {
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(filePathResolver));
		assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(filePathResolver));
		assetManager.setLoader(TiledMap.class, tmxMapLoader);
		assetManager.setLoader(Texture.class, textureLoader);
		assetManager.setLoader(ParticleEffect.class, particleEffectLoader);
		assetManager.setLoader(Sound.class, soundLoader);
		assetManager.setLoader(Music.class, musicLoader);
		assetManager.setLoader(TextureAtlas.class, textureAtlasLoader);
		// assetManager.setLoader(Skin.class, new FreeTypeSkinLoader(assetManager.getFileHandleResolver()));TODO readd after skin is added
		loadersSet = true;
	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}

}
