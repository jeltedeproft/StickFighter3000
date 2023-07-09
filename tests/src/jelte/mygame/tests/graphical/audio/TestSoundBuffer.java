package jelte.mygame.tests.graphical.audio;

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

import de.pottgames.tuningfork.Audio;
import de.pottgames.tuningfork.SoundBuffer;
import de.pottgames.tuningfork.logger.TuningForkLogger;
import jelte.mygame.graphical.audio.AudioFileReader;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@Ignore
@RunWith(GdxTestRunner.class)
public class TestSoundBuffer {

	@Mock
	private Audio mockedAudio;

	@Mock
	private TuningForkLogger tuningForkLogger;

	private SoundBuffer soundBuffer;

	@BeforeClass
	public static void beforeAllTests() {
		ApplicationLogger logger = new MultiFileLogger();
		Gdx.app.setApplicationLogger(logger);
		AudioFileReader.loadAudioInMemory(Constants.AUDIO_FILE_LOCATION);
		AssetManagerUtility.loadSoundBufferAsset("audio/attack1.wav");
	}

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		when(mockedAudio.getLogger()).thenReturn(tuningForkLogger);
		SoundBuffer soundBuffer = AssetManagerUtility.getSoundBufferAsset("audio/attack1.wav");
	}

	@Test
	public void testSoundBufferPlay() {
		// Set up test data
		float volume = 0.5f;

		// Invoke the method under test
		soundBuffer.play(volume);

		// Verify the expected interactions
		// verify(mockedAudio).play(soundBuffer);
		// verify(mockedAudio).play(soundBuffer, volume);
	}

}
