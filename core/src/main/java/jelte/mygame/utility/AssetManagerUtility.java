package jelte.mygame.utility;

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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.crashinvaders.basisu.gdx.BasisuTextureLoader;
import com.ray3k.stripe.FreeTypeSkinLoader;

import de.pottgames.tuningfork.SoundBuffer;
import de.pottgames.tuningfork.SoundBufferLoader;
import jelte.mygame.graphical.animations.NamedSprite;
import jelte.mygame.utility.logging.MultiFileLogger;

public class AssetManagerUtility implements Disposable {
	private static final String TAG = AssetManagerUtility.class.getSimpleName();
	private static final MultiFileLogger logger = (MultiFileLogger) Gdx.app.getApplicationLogger();

	private static boolean loadersSet = false;

	public static final AssetManager assetManager = new AssetManager();
	private static InternalFileHandleResolver filePathResolver = new InternalFileHandleResolver();

	private static TmxMapLoader tmxMapLoader = new TmxMapLoader(filePathResolver);
	private static TextureLoader textureLoader = new TextureLoader(filePathResolver);
	private static ParticleEffectLoader particleEffectLoader = new ParticleEffectLoader(filePathResolver);
	private static SoundLoader soundLoader = new SoundLoader(filePathResolver);
	private static MusicLoader musicLoader = new MusicLoader(filePathResolver);
	private static TextureAtlasLoader textureAtlasLoader = new TextureAtlasLoader(filePathResolver);
	private static BasisUniversalTextureAtlasLoader basisUniversalTextureAtlasLoader = new BasisUniversalTextureAtlasLoader(filePathResolver);
	private static SoundBufferLoader soundBufferLoader = new SoundBufferLoader(new InternalFileHandleResolver());
	private static Set<String> spriteNames = new HashSet<>();;

	private static Map<String, Array<NamedSprite>> cachedAnimations = new HashMap<>();

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

	public static void loadSoundBufferAsset(final String soundFilenamePath) {
		loadAsset(soundFilenamePath, SoundBuffer.class);
	}

	public static SoundBuffer getSoundBufferAsset(final String soundFilenamePath) {
		return (SoundBuffer) getAsset(soundFilenamePath, SoundBuffer.class);
	}

	public static void loadMusicAsset(final String musicFilenamePath) {
		loadAsset(musicFilenamePath, Music.class);
	}

	public static Music getMusicAsset(final String musicFilenamePath) {
		return (Music) getAsset(musicFilenamePath, Music.class);
	}

	public static void loadTextureAtlas(final String textureAtlasFilenamePath) {
		loadAsset(textureAtlasFilenamePath, TextureAtlas.class);
		final TextureAtlas atlas = getTextureAtlas(textureAtlasFilenamePath);
		if (atlas != null) {
			for (TextureAtlas.AtlasRegion region : atlas.getRegions()) {
				spriteNames.add(region.name);
			}
		} else {
			logger.error(TAG, String.format("can't preload spritenames, texture atlas is null: %s", textureAtlasFilenamePath), null);
		}
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
				assetManager.finishLoadingAsset(assetName); // block
				logger.error(TAG, String.format("%s loaded: %s", className.getSimpleName(), assetName), null);
			} else {
				logger.error(TAG, String.format("%s doesn't exist: %s", className.getSimpleName(), assetName), null);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getAsset(final String filenamePath, final Class className) {
		Object object = null;

		if (assetManager.isLoaded(filenamePath)) {
			object = assetManager.get(filenamePath, className);
		} else {
			logger.error(TAG, String.format("%s is not loaded: %s", className.getSimpleName(), filenamePath), null);
		}

		return object;
	}

	public static void unloadAsset(final String assetFilenamePath) {
		if (assetManager.isLoaded(assetFilenamePath)) {
			assetManager.unload(assetFilenamePath);
		} else {
			logger.error(TAG, String.format("Asset is not loaded; Nothing to unload: %s", assetFilenamePath), null);
		}
	}

	public static Sprite getSprite(String spriteName) {
		final TextureAtlas atlas = getTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		if (atlas != null) {
			return atlas.createSprite(spriteName);
		}
		logger.error(TAG, String.format("can't create sprite, texture atlas is null: %s", Constants.SPRITES_ATLAS_PATH), null);
		return null;
	}

	public static AtlasRegion getAtlasRegion(String regionName) {
		final TextureAtlas atlas = getTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		if (atlas != null) {
			return atlas.findRegion(regionName);
		}
		logger.error(TAG, String.format("can't create sprite, texture atlas is null: %s", Constants.SPRITES_ATLAS_PATH), null);
		return null;
	}

	public static Array<TextureAtlas.AtlasRegion> getAllRegionsWhichContainName(String spriteName) {
		final TextureAtlas atlas = getTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		if (atlas == null) {
			logger.error(TAG, String.format("can't get animation, texture atlas is null: %s", Constants.SPRITES_ATLAS_PATH), null);
			return null;
		}
		final Array<AtlasRegion> allRegions = atlas.getRegions();
		final Array<AtlasRegion> matched = new Array<>(AtlasRegion.class);

		for (int i = 0, n = allRegions.size; i < n; i++) {
			final AtlasRegion region = allRegions.get(i);
			if (region.name.contains(spriteName)) {
				matched.add(new AtlasRegion(region));
			}
		}
		return matched;
	}

	public static boolean animationExists(String animationName) {
		return spriteNames.contains(animationName);
	}

	// TODO refactor this, the 2 methods are almost identical
	public static Animation<NamedSprite> getAnimation(String animationName, float frameDuration, PlayMode playMode) {
		final TextureAtlas atlas = getTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		if (atlas == null) {
			logger.error(TAG, String.format("can't get animation, texture atlas is null: %s", Constants.SPRITES_ATLAS_PATH), null);
			return null;
		}

		Array<NamedSprite> cachedAnimation = cachedAnimations.get(animationName);

		if (cachedAnimation == null) {
			Sprite[] sprites = atlas.createSprites(animationName).toArray();
			NamedSprite[] namedSprites = Arrays.stream(sprites)
					.map(sprite -> new NamedSprite(sprite, animationName))
					.toArray(NamedSprite[]::new);
			cachedAnimation = new Array<>(namedSprites);
		}

		if (cachedAnimation.size == 0) {
			logger.error(TAG, String.format("can't get animation, no region found in atlas: %s", Constants.SPRITES_ATLAS_PATH), null);
			return null;
		}

		return new Animation<>(frameDuration, cachedAnimation, playMode);
	}

	public static Animation<NamedSprite> getBackgroundAnimation(String animationName, float animationSpeed, PlayMode playMode) {
		final TextureAtlas atlas = getTextureAtlas(Constants.SPRITES_BACKGROUND_ATLAS_PATH);
		if (atlas == null) {
			logger.error(TAG, String.format("can't get animation, texture atlas is null: %s", Constants.SPRITES_ATLAS_PATH), null);
			return null;
		}

		Array<NamedSprite> cachedAnimation = cachedAnimations.get(animationName);

		if (cachedAnimation == null) {
			Sprite[] sprites = atlas.createSprites(animationName).toArray();
			NamedSprite[] namedSprites = Arrays.stream(sprites)
					.map(sprite -> new NamedSprite(sprite, animationName))
					.toArray(NamedSprite[]::new);
			cachedAnimation = new Array<>(namedSprites);
		}

		if (cachedAnimation.size == 0) {
			logger.error(TAG, String.format("can't get animation, no region found in atlas: %s", Constants.SPRITES_ATLAS_PATH), null);
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
		return string != null && !string.isEmpty();
	}

	private AssetManagerUtility() {

	}

	private static void setLoaders() {
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(filePathResolver));
		assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(filePathResolver));
		assetManager.setLoader(TiledMap.class, tmxMapLoader);
		assetManager.setLoader(TextureAtlas.class, ".basis", basisUniversalTextureAtlasLoader);
		assetManager.setLoader(Texture.class, ".basis", new BasisuTextureLoader(assetManager.getFileHandleResolver()));
		assetManager.setLoader(Texture.class, textureLoader);
		assetManager.setLoader(ParticleEffect.class, particleEffectLoader);
		assetManager.setLoader(Sound.class, soundLoader);
		assetManager.setLoader(Music.class, musicLoader);
		assetManager.setLoader(TextureAtlas.class, textureAtlasLoader);
		assetManager.setLoader(SoundBuffer.class, soundBufferLoader);
		assetManager.setLoader(Skin.class, new FreeTypeSkinLoader(assetManager.getFileHandleResolver()));
		loadersSet = true;
	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}

}
