package jelte.mygame.logic.collisions;

import com.badlogic.gdx.math.Rectangle;

public class StaticBlockLeft extends StaticBlock {

	public StaticBlockLeft(Rectangle rectangle) {
		super(rectangle);
	}

	public StaticBlockLeft(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.STATIC_LEFT;
	}

}
