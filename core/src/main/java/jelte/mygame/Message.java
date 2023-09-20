package jelte.mygame;

import com.badlogic.gdx.utils.StringBuilder;

import java.util.Objects;

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
		CAMERA_ZOOM,
		RENDER_PLAYER,
		RENDER_ENEMY,
		RENDER_SPELLS,
		UPDATE_CAMERA_POS,
		SEND_MAP_DIMENSIONS,
		SEND_BLOCKING_OBJECTS,
		SEND_STAGE,
		SEND_MOUSE_COORDINATES,
		PLAYER_SEEN,
		IN_ATTACK_RANGE,
		OUT_ATTACK_RANGE,
		LOST_PLAYER,
		START_PATROLLING,
		IN_CAST_RANGE,
		OUT_CAST_RANGE,
		SPAWN_ENEMIES,
		ATTACKED_PLAYER,
		EXIT_GAME,
		ACTIVATE_SPELL,
		SEND_ITEMS,
		REMOVE_ITEM,
		SEND_BUTTONS_MAP,
		SWITCH_SCREEN;
	}

	public enum RECIPIENT {
		LOGIC, GRAPHIC, INPUT
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.action.name());
		sb.append(" for : ");
		sb.append(this.recipient);
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(action, recipient);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Message other = (Message) obj;
		return action == other.action && recipient == other.recipient;
	}

}
