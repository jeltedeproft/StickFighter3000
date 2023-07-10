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
import jelte.mygame.logic.character.state.CharacterStateHurt;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateHurt {

	private CharacterStateManager characterStateManager;
	private CharacterStateHurt characterState;

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
		characterState = new CharacterStateHurt(characterStateManager, duration);
	}

	@Test
	public void testEntry() {
		characterState.entry();
		verify(characterStateManager).hangCharacterInTheAirAgainstGravity();
	}

	@Test
	public void testUpdate() {
		float delta = 500f;

		characterState.update(delta);

		verify(characterStateManager).transition(CHARACTER_STATE.IDLE);
	}

	@Test
	public void testHandleEventDamageTaken() {
		EVENT event = EVENT.DAMAGE_TAKEN;
		characterState.handleEvent(event);
	}

	@Test
	public void testHandleEventJumpPressed() {
		EVENT event = EVENT.DIED;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.DIE);
	}

	@Test
	public void testExit() {
		characterState.exit();

	}

}
