package jelte.mygame.logic;

import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface Collidable {

	public enum COLLIDABLE_TYPE {
		STATIC_TOP, STATIC_BOT, STATIC_LEFT, STATIC_RIGHT, STATIC_PLATFORM, CHARACTER, SPELL;
	}

	public UUID getId();

	public Rectangle getRectangle();

	public Vector2 getOldPosition();

	public boolean hasMoved();

	public boolean isStatic();

	public boolean isDynamic();

	public COLLIDABLE_TYPE getType();
}
