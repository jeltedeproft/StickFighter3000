package jelte.mygame.logic.collisions;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.physics.PhysicsComponent;

public class StaticBlockLeft extends StaticBlock {

	public StaticBlockLeft(Rectangle rectangle) {
		super(rectangle);
	}

	public StaticBlockLeft(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	void handleCollision(PhysicsComponent body, Vector2 pos) {
		if (contains) {
			contains = false;
			body.move(width - (body.getPosition().x - x), 0);
		} else {
			body.move(overlapX, 0);
			body.getVelocity().x = 0;
		}

	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.STATIC_LEFT;
	}

}
