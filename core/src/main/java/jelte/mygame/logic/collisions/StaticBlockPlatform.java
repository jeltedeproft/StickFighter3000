package jelte.mygame.logic.collisions;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.physics.PhysicsComponent;

public class StaticBlockPlatform extends StaticBlock {

	public StaticBlockPlatform(Rectangle rectangle) {
		super(rectangle);
	}

	public StaticBlockPlatform(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	void handleCollision(PhysicsComponent body, Vector2 pos) {
		if (!body.isFallTrough()) {
			body.move(0, overlapY);
			body.getVelocity().y = 0;
		}
	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.STATIC_PLATFORM;
	}

}
