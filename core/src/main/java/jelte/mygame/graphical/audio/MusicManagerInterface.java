package jelte.mygame.graphical.audio;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.audio.MusicManager.AudioCommand;
import jelte.mygame.graphical.audio.MusicManager.AudioEnum;

public interface MusicManagerInterface {
	void update(float delta, float cameraX, float cameraY);

	void sendCommand(AudioCommand command, String string);

	void sendCommand(AudioCommand command, AudioEnum event, Vector2 pos);

	void sendCommand(AudioCommand command, AudioEnum event);
	// Add other methods from the MusicManager that are used by the CharacterStateManager
}
