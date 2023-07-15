package jelte.mygame.tests.logic.spells.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

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

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.logic.spells.SpellManager;
import jelte.mygame.logic.spells.SpellsEnum;
import jelte.mygame.logic.spells.factories.AoeSpellFactory;
import jelte.mygame.logic.spells.spells.AbstractSpell;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestAoeSpellFactory {

	private SpellManager spellManager;
	private Character caster;
	private Vector2 mousePosition;
	private UUID characterId = UUID.randomUUID();

	@Mock
	private MusicManagerInterface mockMusicManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PlayerFileReader.loadUnitStatsInMemory(Constants.PLAYER_STATS_TEST_FILE_LOCATION);
		SpellFileReader.loadSpellsInMemory(Constants.SPELL_STATS_TEST_FILE_LOCATION);
		ApplicationLogger logger = new MultiFileLogger();
		Gdx.app.setApplicationLogger(logger);
	}

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		MusicManager.setInstance(mockMusicManager);
		caster = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), characterId);
		caster.setCharacterStateManager(new CharacterStateManager(caster));
		caster.setPhysicsComponent(new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0)));
		spellManager = new SpellManager();
		mousePosition = new Vector2(10, 20);
	}

	@Test
	public void testCreateSpellWithAttack() {
		SpellData spellData = SpellFileReader.getSpellData().get(SpellsEnum.ATTACK.ordinal());

		AoeSpellFactory spellFactory = new AoeSpellFactory();
		AbstractSpell spell = spellFactory.createSpell(spellData, caster, null, mousePosition);

		assertNotNull(spell);
		assertEquals(spell.getCasterId(), characterId);
		assertEquals("attack", spell.getName());
		assertNotNull(spell.getSpellStateManager());
	}

	@Test
	public void testCreateSpellWithFallAttack() {
		SpellData spellData = SpellFileReader.getSpellData().get(SpellsEnum.FALLATTACK.ordinal());

		AoeSpellFactory spellFactory = new AoeSpellFactory();
		AbstractSpell spell = spellFactory.createSpell(spellData, caster, null, mousePosition);

		assertNotNull(spell);
		assertEquals(spell.getCasterId(), characterId);
		assertEquals("fallattack", spell.getName());
		assertNotNull(spell.getSpellStateManager());
	}

	@Test
	public void testCreateSpellWithDefault() {
		SpellData spellData = mock(SpellData.class);

		AoeSpellFactory spellFactory = new AoeSpellFactory();
		AbstractSpell spell = spellFactory.createSpell(spellData, caster, null, mousePosition);

		assertNotNull(spell);
		assertEquals(spell.getCasterId(), characterId);
		assertNull(spell.getName());
		assertNotNull(spell.getSpellStateManager());
	}
}
