package jelte.mygame.tests.logic.character.state;

import static org.mockito.Mockito.mock;
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
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateRolling;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateRolling {

	private CharacterStateManager characterStateManager;
	private CharacterStateRolling characterState;

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
		characterState = new CharacterStateRolling(characterStateManager, duration);
	}

	@Test
	public void testEntry() {
		characterState.entry();
		// Assert any expected behavior during the entry phase
	}

	@Test
	public void testUpdate() {
		float delta = 0.01f;

		characterState.update(delta);

	}

	@Test
	public void testUpdateRunning() {
		when(characterStateManager.characterisRunning()).thenReturn(true);
		float delta = 500f;

		characterState.update(delta);

		verify(characterStateManager).transition(CHARACTER_STATE.RUNNING);
	}

	@Test
	public void testUpdateNotRunning() {
		when(characterStateManager.characterisRunning()).thenReturn(false);
		float delta = 500f;

		characterState.update(delta);

		verify(characterStateManager).transition(CHARACTER_STATE.IDLE);
	}

	@Test
	public void testHandleEventAttackPressed() {
		EVENT event = EVENT.ATTACK_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.ROLLATTACK);
	}

	@Test
	public void testHandleEventLeftUnPressed() {
		EVENT event = EVENT.LEFT_UNPRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).stopCharacter();
	}

	@Test
	public void testHandleEventRightUnPressed() {
		EVENT event = EVENT.RIGHT_UNPRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).stopCharacter();
	}

	@Test
	public void testExit() {
		characterState.exit();

	}

}
