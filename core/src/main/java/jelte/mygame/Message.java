package jelte.mygame;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message {
	private RECIPIENT recipient;
	private ACTION action;
	private Object value;

	public Message(RECIPIENT recipient, ACTION action) {
		this.recipient = recipient;
		this.action = action;
	}

	public enum ACTION {
		LEFT_PRESSED,
		RIGHT_PRESSED,
		UP_PRESSED,
		DOWN_PRESSED,
		LEFT_UNPRESSED,
		RIGHT_UNPRESSED,
		DOWN_UNPRESSED,
		CAMERA_LEFT,
		CAMERA_RIGHT,
		CAMERA_UP,
		CAMERA_DOWN,
		CAMERA_LEFT_UNPRESSED,
		CAMERA_RIGHT_UNPRESSED,
		CAMERA_UP_UNPRESSED,
		CAMERA_DOWN_UNPRESSED,
		CAMERA_ZOOM,
		RENDER_WORLD,
		UPDATE_CAMERA;
	}

	public enum RECIPIENT {
		LOGIC, GRAPHIC, INPUT
	}
}
