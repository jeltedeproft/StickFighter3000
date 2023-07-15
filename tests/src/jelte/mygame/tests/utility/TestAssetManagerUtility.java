package jelte.mygame.tests.utility;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestAssetManagerUtility {

	@Mock
	private MusicManagerInterface mockMusicManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PlayerFileReader.loadUnitStatsInMemory(Constants.PLAYER_STATS_TEST_FILE_LOCATION);
		SpellFileReader.loadSpellsInMemory(Constants.SPELL_STATS_TEST_FILE_LOCATION);
		ApplicationLogger logger = new MultiFileLogger();
		Gdx.app.setApplicationLogger(logger);
		Gdx.files = new HeadlessFiles();
	}

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		MusicManager.setInstance(mockMusicManager);
	}

	@Test
	public void testLoadAndRetrieveTextureAsset() {
		String textureFilenamePath = "swordmaster-APPEARING1-left";

		// Set up necessary assets and load a texture atlas
		AssetManagerUtility.loadTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		assertTrue(AssetManagerUtility.isAssetLoaded(Constants.SPRITES_ATLAS_PATH));

		AssetManagerUtility.loadTextureAsset(textureFilenamePath);
		assertTrue(AssetManagerUtility.isAssetLoaded(textureFilenamePath));

		// Retrieve the loaded texture asset
		assertNotNull(AssetManagerUtility.getTextureAsset(textureFilenamePath));
	}

	@Test
	public void testLoadAndRetrieveSoundAsset() {
		String soundFilenamePath = "audio/boom.wav";

		AssetManagerUtility.loadSoundAsset(soundFilenamePath);
		assertTrue(AssetManagerUtility.isAssetLoaded(soundFilenamePath));

		// Retrieve the loaded sound asset
		assertNotNull(AssetManagerUtility.getSoundAsset(soundFilenamePath));
	}

	@Test
	public void testUnloadAsset() {
		String textureFilenamePath = "swordmaster-APPEARING1-left";

		// Set up necessary assets and load a texture atlas
		AssetManagerUtility.loadTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		assertTrue(AssetManagerUtility.isAssetLoaded(Constants.SPRITES_ATLAS_PATH));

		AssetManagerUtility.loadTextureAsset(textureFilenamePath);
		assertTrue(AssetManagerUtility.isAssetLoaded(textureFilenamePath));

		// Unload the asset
		AssetManagerUtility.unloadAsset(textureFilenamePath);
		assertFalse(AssetManagerUtility.isAssetLoaded(textureFilenamePath));
	}

	@Test
	public void testGetSprite() {
		String spriteName = "swordmaster-APPEARING1-left";

		// Set up necessary assets and load a texture atlas
		AssetManagerUtility.loadTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		assertTrue(AssetManagerUtility.isAssetLoaded(Constants.SPRITES_ATLAS_PATH));

		// Retrieve a sprite using the sprite name
		assertNotNull(AssetManagerUtility.getSprite(spriteName));
	}

	@Test
	public void testGetAnimation() {
		String animationName = "swordmaster-APPEARING1-left";
		float frameDuration = 0.1f;

		// Set up necessary assets and load a texture atlas
		AssetManagerUtility.loadTextureAtlas(Constants.SPRITES_ATLAS_PATH);
		assertTrue(AssetManagerUtility.isAssetLoaded(Constants.SPRITES_ATLAS_PATH));

		// Retrieve an animation using the animation name and frame duration
		assertNotNull(AssetManagerUtility.getAnimation(animationName, frameDuration, PlayMode.NORMAL));
	}

	// Add more tests as needed

}
