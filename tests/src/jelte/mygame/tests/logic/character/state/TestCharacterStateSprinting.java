package jelte.mygame.tests.logic.character.state;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.input.CharacterInputHandler;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateSprinting;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateSprinting {

	private CharacterStateManager characterStateManager;
	private CharacterStateSprinting characterState;
	private CharacterInputHandler inputHandler;

	@Mock
	private MusicManagerInterface mockMusicManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationLogger logger = new MultiFileLogger();
		Gdx.app.setApplicationLogger(logger);
	}

	@Before
	public void setup() {
		inputHandler = new CharacterInputHandler();
		MockitoAnnotations.openMocks(this);
		MusicManager.setInstance(mockMusicManager);
		characterStateManager = mock(CharacterStateManager.class);
		characterState = new CharacterStateSprinting(characterStateManager);
		Character character = mock(Character.class);
		when(character.getPhysicsComponent()).thenReturn(new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0)));
		when(character.getName()).thenReturn("test");
		when(character.getCharacterInputHandler()).thenReturn(inputHandler);
		when(characterStateManager.getCharacter()).thenReturn(character);
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
		inputHandler.getInputBox().updateButtonPressed(BUTTONS.ATTACK, true);
		characterState.handleInput(inputHandler.getInputBox());

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
		inputHandler.getInputBox().updateButtonPressed(BUTTONS.UP, true);
		characterState.handleInput(inputHandler.getInputBox());

		verify(characterStateManager).pushState(CHARACTER_STATE.JUMPING);
	}

	@Test
	public void testHandleEventTeleportPressed() {
		inputHandler.getInputBox().updateButtonPressed(BUTTONS.TELEPORT, true);
		characterState.handleInput(inputHandler.getInputBox());

		verify(characterStateManager).pushState(CHARACTER_STATE.TELEPORTING);
	}

	@Test
	public void testHandleEventDashPressed() {
		inputHandler.getInputBox().updateButtonPressed(BUTTONS.DASH, true);
		characterState.handleInput(inputHandler.getInputBox());

		verify(characterStateManager).pushState(CHARACTER_STATE.DASHING);
	}

	@Test
	public void testHandleEventRollPressed() {
		inputHandler.getInputBox().updateButtonPressed(BUTTONS.ROLL, true);
		characterState.handleInput(inputHandler.getInputBox());

		verify(characterStateManager).pushState(CHARACTER_STATE.ROLLING);
	}

	@Test
	public void testHandleEventBlockPressed() {
		inputHandler.getInputBox().updateButtonPressed(BUTTONS.BLOCK, true);
		characterState.handleInput(inputHandler.getInputBox());

		verify(characterStateManager).pushState(CHARACTER_STATE.BLOCKING);
	}

	@Test
	public void testHandleEventCastPressed() {
		inputHandler.getInputBox().updateButtonPressed(BUTTONS.SPELL0, true);
		characterState.handleInput(inputHandler.getInputBox());

		verify(characterStateManager).pushState(CHARACTER_STATE.PRECAST);
	}

	@Test
	public void testHandleInputLeftPressed() {
		inputHandler.getInputBox().updateButtonPressed(BUTTONS.LEFT, true);
		characterState.update(1f);

		verify(characterStateManager).continueMovingOnTheGround(-Constants.SPRINT_SPEED);
		verify(characterStateManager).popState();
		verify(characterStateManager).pushState(CHARACTER_STATE.WALKING);
	}

	@Test
	public void testHandleInputRightPressed() {
		inputHandler.getInputBox().updateButtonPressed(BUTTONS.RIGHT, true);
		characterState.update(1f);

		verify(characterStateManager).continueMovingOnTheGround(Constants.SPRINT_SPEED);
		verify(characterStateManager).popState();
		verify(characterStateManager).pushState(CHARACTER_STATE.WALKING);
	}

	@Test
	public void testHandleInputLeftUnPressed() {
		inputHandler.getInputBox().updateButtonPressed(BUTTONS.LEFT, false);
		characterState.update(1f);

		verify(characterStateManager).stopMovingOnTheGround();
	}

	@Test
	public void testHandleInputRightUnPressed() {
		inputHandler.getInputBox().updateButtonPressed(BUTTONS.RIGHT, false);
		characterState.update(1f);

		verify(characterStateManager).stopMovingOnTheGround();
	}

	@Test
	public void testExit() {
		characterState.exit();

	}

}
