package jelte.mygame.tests.logic.spells.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import jelte.mygame.logic.spells.factories.BuffSpellFactory;
import jelte.mygame.logic.spells.spells.AbstractSpell;
import jelte.mygame.logic.spells.spells.Spell.SPELL_TYPE;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestBuffSpellFactory {

	private SpellManager spellManager;
	private Vector2 mousePosition;

	private Character caster;
	private Character target;
	private UUID casterId = UUID.randomUUID();
	private UUID targetId = UUID.randomUUID();
	private Vector2 casterPosition;
	private Vector2 targetPosition;

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

		spellManager = new SpellManager();
		mousePosition = new Vector2(10, 20);

		caster = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), casterId);
		caster.setCharacterStateManager(new CharacterStateManager(caster));
		caster.setPhysicsComponent(new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0)));
		casterPosition = caster.getPhysicsComponent().getPosition();

		target = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), targetId);
		target.setCharacterStateManager(new CharacterStateManager(target));
		target.setPhysicsComponent(new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(100, 100)));
		targetPosition = target.getPhysicsComponent().getPosition();

	}

	@Test
	public void testCreateBuffSpell() {
		SpellData spellData = mock(SpellData.class);
		when(spellData.getType()).thenReturn(SPELL_TYPE.BUFF.name());
		when(spellData.getName()).thenReturn("bufftest");

		BuffSpellFactory spellFactory = new BuffSpellFactory();
		AbstractSpell spell = spellFactory.createSpell(spellData, caster, target, mousePosition);

		assertNotNull(spell);
		assertEquals(spell.getCasterId(), casterId);
		assertEquals("bufftest", spell.getName());
		assertNotNull(spell.getSpellStateManager());
	}

	@Test
	public void testCreateSpellWithDefault() {
		SpellData spellData = mock(SpellData.class);

		BuffSpellFactory spellFactory = new BuffSpellFactory();
		AbstractSpell spell = spellFactory.createSpell(spellData, caster, target, mousePosition);

		assertNotNull(spell);
		assertEquals(spell.getCasterId(), casterId);
		assertNull(spell.getName());
		assertNotNull(spell.getSpellStateManager());
	}
}
