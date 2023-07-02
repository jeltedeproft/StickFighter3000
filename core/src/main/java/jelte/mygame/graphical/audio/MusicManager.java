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
import de.pottgames.tuningfork.jukebox.JukeBox;
import de.pottgames.tuningfork.jukebox.playlist.PlayList;
import de.pottgames.tuningfork.jukebox.playlist.ThemePlayListProvider;
import de.pottgames.tuningfork.jukebox.song.Song;
import de.pottgames.tuningfork.jukebox.song.SongMeta;
import de.pottgames.tuningfork.jukebox.song.SongSettings;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

//TODO add link between entity and sond so that we can update pos sound
//TODO start usins positions and maybe reverb in cave
//TODO refactor
public class MusicManager implements Disposable, MusicManagerInterface {
	private static final String TAG = MusicManager.class.getSimpleName();
	private static MusicManagerInterface instance = null;

	private static final Map<AudioEnum, Array<Float>> musicTimers = new EnumMap<>(AudioEnum.class);
	private static final Map<AudioEnum, Float> cooldowns = new EnumMap<>(AudioEnum.class);
	private static final Map<Integer, AudioData> audioDataForIds = new HashMap<>();

	private final HashMap<String, StreamedSoundSource> songs;
	private final HashMap<String, Array<BufferedSoundSource>> loadedSounds;
	private final SoundListener listener;
	private Audio audio;
	private static final Map<MusicTheme, PlayList> playlistsPerTheme = new EnumMap<>(MusicTheme.class);
	private ThemePlayListProvider provider;
	private JukeBox jukeBox;

	private MusicManager() {
		songs = new HashMap<>();
		loadedSounds = new HashMap<>();

		AudioConfig config = new AudioConfig();
		config.setDistanceAttenuationModel(DistanceAttenuationModel.LINEAR_DISTANCE);
		config.setLogger((MultiFileLogger) Gdx.app.getApplicationLogger());
		audio = Audio.init(config);
		audio.setDefaultAttenuationMaxDistance(5f);
		listener = audio.getListener();
		autoloadSounds();
		autoloadMusic();

		provider = new ThemePlayListProvider();
		provider.setTheme(MusicTheme.PLAYING.ordinal());// TODO starting theme is main menu?
		for (Entry<MusicTheme, PlayList> entry : playlistsPerTheme.entrySet()) {
			entry.getValue().setLooping(true);
			entry.getValue().setShuffleAfterPlaytrough(true);
			entry.getValue().shuffle();
			provider.add(entry.getValue(), entry.getKey().ordinal());
		}
		jukeBox = new JukeBox(provider);
		jukeBox.play();
	}

	private void autoloadSounds() {
		for (AudioEnum audioEnum : AudioEnum.values()) {
			if (audioEnum.name().startsWith("SOUND_")) {
				final AudioData audioData = getAudioData(audioEnum);
				audioData.getAudioFileName().forEach(AssetManagerUtility::loadSoundBufferAsset);
			}
		}
	}

	private void autoloadMusic() {
		for (AudioEnum audioEnum : AudioEnum.values()) {
			if (audioEnum.name().startsWith("PLAYLIST_")) {
				final AudioData audioData = getAudioData(audioEnum);
				audioData.getAudioFileName().forEach(fullFilePath -> {
					StreamedSoundSource music = new StreamedSoundSource(Gdx.files.internal(fullFilePath));
					SongMeta meta = new SongMeta().setTitle(audioEnum.name());
					SongSettings settings = SongSettings.linear(audioData.getVolume(), Constants.MUSIC_FADE_IN_DURATION, Constants.MUSIC_FADE_OUT_DURATION);
					Song song = new Song(music, settings, meta);
					playlistsPerTheme.computeIfAbsent(MusicTheme.values()[audioData.getTheme()], key -> new PlayList());
					playlistsPerTheme.get(MusicTheme.values()[audioData.getTheme()]).addSong(song);
				});
			}
		}
	}

	private AudioData getAudioData(AudioEnum event) {
		return audioDataForIds.computeIfAbsent(event.ordinal(), k -> AudioFileReader.getAudioData().get(k));
	}

	@Override
	public void update(float delta, float cameraX, float cameraY) {
		jukeBox.update();
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
		case SOUND_PLAY_LOOP:
			playLoopedSound(audioData.getRandomAudioFileName(), volume, pos);
			break;
		case SOUND_PLAY_LOOP_UNIQUE:
			final Array<BufferedSoundSource> uniqueSounds = loadedSounds.get(audioData.getRandomAudioFileName());
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
		case MUSIC_CHANGE_THEME:
			provider.setTheme(audioData.getTheme());
			break;
		case MUSIC_PLAY:
			jukeBox.play();
			break;
		case MUSIC_PAUSE:
			jukeBox.pause();
			break;
		case MUSIC_STOP:
			jukeBox.stop();
			break;
		case SOUND_PLAY_LOOP:
			playLoopedSound(audioData.getRandomAudioFileName(), volume);
			break;
		case SOUND_PLAY_LOOP_UNIQUE:
			final Array<BufferedSoundSource> uniqueSounds = loadedSounds.get(audioData.getRandomAudioFileName());
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
				final Array<BufferedSoundSource> sounds = loadedSounds.get(name);
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
		loadedSounds.computeIfAbsent(fullFilePath, s -> new Array<>());
		if (AssetManagerUtility.isAssetLoaded(fullFilePath)) {
			SoundBuffer soundBuffer = AssetManagerUtility.getSoundBufferAsset(fullFilePath);
			BufferedSoundSource bufferedSound = audio.obtainSource(soundBuffer);
			bufferedSound.setVolume(volume);
			bufferedSound.setLooping(true);
			loadedSounds.get(fullFilePath).add(bufferedSound);
			return bufferedSound;
		}
		Gdx.app.error(TAG, "Sound not loaded", null);
		return null;
	}

	private void setCooldown(AudioEnum event, float cooldown) {
		cooldowns.put(event, cooldown);
	}

	private boolean offCooldown(AudioEnum event) {
		return cooldowns.get(event) == null || cooldowns.get(event) <= 0;
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
		songs.values().forEach(StreamedSoundSource::dispose);
		loadedSounds.values().forEach(array -> array.forEach(BufferedSoundSource::free));
		audio.dispose();
	}

}
