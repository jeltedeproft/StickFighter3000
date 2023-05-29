package jelte.mygame.graphical.audio;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import de.pottgames.tuningfork.Audio;
import de.pottgames.tuningfork.AudioConfig;
import de.pottgames.tuningfork.BufferedSoundSource;
import de.pottgames.tuningfork.DistanceAttenuationModel;
import de.pottgames.tuningfork.SoundBuffer;
import de.pottgames.tuningfork.SoundListener;
import de.pottgames.tuningfork.StreamedSoundSource;
import jelte.mygame.utility.AssetManagerUtility;

//TODO add link between entity and sond so that we can update pos sound
//TODO start usins positions and maybe reverb in cave
//TODO refactor
public class MusicManager implements Disposable, MusicManagerInterface {
	private static final String TAG = MusicManager.class.getSimpleName();
	private static MusicManagerInterface instance = null;

	private static final Map<AudioEnum, Array<Float>> musicTimers = new EnumMap<>(AudioEnum.class);
	private static final Map<AudioEnum, Float> cooldowns = new EnumMap<>(AudioEnum.class);
	private static final Map<Integer, AudioData> audioDataForIds = new HashMap<>();

	private final HashMap<String, StreamedSoundSource> queuedMusic;
	private final HashMap<String, Array<BufferedSoundSource>> queuedSounds;
	private final SoundListener listener;
	private Audio audio;

	private MusicManager() {
		queuedMusic = new HashMap<>();
		queuedSounds = new HashMap<>();

		AudioConfig config = new AudioConfig();
		config.setDistanceAttenuationModel(DistanceAttenuationModel.LINEAR_DISTANCE);
		audio = Audio.init(config);
		audio.setDefaultAttenuationMaxDistance(5f);
		listener = audio.getListener();
		autoloadSounds();
	}

	private void autoloadSounds() {
		for (AudioEnum audioEnum : AudioEnum.values()) {
			if (audioEnum.name().startsWith("SOUND_")) {
				final AudioData audioData = getAudioData(audioEnum);
				if (audioData == null) {
					int j = 5;
				}
				audioData.getAudioFileName().forEach(AssetManagerUtility::loadSoundBufferAsset);
			}
		}
	}

	private AudioData getAudioData(AudioEnum event) {
		return audioDataForIds.computeIfAbsent(event.ordinal(), k -> AudioFileReader.getAudioData().get(k));
	}

	@Override
	public void update(float delta, float cameraX, float cameraY) {
		listener.setPosition(new Vector3(cameraX, cameraY, 0));
		decreaseTimers(delta);
		cooldowns.replaceAll((k, v) -> v = v - delta);
		stopAudioIfTimerUp();
		cleanup();
	}

	private void decreaseTimers(float delta) {
		for (Array<Float> timers : musicTimers.values()) {
			for (int i = 0; i < timers.size; i++) {
				Float original = timers.get(i);
				timers.set(i, original - delta);
			}
		}
	}

	private void stopAudioIfTimerUp() {
		for (Entry<AudioEnum, Array<Float>> entry : musicTimers.entrySet()) {
			for (Float timer : entry.getValue()) {
				if (timer <= 0f) {
					sendCommand(AudioCommand.SOUND_STOP, entry.getKey());
				}
			}
		}
	}

	private void cleanup() {
		for (Array<Float> timers : musicTimers.values()) {
			final Iterator<Float> iterator = timers.iterator();
			while (iterator.hasNext()) {
				final Float currentTimer = iterator.next();
				if (currentTimer <= 0f) {
					iterator.remove();
					break;
				}
			}
		}
	}

	@Override
	public void sendCommand(AudioCommand command, AudioEnum event, Vector2 pos) {
		final AudioData audioData = getAudioData(event);
		final float volume = audioData.getVolume();

		switch (command) {
		case MUSIC_PLAY_LOOP:
			playMusic(true, audioData.getRandomAudioFileName(), volume, pos);
			break;
		case SOUND_PLAY_LOOP:
			playLoopedSound(audioData.getRandomAudioFileName(), volume, pos);
			break;
		case SOUND_PLAY_LOOP_UNIQUE:
			final Array<BufferedSoundSource> uniqueSounds = queuedSounds.get(audioData.getRandomAudioFileName());
			if (uniqueSounds == null || uniqueSounds.isEmpty()) {
				playLoopedSound(audioData.getRandomAudioFileName(), volume, pos);
			}
			break;
		case SOUND_PLAY_LOOP_FOR:
			musicTimers.computeIfAbsent(event, s -> new Array<>());
			musicTimers.get(event).add(audioData.getDuration());
			playLoopedSound(audioData.getRandomAudioFileName(), volume, pos);
			break;
		case SOUND_PLAY_ONCE:
			playAndForget(audioData.getRandomAudioFileName(), volume, pos);
			break;
		case SOUND_PLAY_ONCE_WITH_COOLDOWN:
			if (offCooldown(event)) {
				playAndForget(audioData.getRandomAudioFileName(), volume, pos);
				setCooldown(event, audioData.getCooldown());
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void sendCommand(AudioCommand command, AudioEnum event) {
		final AudioData audioData = getAudioData(event);
		final float volume = audioData.getVolume();

		switch (command) {
		case MUSIC_PLAY_ONCE:
			playMusic(false, audioData.getRandomAudioFileName(), volume);
			break;
		case MUSIC_PLAY_LOOP:
			playMusic(true, audioData.getRandomAudioFileName(), volume);
			break;
		case MUSIC_PAUSE:
			final StreamedSoundSource musicToPause = queuedMusic.get(audioData.getRandomAudioFileName());
			if (musicToPause != null) {
				musicToPause.pause();
			}
			break;
		case MUSIC_RESUME:
			final StreamedSoundSource musicToResume = queuedMusic.get(audioData.getRandomAudioFileName());
			if (musicToResume != null) {
				musicToResume.setVolume(volume);
				musicToResume.play();
			}
			break;
		case MUSIC_STOP:
			final StreamedSoundSource music = queuedMusic.get(audioData.getRandomAudioFileName());
			if (music != null) {
				music.stop();
			}
			break;
		case MUSIC_STOP_ALL:
			queuedMusic.values().forEach(StreamedSoundSource::stop);
			break;
		case SOUND_PLAY_LOOP:
			playLoopedSound(audioData.getRandomAudioFileName(), volume);
			break;
		case SOUND_PLAY_LOOP_UNIQUE:
			final Array<BufferedSoundSource> uniqueSounds = queuedSounds.get(audioData.getRandomAudioFileName());
			if (uniqueSounds == null || uniqueSounds.isEmpty()) {
				playLoopedSound(audioData.getRandomAudioFileName(), volume);
			}
			break;
		case SOUND_PLAY_LOOP_FOR:
			musicTimers.computeIfAbsent(event, s -> new Array<>());
			musicTimers.get(event).add(audioData.getDuration());
			playLoopedSound(audioData.getRandomAudioFileName(), volume);
			break;
		case SOUND_PLAY_ONCE:
			playAndForget(audioData.getRandomAudioFileName(), volume);
			break;
		case SOUND_PLAY_ONCE_WITH_COOLDOWN:
			if (offCooldown(event)) {
				playAndForget(audioData.getRandomAudioFileName(), volume);
				setCooldown(event, audioData.getCooldown());
			}
			break;
		case SOUND_STOP:
			audioData.getAudioFileName().forEach(name -> {
				final Array<BufferedSoundSource> sounds = queuedSounds.get(name);
				if (sounds != null && sounds.size > 0) {
					sounds.get(0).stop();// TODO removes the first, is this ok? maybe we wanna stop the second
					sounds.removeIndex(0);
				}
			});

			break;
		default:
			break;
		}
	}

	private void setCooldown(AudioEnum event, float cooldown) {
		cooldowns.put(event, cooldown);
	}

	private boolean offCooldown(AudioEnum event) {
		return cooldowns.get(event) == null || cooldowns.get(event) <= 0;
	}

	private void playMusic(boolean isLooping, String fullFilePath, float volume, Vector2 pos) {
		StreamedSoundSource music = createMusic(isLooping, fullFilePath, volume);
		music.setPosition(new Vector3(pos.x, pos.y, 0));
		music.play();
	}

	private void playMusic(boolean isLooping, String fullFilePath, float volume) {
		StreamedSoundSource music = createMusic(isLooping, fullFilePath, volume);
		music.play();
	}

	private StreamedSoundSource createMusic(boolean isLooping, String fullFilePath, float volume) {
		StreamedSoundSource music = queuedMusic.get(fullFilePath);
		if (music != null) {
			music.setLooping(isLooping);
			music.setVolume(volume);
		} else {
			music = new StreamedSoundSource(Gdx.files.internal(fullFilePath));
			music.setLooping(isLooping);
			music.setVolume(volume);
			queuedMusic.put(fullFilePath, music);
		}
		return music;
	}

	private void playLoopedSound(String fullFilePath, float volume) {
		BufferedSoundSource sound = createBufferedSound(fullFilePath, volume);
		if (sound != null) {
			sound.play();
		}
	}

	private void playLoopedSound(String fullFilePath, float volume, Vector2 pos) {
		BufferedSoundSource sound = createBufferedSound(fullFilePath, volume);
		if (sound != null) {
			sound.setDirection(new Vector3(1, 1, 0));
			sound.setPosition(pos.x, pos.y, 0);
			sound.play();
		}
	}

	private void playAndForget(String fullFilePath, float volume) {
		if (AssetManagerUtility.isAssetLoaded(fullFilePath)) {
			SoundBuffer soundBuffer = AssetManagerUtility.getSoundBufferAsset(fullFilePath);
			soundBuffer.play(volume);
		}
	}

	private void playAndForget(String fullFilePath, float volume, Vector2 pos) {
		if (AssetManagerUtility.isAssetLoaded(fullFilePath)) {
			SoundBuffer soundBuffer = AssetManagerUtility.getSoundBufferAsset(fullFilePath);
			soundBuffer.play3D(volume, new Vector3(pos.x, pos.y, 0));
		}
	}

	private BufferedSoundSource createBufferedSound(String fullFilePath, float volume) {
		queuedSounds.computeIfAbsent(fullFilePath, s -> new Array<>());
		if (AssetManagerUtility.isAssetLoaded(fullFilePath)) {
			SoundBuffer soundBuffer = AssetManagerUtility.getSoundBufferAsset(fullFilePath);
			BufferedSoundSource bufferedSound = audio.obtainSource(soundBuffer);
			bufferedSound.setVolume(volume);
			bufferedSound.setLooping(true);
			queuedSounds.get(fullFilePath).add(bufferedSound);
			return bufferedSound;
		}
		Gdx.app.debug(TAG, "Sound not loaded");
		return null;
	}

	public static MusicManagerInterface getInstance() {
		if (instance == null) {
			instance = new MusicManager();
		}

		return instance;
	}

	public static void setInstance(MusicManagerInterface newInstance) {
		instance = newInstance;
	}

	@Override
	public void dispose() {
		queuedMusic.values().forEach(StreamedSoundSource::dispose);
		queuedSounds.values().forEach(array -> array.forEach(BufferedSoundSource::free));
		audio.dispose();
	}

}
