package jelte.mygame.graphical.audio;

import jelte.mygame.logic.collisions.collidable.Collidable;

public interface MusicManagerInterface {
	void update(float delta, float cameraX, float cameraY);

	void sendCommand(AudioCommand command, AudioEnum event, Collidable collidable);

	void sendCommand(AudioCommand command, AudioEnum event);

}
