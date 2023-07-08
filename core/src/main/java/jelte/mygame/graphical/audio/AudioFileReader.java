package jelte.mygame.graphical.audio;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class AudioFileReader {
	private static boolean audioLoaded = false;
	private static ObjectMap<Integer, AudioData> audioData = new ObjectMap<>();

	private AudioFileReader() {

	}

	@SuppressWarnings("unchecked")
	public static void loadAudioInMemory(final String fileLocation) {
		if (!audioLoaded) {
			final Json json = new Json();
			ArrayList<AudioData> audioFiles;
			try {
				audioFiles = (ArrayList<AudioData>) json.fromJson(ClassReflection.forName("java.util.ArrayList"), ClassReflection.forName("jelte.mygame.graphical.audio.AudioData"), Gdx.files.internal(fileLocation));
				for (int i = 0; i < audioFiles.size(); i++) {
					final AudioData data = audioFiles.get(i);
					audioData.put(data.getId(), data);
				}
				audioLoaded = true;
			} catch (final ReflectionException e) {
				e.printStackTrace();
			}
		}
	}

	public static ObjectMap<Integer, AudioData> getAudioData() {
		return audioData;
	}
}
