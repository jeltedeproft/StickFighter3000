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
import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateSprinting;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateSprinting {

	private CharacterStateManager characterStateManager;
	private CharacterStateSprinting characterState;

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
		characterState = new CharacterStateSprinting(characterStateManager);
	}

	@Test
	public void testEntry() {
		characterState.entry();
		// Assert any expected behavior during the entry phase
	}

	@Test
	public void testUpdate() {
		float delta = 500f;
		characterState.update(delta);
	}

	@Test
	public void testHandleEventAttackPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.ATTACK, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).pushState(CHARACTER_STATE.ATTACKING);
	}

	@Test
	public void testHandleEventDamageTaken() {
		EVENT event = EVENT.DAMAGE_TAKEN;
		characterState.handleEvent(event);

		verify(characterStateManager).pushState(CHARACTER_STATE.HURT);
	}

	@Test
	public void testHandleEventJumpPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.UP, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).pushState(CHARACTER_STATE.JUMPING);
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
	public void testHandleEventBlockPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.BLOCK, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).pushState(CHARACTER_STATE.BLOCKING);
	}

	@Test
	public void testHandleEventCastPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.SPELL0, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).pushState(CHARACTER_STATE.PRECAST);
	}

	@Test
	public void testHandleInputLeftPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.LEFT, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).startMovingOnTheGround(Constants.SPRINT_SPEED);
		verify(characterStateManager).pushState(CHARACTER_STATE.WALKING);
	}

	@Test
	public void testHandleInputRightPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.RIGHT, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).startMovingOnTheGround(Constants.SPRINT_SPEED);
		verify(characterStateManager).pushState(CHARACTER_STATE.WALKING);
	}

	@Test
	public void testHandleInputLeftUnPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.LEFT, false);
		characterState.handleInput(inputBox);

		verify(characterStateManager).stopMovingOnTheGround();
	}

	@Test
	public void testHandleInputRightUnPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.RIGHT, false);
		characterState.handleInput(inputBox);

		verify(characterStateManager).stopMovingOnTheGround();
	}

	@Test
	public void testExit() {
		characterState.exit();

	}

}
