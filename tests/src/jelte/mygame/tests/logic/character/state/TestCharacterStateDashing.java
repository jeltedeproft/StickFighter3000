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
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.input.CharacterInputHandler;
import jelte.mygame.logic.character.state.CharacterStateDashing;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateDashing {

	private CharacterStateManager characterStateManager;
	private CharacterStateDashing characterState;

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
		characterState = new CharacterStateDashing(characterStateManager, duration);
		Character character = mock(Character.class);
		when(character.getName()).thenReturn("test");
		when(character.getCharacterInputHandler()).thenReturn(new CharacterInputHandler());
		when(character.getPhysicsComponent()).thenReturn(new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0)));
		when(characterStateManager.getCharacter()).thenReturn(character);
	}

	@Test
	public void testEntry() {
		characterState.entry();
		// Assert any expected behavior during the entry phase
	}

	@Test
	public void testUpdate() {
		float delta = 1f;

		characterState.update(delta);
		verify(characterStateManager).applyHorizontalForce(Constants.DASH_DISTANCE);
	}

	@Test
	public void testExit() {
		characterState.exit();

	}

}
