package jelte.mygame.logic.collisions.collidable;

import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;

public interface Collidable {

	public enum COLLIDABLE_TYPE {
		STATIC_TOP, STATIC_BOT, STATIC_LEFT, STATIC_RIGHT, STATIC_PLATFORM, PLAYER, ENEMY, SPELL, VISION;

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
	}

	public UUID getId();

	public Rectangle getRectangle();

	public Rectangle getOldRectangle();

	public boolean hasMoved();

	public boolean isStatic();

	public boolean isDynamic();

	public boolean goesTroughObjects();

	public COLLIDABLE_TYPE getType();

	@Override
	public boolean equals(Object obj);
}
