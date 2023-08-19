package jelte.mygame.tests.logic.character.state;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
import jelte.mygame.logic.character.state.CharacterStateBlocking;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateBlocking {

	private CharacterStateManager characterStateManager;
	private CharacterStateBlocking characterState;

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
		float duration = 1.5f; // Example duration
		characterState = new CharacterStateBlocking(characterStateManager, duration);
	}

	@Test
	public void testUpdate() {
		float delta = 500f;

		characterState.update(delta);

		verify(characterStateManager).popState();
	}

	@Test
	public void testHandleEventTeleportPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.TELEPORT, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).pushState(CHARACTER_STATE.TELEPORTING);
	}

	@Test
	public void testHandleEventDashPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.DASH, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).pushState(CHARACTER_STATE.DASHING);
	}

	@Test
	public void testHandleEventRollPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.ROLL, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).pushState(CHARACTER_STATE.ROLLING);
	}

	@Test
	public void testHandleEventCastPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.SPELL0, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).pushState(CHARACTER_STATE.PRECAST);
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
