package jelte.mygame.logic;

import com.badlogic.gdx.math.Rectangle;

import lombok.Getter;

@Getter
public class TypedRectangle extends Rectangle {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private BLOCKING_TYPE type;

	public enum BLOCKING_TYPE {
		GROUND, WALL, CEILING, PLATFORM
	}

	public TypedRectangle(Rectangle rectangle, String name) {
		super(rectangle);
		type = BLOCKING_TYPE.valueOf(name);
	}

	public TypedRectangle(int x, int y, int width, int height, BLOCKING_TYPE type) {
		super(x, y, width, height);
		this.type = type;
	}

}
