package jelte.mygame.logic.collisions;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.physics.PhysicsComponent;

public class StaticBlockTop extends StaticBlock {

	public StaticBlockTop(Rectangle rectangle) {
		super(rectangle);
	}

	public StaticBlockTop(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	void handleCollision(PhysicsComponent body, Vector2 pos) {
		if (contains) {
			contains = false;
			body.move(0, -height + (body.getPosition().y - y));
		} else {
			body.move(0, -overlapY);
		}
		body.getVelocity().y = 0;
		body.getAcceleration().y = 0;
	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.STATIC_TOP;
	}

}
