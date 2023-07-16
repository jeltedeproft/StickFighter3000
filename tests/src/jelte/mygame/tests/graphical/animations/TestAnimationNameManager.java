package jelte.mygame.tests.graphical.animations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jelte.mygame.graphical.animations.AnimationName;
import jelte.mygame.graphical.animations.AnimationNameManager;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.logic.character.EnemyFileReader;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.logic.spells.spells.AbstractSpell;
import jelte.mygame.logic.spells.spells.AoeSpell;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestAnimationNameManager {
	private AnimationNameManager animationNameManager;

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
		animationNameManager = new AnimationNameManager();
		MockitoAnnotations.openMocks(this);
		MusicManager.setInstance(mockMusicManager);
	}

	@Test
	public void testanimationsExist() {
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.APPEARING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.ATTACKING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.PRECAST));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.BLOCKING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.CROUCHED));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.DASHING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.DIE));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.FALLATTACKING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.FALLING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.GRABBING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.HOLDING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.HOLDINGTOSLIDING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.HURT));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.IDLE));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.JUMPING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.JUMPTOFALL));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.LANDING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.ROLLATTACK));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.ROLLING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.RUNNING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.SPRINTING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.WALKING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.WALLSLIDING));
		assertTrue(animationNameManager.animationsExist("swordmaster", CHARACTER_STATE.WALLSLIDINGSTOP));
		assertTrue(animationNameManager.animationsExist("archer", CHARACTER_STATE.ATTACKING));
		assertTrue(animationNameManager.animationsExist("archer", CHARACTER_STATE.DIE));
		assertTrue(animationNameManager.animationsExist("archer", CHARACTER_STATE.HURT));
		assertTrue(animationNameManager.animationsExist("archer", CHARACTER_STATE.RUNNING));
		assertTrue(animationNameManager.animationsExist("archer", CHARACTER_STATE.IDLE));
		assertTrue(animationNameManager.animationsExist("orby", CHARACTER_STATE.ATTACKING));
		assertTrue(animationNameManager.animationsExist("orby", CHARACTER_STATE.CAST));
		assertTrue(animationNameManager.animationsExist("orby", CHARACTER_STATE.DIE));
		assertTrue(animationNameManager.animationsExist("orby", CHARACTER_STATE.HURT));
		assertTrue(animationNameManager.animationsExist("orby", CHARACTER_STATE.IDLE));
		assertTrue(animationNameManager.animationsExist("orby", CHARACTER_STATE.RUNNING));
		assertTrue(animationNameManager.animationsExist("alchemist", CHARACTER_STATE.CLIMBING));
		assertTrue(animationNameManager.animationsExist("alchemist", CHARACTER_STATE.CROUCHED));
		assertTrue(animationNameManager.animationsExist("alchemist", CHARACTER_STATE.IDLECROUCH));
		assertTrue(animationNameManager.animationsExist("alchemist", CHARACTER_STATE.DASHING));
		assertTrue(animationNameManager.animationsExist("alchemist", CHARACTER_STATE.FALLING));
		assertTrue(animationNameManager.animationsExist("alchemist", CHARACTER_STATE.DIE));
		assertTrue(animationNameManager.animationsExist("alchemist", CHARACTER_STATE.HOLDING));
		assertTrue(animationNameManager.animationsExist("alchemist", CHARACTER_STATE.JUMPING));
		assertTrue(animationNameManager.animationsExist("alchemist", CHARACTER_STATE.LANDING));
		assertTrue(animationNameManager.animationsExist("alchemist", CHARACTER_STATE.RUNNING));
		assertTrue(animationNameManager.animationsExist("alchemist", CHARACTER_STATE.STOPRUNNING));
		assertTrue(animationNameManager.animationsExist("shocker", CHARACTER_STATE.ATTACKING));
		assertTrue(animationNameManager.animationsExist("shocker", CHARACTER_STATE.DIE));
		assertTrue(animationNameManager.animationsExist("shocker", CHARACTER_STATE.HURT));
		assertTrue(animationNameManager.animationsExist("shocker", CHARACTER_STATE.IDLE));
		assertTrue(animationNameManager.animationsExist("shocker", CHARACTER_STATE.RUNNING));
	}

	@Test
	public void testGetAnimationNameCharacterAppear() {
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());
		assertEquals("swordmaster-APPEARING1-right", animationNameManager.getAnimationName(player));
	}

	@Test
	public void testGetAnimationNameCharacterAttack() {
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());
		player.getCharacterStateManager().transition(CHARACTER_STATE.ATTACKING);
		assertThat(animationNameManager.getAnimationName(player), anyOf(is("swordmaster-ATTACKING1-right"), is("swordmaster-ATTACKING2-right"), is("swordmaster-ATTACKING3-right"), is("swordmaster-ATTACKING4-right")));
	}

	@Test
	public void testGetCharacterAnimation() {
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());
		AnimationName name = animationNameManager.getCharacterAnimation(player, CHARACTER_STATE.ATTACKING);
		assertThat(name.getFullName(), anyOf(is("swordmaster-ATTACKING1-right"), is("swordmaster-ATTACKING2-right"), is("swordmaster-ATTACKING3-right"), is("swordmaster-ATTACKING4-right")));
	}

	@Test
	public void testGetAnimationNameAbstractSpell() {
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());
		AbstractSpell spell = new AoeSpell(SpellFileReader.getSpellData().get(0), player, new Vector2(100, 100), true);

		assertEquals("attack-WINDUP", animationNameManager.getAnimationName(spell));
	}

	@Test
	public void testGetSpellAnimation() {
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());
		AbstractSpell spell = new AoeSpell(SpellFileReader.getSpellData().get(0), player, new Vector2(100, 100), true);
		AnimationName name = animationNameManager.getSpellAnimation(spell);
		assertEquals(name.getFullName(), "attack-WINDUP");
	}

}
