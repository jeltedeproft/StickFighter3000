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
import jelte.mygame.logic.spells.spells.DefaultSpell;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestDefaultSpell {

	private Character caster;
	private Character target;
	private UUID casterId = UUID.randomUUID();
	private UUID targetId = UUID.randomUUID();
	private Vector2 casterPosition;
	private Vector2 targetPosition;
	private Vector2 mousePosition;

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

		caster = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), casterId);
		caster.setCharacterStateManager(new CharacterStateManager(caster));
		caster.setPhysicsComponent(new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0)));
		casterPosition = caster.getPhysicsComponent().getPosition();

		target = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), targetId);
		target.setCharacterStateManager(new CharacterStateManager(target));
		target.setPhysicsComponent(new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(100, 100)));
		targetPosition = target.getPhysicsComponent().getPosition();

		mousePosition = new Vector2(50, 50);
	}

	@Test
	public void testCastDefaultSpell() {
		DefaultSpell defaultSpell = new DefaultSpell(SpellFileReader.getSpellData().get(2), caster, mousePosition);

		defaultSpell.getData().setDamage(100.0f);
		defaultSpell.applyCollisionEffect(target);

		assertEquals(0.0f, target.getCurrentHp(), 0.001f);
	}

	@Test
	public void testCastDefaultCasterEqualsTarget() {
		DefaultSpell defaultSpell = new DefaultSpell(SpellFileReader.getSpellData().get(2), caster, mousePosition);

		defaultSpell.getData().setDamage(100.0f);
		target.setId(casterId);
		defaultSpell.applyCollisionEffect(target);

		assertEquals(100.0f, target.getCurrentHp(), 0.001f);
	}

	@Test
	public void testUpdateDefaultSpell() {
		DefaultSpell defaultSpell = new DefaultSpell(SpellFileReader.getSpellData().get(2), caster, mousePosition);

		Vector2 updatedMousePosition = new Vector2(10.0f, 10.0f);
		defaultSpell.update(0.5f, caster, updatedMousePosition);

		assertEquals(88.388f, defaultSpell.getPhysicsComponent().getPosition().x, 0.01f);
		assertEquals(88.388f, defaultSpell.getPhysicsComponent().getPosition().y, 0.01f);
	}

}
