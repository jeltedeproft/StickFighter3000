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
import jelte.mygame.logic.character.state.CharacterStateHolding;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateHolding {

	private CharacterStateManager characterStateManager;
	private CharacterStateHolding characterState;

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
		characterState = new CharacterStateHolding(characterStateManager);
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
	}

	@Test
	public void testHandleEventDamageTaken() {
		EVENT event = EVENT.DAMAGE_TAKEN;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.HURT);
	}

	@Test
	public void testHandleEventJumpPressed() {
		EVENT event = EVENT.JUMP_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.JUMPING);
	}

	@Test
	public void testHandleEventDownPressed() {
		EVENT event = EVENT.DOWN_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.WALLSLIDING);
	}

	@Test
	public void testExit() {
		characterState.exit();

	}

}
