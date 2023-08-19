package jelte.mygame.tests.logic.character;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.logic.spells.SpellsEnum;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacter {

	private PlayerCharacter character;
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
	}

	@Test
	public void testInitialization() {
		assertNotNull(character);
		assertNotNull(character.getSpellsPreparedToCast());
		assertNotNull(character.getSpellsreadyToCast());
		assertEquals(characterId, character.getId());
		assertEquals(100f, character.getCurrentHp(), 0.001f);
		assertFalse(character.isDead());
		assertNotNull(character.getData());
		assertNotNull(character.getCurrentCharacterState());
		assertNotNull(character.getPhysicsComponent().toString());
	}

	@Test
	public void testUpdate() {
		// Test the update method
		float delta = 0.5f;
		character.update(delta);
		// Add assertions as needed
	}

	@Test
	public void testDamage() {
		float initialHp = 100f;
		float damageAmount = 20f;

		character.setCurrentHp(initialHp);
		boolean isCharacterDead = character.damage(damageAmount);

		assertEquals(initialHp - damageAmount, character.getCurrentHp(), 0.001f);
		assertFalse(isCharacterDead);
		// Add more assertions as needed
	}

	@Test
	public void testHeal() {
		float initialHp = 20f;
		float healAmount = 30f;

		character.setCurrentHp(initialHp);
		character.heal(healAmount);

		assertEquals(initialHp + healAmount, character.getCurrentHp(), 0.001f);

		character.heal(10000f);

		assertEquals(character.getData().getMaxHP(), character.getCurrentHp(), 0.001f);
	}

	@Test
	public void testGetters() {
		// Test the getter methods
		// Add assertions as needed
	}

	@Test
	public void testReceiveMessageSpellNotUnlocked() {
		// Test the receiveMessage() method
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.SPELL0, true);
		Message message = new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP, inputBox);
		character.receiveMessage(message);
		// Add assertions as needed
		assertEquals(0, character.getSpellsPreparedToCast().size);
	}

	@Test
	public void testReceiveMessageSpellUnlocked() {
		// Test the receiveMessage() method
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.SPELL0, true);
		Message message = new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP, inputBox);
		character.unlockSpell(SpellsEnum.values()[2]);
		character.receiveMessage(message);

		// Add assertions as needed
		assertEquals(1, character.getSpellsPreparedToCast().size);
		assertEquals(2, character.getSpellsPreparedToCast().get(0).getId());
	}

}
