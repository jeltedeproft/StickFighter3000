package jelte.mygame.logic.collisions.collidable;

import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface Collidable {

	public enum COLLIDABLE_TYPE {
		STATIC_TOP, STATIC_BOT, STATIC_LEFT, STATIC_RIGHT, STATIC_PLATFORM, CHARACTER, SPELL;

		public static boolean isStatic(COLLIDABLE_TYPE type) {
			return type == STATIC_TOP || type == STATIC_BOT || type == STATIC_LEFT || type == STATIC_RIGHT || type == STATIC_PLATFORM;
		}

		public static boolean isCharacter(COLLIDABLE_TYPE type) {
			return type == CHARACTER;
		}

		public static boolean isSpell(COLLIDABLE_TYPE type) {
			return type == SPELL;
		}
	}

	public UUID getId();

	public Rectangle getRectangle();

	public Vector2 getOldPosition();

	public boolean hasMoved();

	public boolean isStatic();

	public boolean isDynamic();

	public boolean goesTroughObjects();

	public COLLIDABLE_TYPE getType();
}
