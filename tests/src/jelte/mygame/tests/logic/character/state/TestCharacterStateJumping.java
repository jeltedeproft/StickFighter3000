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
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateJumping;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
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
		verify(characterStateManager).forceUp(Constants.JUMP_SPEED.y);
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

		verify(characterStateManager).transition(CHARACTER_STATE.GRABBING);
	}

	@Test
	public void testUpdateCollisionLeftWall() {
		when(characterStateManager.getCharacterCollisions()).thenReturn(new Array<>(new Collidable.COLLIDABLE_TYPE[] { Collidable.COLLIDABLE_TYPE.STATIC_LEFT}));
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).transition(CHARACTER_STATE.HOLDING);
	}

	@Test
	public void testUpdateCollisionRightWall() {
		when(characterStateManager.getCharacterCollisions()).thenReturn(new Array<>(new Collidable.COLLIDABLE_TYPE[] { Collidable.COLLIDABLE_TYPE.STATIC_RIGHT}));
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).transition(CHARACTER_STATE.HOLDING);
	}

	@Test
	public void testUpdateCollisionLandedPlatform() {
		when(characterStateManager.getCharacterCollisions()).thenReturn(new Array<>(new Collidable.COLLIDABLE_TYPE[] { }));
		when(characterStateManager.characterIsAtHighestPoint()).thenReturn(true);
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).transition(CHARACTER_STATE.JUMPTOFALL);
	}

	@Test
	public void testHandleEventAttackPressed() {
		EVENT event = EVENT.ATTACK_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.ATTACKING);
	}

	@Test
	public void testHandleEventJumpPressed() {
		EVENT event = EVENT.JUMP_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).forceUp(Constants.JUMP_SPEED.y);
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