package jelte.mygame.logic.collisions.collidable;

import com.badlogic.gdx.math.Rectangle;

public class StaticBlockTop extends StaticBlock {

	public StaticBlockTop(Rectangle rectangle) {
		super(rectangle);
	}

	public StaticBlockTop(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.STATIC_TOP;
	}

}
