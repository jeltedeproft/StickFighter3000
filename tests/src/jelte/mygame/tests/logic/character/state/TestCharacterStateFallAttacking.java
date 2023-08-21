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
import jelte.mygame.logic.character.state.CharacterStateFallAttacking;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateFallAttacking {

	private CharacterStateManager characterStateManager;
	private CharacterStateFallAttacking characterState;

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
		characterState = new CharacterStateFallAttacking(characterStateManager);
	}

	@Test
	public void testEntry() {
		characterState.entry();
		verify(characterStateManager).pullDown(Constants.FALL_ATTACK_SPEED_BOOST);
		verify(characterStateManager).makeSpellReady(SpellFileReader.getSpellData().get(1));
	}

	@Test
	public void testUpdate() {
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).characterHaslanded();
	}

	@Test
	public void testUpdateLanded() {
		when(characterStateManager.characterHaslanded()).thenReturn(true);
		float delta = 1f;

		characterState.update(delta);

		verify(characterStateManager).pushState(CHARACTER_STATE.LANDATTACKING);
	}

	@Test
	public void testExit() {
		characterState.exit();

	}

}
