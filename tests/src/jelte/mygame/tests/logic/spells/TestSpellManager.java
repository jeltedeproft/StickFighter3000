package jelte.mygame.tests.logic.spells;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashSet;
import java.util.Set;
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
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.logic.physics.SpellPhysicsComponent;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.logic.spells.SpellManager;
import jelte.mygame.logic.spells.SpellsEnum;
import jelte.mygame.logic.spells.spells.AbstractSpell;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestSpellManager {

	private SpellManager spellManager;
	private Character character;
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
		character = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), characterId);
		character.setCharacterStateManager(new CharacterStateManager(character));
		character.setPhysicsComponent(new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0)));
		spellManager = new SpellManager();
		mousePosition = new Vector2(10, 20);
	}

	@Test
	public void testCreateSpell() {
		SpellData spellData = SpellFileReader.getSpellData().get(0);
		spellManager.createSpell(spellData, character);

		Array<AbstractSpell> allSpells = spellManager.getAllSpells();
		assertEquals(1, allSpells.size);
		assertEquals("attack", allSpells.get(0).getName());
	}

	@Test
	public void testUpdate() {
		SpellData spellData = SpellFileReader.getSpellData().get(0);
		spellManager.createSpell(spellData, character);

		spellManager.update(0.1f, mousePosition, new Array<>());

		Array<AbstractSpell> allSpells = spellManager.getAllSpells();
		assertEquals(1, allSpells.size);
		assertEquals(0.1f, allSpells.get(0).getTimeAlive(), 0.001f);
	}

	@Test
	public void testGetAllSpellBodies() {
		SpellData spellData = SpellFileReader.getSpellData().get(0);

		spellManager.createSpell(spellData, character);
		UUID spellId = spellManager.getAllSpells().get(0).getId();

		Set<Collidable> expectedBodies = new HashSet<>();
		expectedBodies.add(new SpellPhysicsComponent(spellId, SpellsEnum.ATTACK, character.getPhysicsComponent().getPosition()));

		Set<Collidable> actualBodies = spellManager.getAllSpellBodies();

		assertEquals(expectedBodies, actualBodies);
	}

}
