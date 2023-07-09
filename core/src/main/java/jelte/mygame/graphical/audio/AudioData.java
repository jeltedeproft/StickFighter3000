package jelte.mygame.graphical.audio;

import java.util.ArrayList;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.utility.UtilityFunctions;
import lombok.Data;

@Data
public class AudioData {
	private int id;
	private String name;
	private ArrayList<String> audioFileName;
	private float volume;
	private float duration;
	private float cooldown;
	private int theme;

	public String getRandomAudioFileName() {
		return audioFileName.get(UtilityFunctions.randomNumberFromTo(0, audioFileName.size()));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name: ");
		sb.append(name);
		sb.append(System.lineSeparator());
		sb.append("theme: ");
		sb.append(theme);
		return sb.toString();
	}
}
