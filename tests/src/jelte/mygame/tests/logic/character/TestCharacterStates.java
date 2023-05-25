package jelte.mygame.tests.logic.character;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManager.AudioCommand;
import jelte.mygame.graphical.audio.MusicManager.AudioEnum;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterFileReader;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestCharacterStates {
	private CharacterStateManager characterStateManager;
	private Character testCharacter = new Character(CharacterFileReader.getUnitData().get(4), UUID.randomUUID());

	@Mock
	private static MusicManagerInterface mockMusicManager;

	@BeforeClass
	public static void beforeAllTests() {
		CharacterFileReader.loadUnitStatsInMemory();
		MockitoAnnotations.initMocks(TestCharacterStates.class);
		MusicManager.setInstance(mockMusicManager);
	}

	@Before
	public void beforeEverytest() {
		characterStateManager = new CharacterStateManager(testCharacter);
	}

	@Test
	public void testStartInAppear() {
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.APPEARING);
	}

	@Test
	public void testAppearToIdle() {
		characterStateManager.update(1000);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.IDLE);
	}

	@Test
	public void testAppearToAttack() {
		doNothing().when(mockMusicManager).sendCommand(eq(MusicManager.AudioCommand.SOUND_PLAY_ONCE), anyString());
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_APPEAR1);
		characterStateManager.handleEvent(EVENT.ATTACK_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.ATTACKING);
	}

}
