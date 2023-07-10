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
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateJumpToFall;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateJumpToFall {

	private CharacterStateManager characterStateManager;
	private CharacterStateJumpToFall characterState;

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
		characterState = new CharacterStateJumpToFall(characterStateManager);
	}

	@Test
	public void testEntry() {
		characterState.entry();
	}

	@Test
	public void testUpdate() {
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).characterisFalling();
	}

	@Test
	public void testUpdateLanded() {
		when(characterStateManager.characterisFalling()).thenReturn(true);
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).transition(CHARACTER_STATE.FALLING);
	}

	@Test
	public void testHandleEventJumpPressed() {
		EVENT event = EVENT.JUMP_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.JUMPING);
	}

	@Test
	public void testHandleEventCastPressed() {
		EVENT event = EVENT.CAST_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.PRECAST);
	}

	@Test
	public void testHandleEventLeftPressed() {
		EVENT event = EVENT.LEFT_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).accelerateCharacterX(Direction.left, Constants.MOVEMENT_SPEED);
	}

	@Test
	public void testHandleEventRightPressed() {
		EVENT event = EVENT.RIGHT_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).accelerateCharacterX(Direction.right, Constants.MOVEMENT_SPEED);
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
