package jelte.mygame.logic.collisions.collidable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public interface Collidable {

	public enum COLLIDABLE_TYPE {
		STATIC_TOP, STATIC_BOT, STATIC_LEFT, STATIC_RIGHT, STATIC_PLATFORM, PLAYER, ENEMY, SPELL, VISION, ITEM;

		public static boolean isStatic(COLLIDABLE_TYPE type) {
			return type == STATIC_TOP || type == STATIC_BOT || type == STATIC_LEFT || type == STATIC_RIGHT || type == STATIC_PLATFORM;
		}

		public static boolean isCharacter(COLLIDABLE_TYPE type) {
			return type == PLAYER || type == ENEMY;
		}

		public static boolean isPlayer(COLLIDABLE_TYPE type) {
			return type == PLAYER;
		}

		public static boolean isEnemy(COLLIDABLE_TYPE type) {
			return type == ENEMY;
		}

		public static boolean isSpell(COLLIDABLE_TYPE type) {
			return type == SPELL;
		}

		public static boolean isVision(COLLIDABLE_TYPE type) {
			return type == VISION;
		}

		public static boolean isItem(COLLIDABLE_TYPE type) {
			return type == ITEM;
		}
	}

	public UUID getId();

	public Vector2 getPosition();

	public void setPosition(Vector2 pos);

	public float getWidth();

	public float getHeight();

	public void setSize(float width, float height);

	public Rectangle getRectangle();

	public Rectangle getOldRectangle();

	public boolean hasMoved();

	public boolean isStatic();

	public boolean isDynamic();

	public void setCollided(boolean b);

	public boolean isCollided();

	public COLLIDABLE_TYPE getType();

	@Override
	public boolean equals(Object obj);
}
