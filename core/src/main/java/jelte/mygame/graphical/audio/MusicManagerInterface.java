package jelte.mygame.graphical.audio;

import com.badlogic.gdx.math.Vector2;

public interface MusicManagerInterface {
	void update(float delta, float cameraX, float cameraY);

	void sendCommand(AudioCommand command, AudioEnum event, Vector2 pos);

	void sendCommand(AudioCommand command, AudioEnum event);
}
