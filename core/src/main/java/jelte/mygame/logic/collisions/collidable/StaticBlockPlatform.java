package jelte.mygame.logic.collisions.collidable;

import com.badlogic.gdx.math.Rectangle;

public class StaticBlockPlatform extends StaticBlock {

	public StaticBlockPlatform(Rectangle rectangle) {
		super(rectangle);
	}

	public StaticBlockPlatform(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.STATIC_PLATFORM;
	}

}
