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
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateWallSliding;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateWallSliding {

	private CharacterStateManager characterStateManager;
	private CharacterStateWallSliding characterState;

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
		characterState = new CharacterStateWallSliding(characterStateManager);
	}

	@Test
	public void testEntry() {
		characterState.entry();
		verify(characterStateManager).pullDown(10);
	}

	@Test
	public void testUpdate() {
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).characterHaslanded();
	}

	@Test
	public void testUpdateCollisionCollidedButNotLanded() {
		when(characterStateManager.getCharacterCollisions()).thenReturn(new Array<>(new Collidable.COLLIDABLE_TYPE[] { Collidable.COLLIDABLE_TYPE.STATIC_BOT}));
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).characterHaslanded();
	}

	@Test
	public void testUpdateCollisionLandedButNotCollided() {
		when(characterStateManager.getCharacterCollisions()).thenReturn(new Array<>(new Collidable.COLLIDABLE_TYPE[] { Collidable.COLLIDABLE_TYPE.STATIC_TOP}));
		when(characterStateManager.characterHaslanded()).thenReturn(true);
		float delta = 1f;
		characterState.update(delta);
	}

	@Test
	public void testUpdateCollisionLandedAndCollided() {
		when(characterStateManager.getCharacterCollisions()).thenReturn(new Array<>(new Collidable.COLLIDABLE_TYPE[] { Collidable.COLLIDABLE_TYPE.STATIC_BOT}));
		when(characterStateManager.characterHaslanded()).thenReturn(true);
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).transition(CHARACTER_STATE.LANDING);
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
	public void testHandleEventDownPressed() {
		EVENT event = EVENT.DOWN_PRESSED;
		characterState.handleEvent(event);

		verify(characterStateManager).transition(CHARACTER_STATE.CROUCHED);
	}

	@Test
	public void testExit() {
		characterState.exit();

	}

}
