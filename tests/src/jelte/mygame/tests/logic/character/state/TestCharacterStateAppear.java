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
import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.input.CharacterInputHandler;
import jelte.mygame.logic.character.state.CharacterStateAppear;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateAppear {

	private CharacterStateManager characterStateManager;
	private CharacterStateAppear characterState;

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
		characterState = new CharacterStateAppear(characterStateManager, duration);
		Character character = mock(Character.class);
		when(character.getPhysicsComponent()).thenReturn(new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0)));
		when(character.getName()).thenReturn("test");
		when(character.getCharacterInputHandler()).thenReturn(new CharacterInputHandler());
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

		verify(characterStateManager).pushState(CHARACTER_STATE.IDLE);
	}

	@Test
	public void testHandleInputAttackPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.ATTACK, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).popState();
		verify(characterStateManager).pushState(CHARACTER_STATE.ATTACKING);
	}

	@Test
	public void testHandleEventDamageTaken() {
		EVENT event = EVENT.DAMAGE_TAKEN;
		characterState.handleEvent(event);

		verify(characterStateManager).pushState(CHARACTER_STATE.HURT);
	}

	@Test
	public void testHandleInputJumpPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.UP, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).pushState(CHARACTER_STATE.JUMPING);
	}

	@Test
	public void testHandleInputTeleportPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.TELEPORT, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).pushState(CHARACTER_STATE.TELEPORTING);
	}

	@Test
	public void testHandleInputDashPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.DASH, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).pushState(CHARACTER_STATE.DASHING);
	}

	@Test
	public void testHandleInputRollPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.ROLL, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).pushState(CHARACTER_STATE.ROLLING);
	}

	@Test
	public void testHandleInputBlockPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.BLOCK, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).pushState(CHARACTER_STATE.BLOCKING);
	}

	@Test
	public void testHandleInputCastPressed() {
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

		verify(characterStateManager).startMovingOnTheGround(-Constants.WALK_SPEED);
		verify(characterStateManager).popState();
		verify(characterStateManager).pushState(CHARACTER_STATE.WALKING);
	}

	@Test
	public void testHandleInputRightPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.RIGHT, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).startMovingOnTheGround(Constants.WALK_SPEED);
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
