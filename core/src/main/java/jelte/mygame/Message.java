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
	private int value;

	public Message(RECIPIENT recipient, ACTION action) {
		this.recipient = recipient;
		this.action = action;
	}

	public enum ACTION {
		CAMERA_MOVE_LEFT, CAMERA_MOVE_RIGHT, HERO_JUMP, HERO_DUCK;
	}

	public enum RECIPIENT {
		CODE, GRAPHIC, INPUT
	}
}
