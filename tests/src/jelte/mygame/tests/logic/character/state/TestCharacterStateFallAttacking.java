package jelte.mygame.tests.logic.character.state;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.character.state.CharacterStateFallAttacking;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateFallAttacking {

	private CharacterStateManager characterStateManager;
	private CharacterStateFallAttacking characterState;

	@Mock
	private MusicManagerInterface mockMusicManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationLogger logger = new MultiFileLogger();
		Gdx.app.setApplicationLogger(logger);
	}

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		MusicManager.setInstance(mockMusicManager);
		characterStateManager = mock(CharacterStateManager.class);
		characterState = new CharacterStateFallAttacking(characterStateManager);
	}

	@Test
	public void testEntry() {
		characterState.entry();
		verify(characterStateManager).pullDown(Constants.FALL_ATTACK_SPEED_BOOST);
		verify(characterStateManager).makeSpellReady(SpellFileReader.getSpellData().get(1));
	}

	@Test
	public void testUpdate() {
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).characterHaslanded();
	}

	@Test
	public void testUpdateLanded() {
		when(characterStateManager.characterHaslanded()).thenReturn(true);
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).pushState(CHARACTER_STATE.LANDATTACKING);
	}

	@Test
	public void testHandleEventJumpPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.UP, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager, never());
	}

	@Test
	public void testHandleEventTeleportPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.TELEPORT, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager, never());
	}

	@Test
	public void testHandleEventDashPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.DASH, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager, never());
	}

	@Test
	public void testHandleEventCastPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.SPELL0, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager, never());
	}

	@Test
	public void testHandleInputLeftPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.LEFT, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager, never());
	}

	@Test
	public void testHandleInputRightPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.RIGHT, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager, never());
	}

	@Test
	public void testHandleInputLeftUnPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.LEFT, false);
		characterState.handleInput(inputBox);

		verify(characterStateManager, never());
	}

	@Test
	public void testHandleInputRightUnPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.RIGHT, false);
		characterState.handleInput(inputBox);

		verify(characterStateManager, never());
	}

	@Test
	public void testExit() {
		characterState.exit();

	}

}
