package jelte.mygame.graphical.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import de.pottgames.tuningfork.Audio;
import de.pottgames.tuningfork.AudioConfig;
import de.pottgames.tuningfork.BufferedSoundSource;
import de.pottgames.tuningfork.DistanceAttenuationModel;
import de.pottgames.tuningfork.Reverb;
import de.pottgames.tuningfork.SoundBuffer;
import de.pottgames.tuningfork.SoundEffect;
import de.pottgames.tuningfork.SoundListener;
import de.pottgames.tuningfork.StreamedSoundSource;
import de.pottgames.tuningfork.jukebox.JukeBox;
import de.pottgames.tuningfork.jukebox.playlist.PlayList;
import de.pottgames.tuningfork.jukebox.playlist.ThemePlayListProvider;
import de.pottgames.tuningfork.jukebox.song.Song;
import de.pottgames.tuningfork.jukebox.song.SongMeta;
import de.pottgames.tuningfork.jukebox.song.SongSettings;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

public class MusicManager implements Disposable, MusicManagerInterface {
	private static final String TAG = MusicManager.class.getSimpleName();
	private static MusicManagerInterface instance = null;

	private final Map<Integer, AudioData> audioDataForIds = new HashMap<>();
	private final Map<MusicTheme, PlayList> playlistsPerTheme = new EnumMap<>(MusicTheme.class);
	private final HashMap<String, StreamedSoundSource> songs = new HashMap<>();
	private final HashMap<String, Array<BufferedSoundSource>> loadedSounds = new HashMap<>();
	private final HashMap<Collidable, HashMap<String, Array<BufferedSoundSource>>> collidablesWithTheirSound = new HashMap<>();

	private final Audio audio;
	private final SoundListener listener;
	private final SoundEffect reverb;
	private ThemePlayListProvider provider;
	private JukeBox jukeBox;

	private MusicManager() {
		audio = initAudio();
		listener = audio.getListener();
		reverb = initReverb();
		jukeBox = initJukeBox();

		autoloadSounds();
		autoloadMusic();

		jukeBox.play();
		jukeBox.update();
	}

	private JukeBox initJukeBox() {
		provider = new ThemePlayListProvider();
		provider.setTheme(MusicTheme.PLAYING.ordinal());

		for (MusicTheme theme : MusicTheme.values()) {
			PlayList playlist = new PlayList();
			initPlaylist(playlist);
			playlistsPerTheme.put(theme, playlist);
			provider.add(playlist, theme.ordinal());
		}

		return new JukeBox(provider);
	}

	private void initPlaylist(PlayList playlist) {
		playlist.setLooping(true);
		playlist.setShuffleAfterPlaytrough(true);
		playlist.shuffle();
	}

	private Audio initAudio() {
		AudioConfig config = new AudioConfig();
		config.setDistanceAttenuationModel(DistanceAttenuationModel.LINEAR_DISTANCE);
		config.setLogger((MultiFileLogger) Gdx.app.getApplicationLogger());
		return Audio.init(config);
	}

	private SoundEffect initReverb() {
		Reverb reverbData = new Reverb();
		reverbData.gain = 0.7f;
		return new SoundEffect(reverbData);
	}

	private void autoloadSounds() {
		loadAudioData(AudioEnum::isSound, this::loadSoundBufferAsset);
	}

	private void loadSoundBufferAsset(String fullFilePath, AudioData audioData) {
		AssetManagerUtility.loadSoundBufferAsset(fullFilePath);
	}

	private void autoloadMusic() {
		loadAudioData(AudioEnum::isPlaylist, this::loadPlaylistSong);
	}

	private void loadPlaylistSong(String fullFilePath, AudioData audioData) {
		StreamedSoundSource music = new StreamedSoundSource(Gdx.files.internal(fullFilePath));
		music.setRelative(true);
		SongMeta meta = new SongMeta().setTitle(audioData.getName());
		SongSettings settings = SongSettings.linear(audioData.getVolume(), Constants.MUSIC_FADE_IN_DURATION, Constants.MUSIC_FADE_OUT_DURATION);
		Song song = new Song(music, settings, meta);
		PlayList playlist = playlistsPerTheme.computeIfAbsent(MusicTheme.values()[audioData.getTheme()], key -> new PlayList());
		playlist.addSong(song);
	}

	private void loadAudioData(Predicate<AudioEnum> filter, BiConsumer<String, AudioData> loader) {
		for (AudioEnum audioEnum : AudioEnum.values()) {
			if (filter.test(audioEnum)) {
				AudioData audioData = getAudioData(audioEnum);
				audioData.getAudioFileName().forEach(fullFilePath -> loader.accept(fullFilePath, audioData));
			}
		}
	}

	private AudioData getAudioData(AudioEnum event) {
		return audioDataForIds.computeIfAbsent(event.ordinal(), this::getAudioDataFromReader);
	}

	private AudioData getAudioDataFromReader(int ordinal) {
		return AudioFileReader.getAudioData().get(ordinal);
	}

	@Override
	public void update(float delta, float cameraX, float cameraY) {
		jukeBox.update();
		listener.setPosition(cameraX, cameraY, 0);
	}

	@Override
	public void sendCommand(AudioCommand command, AudioEnum event, Collidable collidable) {
		final AudioData audioData = getAudioData(event);
		final float volume = audioData.getVolume();

		switch (command) {
		case SOUND_PLAY_LOOP:
			collidablesWithTheirSound.putIfAbsent(collidable, new HashMap<>());
			collidablesWithTheirSound.get(collidable).putIfAbsent(audioData.getName(), new Array<>());
			collidablesWithTheirSound.get(collidable).get(audioData.getName()).add(playLoopedSound(audioData.getRandomAudioFileName(), volume, collidable.getPosition()));
			break;
		case SOUND_PLAY_ONCE:
			playAndForget(audioData.getRandomAudioFileName(), volume, collidable.getPosition());
			break;
		case SOUND_STOP:
			Array<BufferedSoundSource> sounds = collidablesWithTheirSound.get(collidable).get(audioData.getName());
			sounds.forEach(BufferedSoundSource::stop);
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
		case SOUND_PLAY_LOOP:
			playLoopedSound(audioData.getRandomAudioFileName(), volume, null);
			break;
		case SOUND_PLAY_ONCE:
			playAndForget(audioData.getRandomAudioFileName(), volume, null);
			break;
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
		case SOUND_STOP:
			stopAllSounds(audioData.getAudioFileName());
			break;
		default:
			break;
		}
	}

	private BufferedSoundSource playLoopedSound(String fullFilePath, float volume, Vector2 pos) {
		BufferedSoundSource sound = createBufferedSound(fullFilePath, volume);
		if (sound != null) {
			if (pos != null) {
				sound.setPosition(pos.x, pos.y, 0);
			}
			sound.play();
		}
		return sound;
	}

	private void playAndForget(String fullFilePath, float volume, Vector2 pos) {
		if (AssetManagerUtility.isAssetLoaded(fullFilePath)) {
			SoundBuffer soundBuffer = AssetManagerUtility.getSoundBufferAsset(fullFilePath);
			if (pos != null) {
				soundBuffer.play3D(volume, new Vector3(pos.x, pos.y, 0));
			} else {
				soundBuffer.play(volume);
			}
		}
	}

	private void stopAllSounds(List<String> audioFileNames) {
		audioFileNames.forEach(name -> {
			final Array<BufferedSoundSource> sounds = loadedSounds.get(name);
			if (sounds != null && sounds.size > 0) {
				sounds.get(0).stop();
				sounds.removeIndex(0);
			}
		});
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
