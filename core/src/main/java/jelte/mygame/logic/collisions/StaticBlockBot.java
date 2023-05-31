package jelte.mygame.logic.collisions;

import com.badlogic.gdx.math.Rectangle;

public class StaticBlockBot extends StaticBlock {

	public StaticBlockBot(Rectangle rectangle) {
		super(rectangle);
	}

	public StaticBlockBot(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.STATIC_BOT;
	}

}
