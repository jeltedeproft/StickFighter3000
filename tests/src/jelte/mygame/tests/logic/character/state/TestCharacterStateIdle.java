package jelte.mygame.tests.logic.character.state;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;

import java.util.Stack;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateIdle;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateIdle {

	private CharacterStateManager characterStateManager;
	private CharacterStateIdle characterState;

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
		characterState = new CharacterStateIdle(characterStateManager);
	}

	@Test
	public void testEntry() {
		Stack<EVENT> buffer = new Stack<>();
		buffer.add(EVENT.LEFT_PRESSED);
		when(characterStateManager.getPressedKeysBuffer()).thenReturn(buffer);
		characterState.entry();
		verify(characterStateManager).transition(CHARACTER_STATE.WALKING);
	}

	@Test
	public void testUpdate() {
		float delta = 500f;

		characterState.update(delta);
	}

	@Test
	public void testHandleEventAttackPressed() {
		EVENT event = EVENT.ATTACK_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.ATTACKING);
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
	public void testHandleEventBlockPressed() {
		EVENT event = EVENT.BLOCK_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.BLOCKING);
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
		verify(characterStateManager).transition(CHARACTER_STATE.WALKING);
	}

	@Test
	public void testHandleEventRightPressed() {
		EVENT event = EVENT.RIGHT_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).accelerateCharacterX(Direction.right, Constants.MOVEMENT_SPEED);
		verify(characterStateManager).transition(CHARACTER_STATE.WALKING);
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
	public void testHandleEventNoCollision() {
		EVENT event = EVENT.NO_COLLISION;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.FALLING);
	}

	@Test
	public void testExit() {
		characterState.exit();

	}

}
