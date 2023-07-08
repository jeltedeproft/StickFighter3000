package jelte.mygame.tests.graphical.audio;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import de.pottgames.tuningfork.Audio;
import de.pottgames.tuningfork.SoundBuffer;
import de.pottgames.tuningfork.SoundListener;
import de.pottgames.tuningfork.jukebox.JukeBox;
import de.pottgames.tuningfork.jukebox.playlist.PlayList;
import de.pottgames.tuningfork.jukebox.song.Song;
import de.pottgames.tuningfork.logger.TuningForkLogger;
import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.AudioFileReader;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@Ignore
@RunWith(GdxTestRunner.class)
public class TestMusicManager {

	private MusicManagerInterface musicManager;

	@Mock
	private Audio mockedAudio;

	@Mock
	private TuningForkLogger tuningForkLogger;

	@Mock

	private Song mockedSong;
	@Mock
	private SoundBuffer soundBuffer;

	@Mock
	private SoundListener mockedListener;

	@Mock
	private JukeBox mockedJukeBox;

	@Mock
	private PlayList mockedPlayList;

	@BeforeClass
	public static void beforeAllTests() {
		ApplicationLogger logger = new MultiFileLogger();
		Gdx.app.setApplicationLogger(logger);
		AudioFileReader.loadAudioInMemory(Constants.AUDIO_FILE_LOCATION);
	}

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		when(mockedAudio.getListener()).thenReturn(mockedListener);
		when(mockedAudio.getLogger()).thenReturn(tuningForkLogger);
		when(mockedJukeBox.getCurrentSong()).thenReturn(mockedSong);

		// musicManager = new TestMusicManager(mockedAudio, mockedJukeBox);
	}

	@Test
	public void testGetInstance() {
		assertNotNull(musicManager);
		assertTrue(musicManager instanceof MusicManagerInterface);
	}

	@Test
	public void testMusicManagerPlayTheme() {
		musicManager.sendCommand(AudioCommand.MUSIC_CHANGE_THEME, AudioEnum.PLAYLIST_MAIN);

		verify(soundBuffer).play();
		verify(mockedJukeBox).play();
	}

	@Test
	public void testUpdate() {
		float delta = 0.5f;
		float cameraX = 10f;
		float cameraY = 5f;

		musicManager.update(delta, cameraX, cameraY);

		verify(mockedListener).setPosition(new Vector3(cameraX, cameraY, 0));
	}

	@Test
	public void testSendCommandSoundPlayOnceWithCooldown() {
		float cooldown = 2f;

		// Start playing the sound
		musicManager.sendCommand(AudioCommand.SOUND_PLAY_ONCE_WITH_COOLDOWN, AudioEnum.SOUND_ATTACK);
		// Assert that the sound is played

		// Try playing the sound again within the cooldown period
		musicManager.sendCommand(AudioCommand.SOUND_PLAY_ONCE_WITH_COOLDOWN, AudioEnum.SOUND_ATTACK);
		// Assert that the sound is not played

		// Wait for the cooldown period to pass
		Gdx.graphics.getDeltaTime(); // Simulate some time passing
		Gdx.graphics.getDeltaTime(); // Simulate some time passing

		// Try playing the sound again after the cooldown period
		musicManager.sendCommand(AudioCommand.SOUND_PLAY_ONCE_WITH_COOLDOWN, AudioEnum.SOUND_ATTACK);
		// Assert that the sound is played again
	}

	@Test
	public void testSendCommandSoundPlayLoopFor() {
		float duration = 3f;

		// Start playing the looped sound for the specified duration
		musicManager.sendCommand(AudioCommand.SOUND_PLAY_LOOP_FOR, AudioEnum.SOUND_ATTACK);
		// Assert that the sound is played

		// Wait for the duration to pass
		Gdx.graphics.getDeltaTime(); // Simulate some time passing
		Gdx.graphics.getDeltaTime(); // Simulate some time passing
		Gdx.graphics.getDeltaTime(); // Simulate some time passing

		// Assert that the sound is stopped after the duration
		// Add assertions to check if the sound is stopped
	}

}
