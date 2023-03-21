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

public class MusicManager implements Disposable {
	private static final String TAG = MusicManager.class.getSimpleName();
	private static final Map<AudioEnum, Array<Float>> musicTimers = new EnumMap<>(AudioEnum.class);
	private static final Map<AudioEnum, Float> cooldowns = new EnumMap<>(AudioEnum.class);
	private static final Map<Integer, AudioData> audioDataForIds = new HashMap<>();

	private static MusicManager instance = null;
	private Audio audio;

	private final HashMap<String, StreamedSoundSource> queuedMusic;
	private final HashMap<String, Array<BufferedSoundSource>> queuedSounds;
	private final SoundListener listener;

	public enum AudioCommand {
		MUSIC_PLAY_ONCE, MUSIC_PLAY_LOOP, MUSIC_STOP, MUSIC_STOP_ALL, SOUND_LOAD, SOUND_PLAY_ONCE, SOUND_PLAY_LOOP, SOUND_PLAY_LOOP_FOR, SOUND_STOP, MUSIC_PAUSE, MUSIC_RESUME, SOUND_PLAY_ONCE_WITH_COOLDOWN, SOUND_PLAY_LOOP_UNIQUE, STOP_ALL, PAUZE_ALL, RESUME_ALL
	}

	public enum AudioEnum {
		MAIN_THEME;// keep in sync with file

		private static final AudioEnum[] copyOfValues = AudioEnum.values();

		public static AudioEnum forName(String name) {
			for (AudioEnum value : copyOfValues) {
				if (value.name().equalsIgnoreCase(name)) {
					return value;
				}
			}
			return null;
		}

	}

	private MusicManager() {
		queuedMusic = new HashMap<>();
		queuedSounds = new HashMap<>();

		AudioConfig config = new AudioConfig();
		config.setDistanceAttenuationModel(DistanceAttenuationModel.LINEAR_DISTANCE);
		audio = Audio.init(config);
		audio.setDefaultAttenuationMaxDistance(5f);
		listener = audio.getListener();
	}

	public static MusicManager getInstance() {
		if (instance == null) {
			instance = new MusicManager();
		}

		return instance;
	}

	public void update(float delta, float cameraX, float cameraY) {
		listener.setPosition(new Vector3(cameraX, cameraY, 0));
		for (Array<Float> numbersToUpdate : musicTimers.values()) {
			for (int i = 0; i < numbersToUpdate.size; i++) {
				Float original = numbersToUpdate.get(i);
				numbersToUpdate.set(i, original - delta);
			}
		}
		cooldowns.replaceAll((k, v) -> v = v - delta);
		stopAudioIfTimerUp();
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

	private void stopAudioIfTimerUp() {
		for (Entry<AudioEnum, Array<Float>> entry : musicTimers.entrySet()) {
			for (Float timer : entry.getValue()) {
				if (timer <= 0f) {
					sendCommand(AudioCommand.SOUND_STOP, entry.getKey());
				}
			}
		}
	}

	public void sendCommand(AudioCommand command, String string) {
		AudioEnum sound = AudioEnum.forName(string);
		if (sound != null) {
			sendCommand(command, AudioEnum.forName(string));
		}
	}

	public void sendCommand(AudioCommand command, AudioEnum event, Vector2 pos) {
		final AudioData audioData = getAudioData(event);
		final float volume = audioData.getVolume();
		switch (command) {
		case MUSIC_PLAY_LOOP:
			playMusic(true, audioData.getAudioFileName(), volume, pos);
			break;
		case SOUND_PLAY_LOOP:
			playSound(true, audioData.getAudioFileName(), volume, pos);
			break;
		case SOUND_PLAY_LOOP_UNIQUE:
			final Array<BufferedSoundSource> uniqueSounds = queuedSounds.get(audioData.getAudioFileName());
			if (uniqueSounds == null || uniqueSounds.isEmpty()) {
				playSound(true, audioData.getAudioFileName(), volume, pos);
			}
			break;
		case SOUND_PLAY_LOOP_FOR:
			musicTimers.computeIfAbsent(event, s -> new Array<>());
			musicTimers.get(event).add(audioData.getDuration());
			playSound(true, audioData.getAudioFileName(), volume, pos);
			break;
		case SOUND_PLAY_ONCE:
			playSound(false, audioData.getAudioFileName(), volume, pos);
			break;
		case SOUND_PLAY_ONCE_WITH_COOLDOWN:
			if (offCooldown(event)) {
				playSound(false, audioData.getAudioFileName(), volume, pos);
				setCooldown(event, audioData.getCooldown());
			}
			break;
		default:
			break;
		}
	}

	public void sendCommand(AudioCommand command, AudioEnum event) {
		final AudioData audioData = getAudioData(event);
		final float volume = audioData.getVolume();
		switch (command) {
		case MUSIC_PLAY_ONCE:
			playMusic(false, audioData.getAudioFileName(), volume);
			break;
		case MUSIC_PLAY_LOOP:
			playMusic(true, audioData.getAudioFileName(), volume);
			break;
		case MUSIC_STOP:
			final StreamedSoundSource music = queuedMusic.get(audioData.getAudioFileName());
			if (music != null) {
				music.stop();
			}
			break;
		case MUSIC_STOP_ALL:
			for (final StreamedSoundSource musicStop : queuedMusic.values()) {
				musicStop.stop();
			}
			break;
		case SOUND_LOAD:
			AssetManagerUtility.loadSoundBufferAsset(audioData.getAudioFileName());
			break;
		case SOUND_PLAY_LOOP:
			playSound(true, audioData.getAudioFileName(), volume);
			break;
		case SOUND_PLAY_LOOP_UNIQUE:
			final Array<BufferedSoundSource> uniqueSounds = queuedSounds.get(audioData.getAudioFileName());
			if (uniqueSounds == null || uniqueSounds.isEmpty()) {
				playSound(true, audioData.getAudioFileName(), volume);
			}
			break;
		case SOUND_PLAY_LOOP_FOR:
			musicTimers.computeIfAbsent(event, s -> new Array<>());
			musicTimers.get(event).add(audioData.getDuration());
			playSound(true, audioData.getAudioFileName(), volume);
			break;
		case SOUND_PLAY_ONCE:
			playSound(false, audioData.getAudioFileName(), volume);
			break;
		case SOUND_PLAY_ONCE_WITH_COOLDOWN:
			if (offCooldown(event)) {
				playSound(false, audioData.getAudioFileName(), volume);
				setCooldown(event, audioData.getCooldown());
			}
			break;
		case SOUND_STOP:
			final Array<BufferedSoundSource> sounds = queuedSounds.get(audioData.getAudioFileName());
			if (sounds != null && sounds.size > 0) {
				sounds.get(0).stop();// TODO removes the first, is this ok? maybe we wanna stop the second
				sounds.removeIndex(0);
			}
			break;
		case MUSIC_PAUSE:
			final StreamedSoundSource musicToPause = queuedMusic.get(audioData.getAudioFileName());
			if (musicToPause != null) {
				musicToPause.pause();
			}
			break;
		case MUSIC_RESUME:
			final StreamedSoundSource musicToResume = queuedMusic.get(audioData.getAudioFileName());
			if (musicToResume != null) {
				musicToResume.setVolume(volume);
				musicToResume.play();
			}
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

	private AudioData getAudioData(AudioEnum event) {
		return audioDataForIds.computeIfAbsent(event.ordinal(), k -> AudioFileReader.getAudioData().get(k));
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

	private void playSound(boolean isLooping, String fullFilePath, float volume) {
		BufferedSoundSource sound = createSound(isLooping, fullFilePath, volume);
		if (sound != null) {
			sound.play();
		}
	}

	private void playSound(boolean isLooping, String fullFilePath, float volume, Vector2 pos) {
		BufferedSoundSource sound = createSound(isLooping, fullFilePath, volume);
		if (sound != null) {
			sound.setDirection(new Vector3(1, 1, 0));
			sound.setPosition(pos.x, pos.y, 0);
			sound.play();
		}
	}

	private BufferedSoundSource createSound(boolean isLooping, String fullFilePath, float volume) {
		queuedSounds.computeIfAbsent(fullFilePath, s -> new Array<>());
		if (AssetManagerUtility.isAssetLoaded(fullFilePath)) {
			SoundBuffer soundBuffer = AssetManagerUtility.getSoundBufferAsset(fullFilePath);
			BufferedSoundSource bufferedSound = audio.obtainSource(soundBuffer);
			bufferedSound.setVolume(volume);
			bufferedSound.setLooping(isLooping);
			queuedSounds.get(fullFilePath).add(bufferedSound);
			return bufferedSound;
		}
		Gdx.app.debug(TAG, "Sound not loaded");
		return null;
	}

	@Override
	public void dispose() {
		for (final StreamedSoundSource music : queuedMusic.values()) {
			music.dispose();
		}

		for (final Array<BufferedSoundSource> sounds : queuedSounds.values()) {
			for (BufferedSoundSource sound : sounds) {
				sound.free();
			}

		}

		this.audio.dispose();
	}

}
