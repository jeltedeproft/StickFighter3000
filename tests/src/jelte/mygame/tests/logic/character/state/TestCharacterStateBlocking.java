package jelte.mygame.tests.logic.character.state;

import static org.mockito.Mockito.mock;
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
import jelte.mygame.logic.character.state.CharacterStateBlocking;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
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
	public void testEntry() {
		characterState.entry();
		verify(characterStateManager).stopCharacter();
	}

	@Test
	public void testUpdate() {
		float delta = 500f;

		characterState.update(delta);

		verify(characterStateManager).transition(CHARACTER_STATE.IDLE);
	}

	@Test
	public void testHandleEventTeleportPressed() {
		EVENT event = EVENT.TELEPORT_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.TELEPORTING);
	}

	@Test
	public void testHandleEventDashPressed() {
		EVENT event = EVENT.DASH_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.DASHING);
	}

	@Test
	public void testHandleEventRollPressed() {
		EVENT event = EVENT.ROLL_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.ROLLING);
	}

	@Test
	public void testHandleEventCastPressed() {
		EVENT event = EVENT.CAST_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.PRECAST);
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
