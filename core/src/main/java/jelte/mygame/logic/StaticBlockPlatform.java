package jelte.mygame.logic;

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
			pos.y += overlapY;
			body.getVelocity().y = 0;
		}

	}

}
