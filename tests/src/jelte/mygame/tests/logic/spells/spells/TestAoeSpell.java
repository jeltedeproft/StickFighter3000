package jelte.mygame.tests.logic.spells.spells;

import static org.junit.Assert.assertEquals;

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
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.logic.spells.spells.AoeSpell;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestAoeSpell {

	private Character caster;
	private Character target;
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
		target = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), characterId);
		target.setCharacterStateManager(new CharacterStateManager(target));
		target.setPhysicsComponent(new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(100, 100)));
	}

	@Test
	public void testApplyEffect() {
		// Create an instance of AoeSpell
		AoeSpell aoeSpell = new AoeSpell(SpellFileReader.getSpellData().get(0), caster, new Vector2(), true);

		// Apply the effect of the AoeSpell on the target character
		aoeSpell.applyEffect(target);

		// Assert that the target character has been damaged
		assertEquals(0.0f, target.getCurrentHp(), 0.001f); // Assuming initial health is 1.0f
	}

	@Test
	public void testUpdateSpell() {
		// Create an instance of AoeSpell
		AoeSpell aoeSpell = new AoeSpell(SpellFileReader.getSpellData().get(1), caster, new Vector2(), true);

		// Update the AoeSpell with a delta time and mouse position
		Vector2 updatedMousePosition = new Vector2(10.0f, 10.0f);
		aoeSpell.update(0.5f, caster, updatedMousePosition);

		// Assert that the physics component position has been updated
		assertEquals(target.getPhysicsComponent().getPosition(), aoeSpell.getPhysicsComponent().getPosition());
	}

	// Add more test cases as needed

}
