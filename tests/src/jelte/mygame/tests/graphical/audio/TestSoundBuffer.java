package jelte.mygame.tests.graphical.audio;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.pottgames.tuningfork.Audio;
import de.pottgames.tuningfork.SoundBuffer;
import jelte.mygame.graphical.audio.AudioFileReader;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;

@RunWith(GdxTestRunner.class)
public class TestSoundBuffer {

	@Mock
	private Audio mockedAudio;

	private SoundBuffer soundBuffer;

	@BeforeClass
	public static void beforeAllTests() {
		AudioFileReader.loadAudioInMemory(Constants.AUDIO_FILE_LOCATION);
		AssetManagerUtility.loadSoundBufferAsset("audio/attack1.wav");
	}

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		SoundBuffer soundBuffer = AssetManagerUtility.getSoundBufferAsset("audio/attack1.wav");
	}

	@Test
	public void testSoundBufferPlay() {
		// Set up test data
		float volume = 0.5f;

		// Invoke the method under test
		soundBuffer.play(volume);

		// Verify the expected interactions
		verify(mockedAudio).play(soundBuffer);
		verify(mockedAudio).play(soundBuffer, volume);
	}

}
