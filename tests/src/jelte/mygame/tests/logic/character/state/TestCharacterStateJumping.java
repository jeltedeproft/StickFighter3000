package jelte.mygame.tests.logic.character.state;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

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
import jelte.mygame.logic.character.state.CharacterStateJumping;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateJumping {

	private CharacterStateManager characterStateManager;
	private CharacterStateJumping characterState;

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
		characterState = new CharacterStateJumping(characterStateManager);
	}

	@Test
	public void testEntry() {
		characterState.entry();
		verify(characterStateManager).applyJumpForce();
	}

	@Test
	public void testUpdate() {
		when(characterStateManager.getCharacterCollisions()).thenReturn(new Array<>(new Collidable.COLLIDABLE_TYPE[] { Collidable.COLLIDABLE_TYPE.STATIC_TOP}));
		float delta = 1f;
		characterState.update(delta);
	}

	@Test
	public void testUpdateCollisionCorner() {
		when(characterStateManager.getCharacterCollisions()).thenReturn(new Array<>(new Collidable.COLLIDABLE_TYPE[] { Collidable.COLLIDABLE_TYPE.STATIC_LEFT,Collidable.COLLIDABLE_TYPE.STATIC_TOP}));
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).pushState(CHARACTER_STATE.GRABBING);
	}

	@Test
	public void testUpdateCollisionLeftWall() {
		when(characterStateManager.getCharacterCollisions()).thenReturn(new Array<>(new Collidable.COLLIDABLE_TYPE[] { Collidable.COLLIDABLE_TYPE.STATIC_LEFT}));
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).pushState(CHARACTER_STATE.HOLDING);
	}

	@Test
	public void testUpdateCollisionRightWall() {
		when(characterStateManager.getCharacterCollisions()).thenReturn(new Array<>(new Collidable.COLLIDABLE_TYPE[] { Collidable.COLLIDABLE_TYPE.STATIC_RIGHT}));
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).pushState(CHARACTER_STATE.HOLDING);
	}

	@Test
	public void testUpdateCollisionLandedPlatform() {
		when(characterStateManager.getCharacterCollisions()).thenReturn(new Array<>(new Collidable.COLLIDABLE_TYPE[] { }));
		when(characterStateManager.characterIsAtHighestPoint()).thenReturn(true);
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).pushState(CHARACTER_STATE.JUMPTOFALL);
	}

	@Test
	public void testHandleEventAttackPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.ATTACK, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).pushState(CHARACTER_STATE.ATTACKING);
	}

	@Test
	public void testHandleEventJumpPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.UP, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).applyJumpForce();
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

		verify(characterStateManager).startMovingInTheAir(Constants.WALK_SPEED);
		verify(characterStateManager).pushState(CHARACTER_STATE.WALKING);
	}

	@Test
	public void testHandleInputRightPressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.RIGHT, true);
		characterState.handleInput(inputBox);

		verify(characterStateManager).startMovingInTheAir(Constants.WALK_SPEED);
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