package jelte.mygame.graphical.audio;

import java.util.ArrayList;

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

	public String getRandomAudioFileName() {
		return audioFileName.get(UtilityFunctions.randomNumberFromTo(0, audioFileName.size()));
	}
}
