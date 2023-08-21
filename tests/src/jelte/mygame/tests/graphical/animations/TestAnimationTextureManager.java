package jelte.mygame.tests.graphical.animations;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jelte.mygame.graphical.animations.AnimationTextureManager;
import jelte.mygame.graphical.animations.NamedSprite;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.logic.character.EnemyFileReader;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.logic.spells.spells.AbstractSpell;
import jelte.mygame.logic.spells.spells.AoeSpell;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestAnimationTextureManager {
	private AnimationTextureManager animationTextureManager;

	@Mock
	private MusicManagerInterface mockMusicManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PlayerFileReader.loadUnitStatsInMemory(Constants.PLAYER_STATS_TEST_FILE_LOCATION);
		EnemyFileReader.loadUnitStatsInMemory(Constants.ENEMY_STATS_TEST_FILE_LOCATION);
		SpellFileReader.loadSpellsInMemory(Constants.SPELL_STATS_TEST_FILE_LOCATION);
		ApplicationLogger logger = new MultiFileLogger();
		Gdx.app.setApplicationLogger(logger);
		AssetManagerUtility.loadTextureAtlas(Constants.SPRITES_ATLAS_PATH);
	}

	@Before
	public void prepareTest() {
		animationTextureManager = new AnimationTextureManager();
		MockitoAnnotations.openMocks(this);
		MusicManager.setInstance(mockMusicManager);
	}

	@Test
	public void testGetAnimationNoCache() {
		Animation<NamedSprite> animation = animationTextureManager.getEffectAnimation("swordmaster-APPEARING1-right", 1.0f, PlayMode.NORMAL);
		assertEquals(6.0f, animation.getAnimationDuration(), 0.01f);
		assertEquals(1.0f, animation.getFrameDuration(), 0.01f);
		assertEquals(PlayMode.NORMAL, animation.getPlayMode());
		assertEquals("swordmaster-APPEARING1-right", animation.getKeyFrame(0).getName());
	}

	@Test
	public void testGetSpriteStringCharacter() {
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());
		NamedSprite sprite = animationTextureManager.getSprite("swordmaster-APPEARING1-right", player);
		assertEquals("swordmaster-APPEARING1-right", sprite.getName());
	}

	@Test
	public void testGetSpriteStringAbstractSpell() {
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());
		AbstractSpell spell = new AoeSpell(SpellFileReader.getSpellData().get(0), player, new Vector2(100, 100), true);
		NamedSprite sprite = animationTextureManager.getSprite("attack-WINDUP", spell);
		assertEquals("attack-WINDUP", sprite.getName());
	}

}
