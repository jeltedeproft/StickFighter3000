package jelte.mygame;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
	private RECIPIENT recipient;
	private ACTION action;
	private int value;

	public enum ACTION {
		CAMERA_MOVE_X, CAMERA_MOVE_Y;
	}

	public enum RECIPIENT {
		CODE, GRAPHIC, INPUT
	}
}
