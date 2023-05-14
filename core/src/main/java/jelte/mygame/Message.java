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
		CAMERA_ZOOM,
		RENDER_PLAYER,
		RENDER_ENEMY,
		UPDATE_CAMERA_POS,
		SEND_MAP_DIMENSIONS,
		SEND_BLOCKING_OBJECTS,
		SEND_STAGE,
		ATTACK_PRESSED,
		DASH_PRESSED,
		ROLL_PRESSED,
		TELEPORT_PRESSED,
		BLOCK_PRESSED,
		BLOCK_UNPRESSED,
		SPRINT_PRESSED,
		SPRINT_UNPRESSED;
	}

	public enum RECIPIENT {
		LOGIC, GRAPHIC, INPUT
	}

	@Override
	public String toString() {
		return this.action.name() + "for : " + this.recipient;
	}
}
