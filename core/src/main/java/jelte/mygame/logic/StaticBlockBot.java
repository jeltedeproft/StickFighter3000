package jelte.mygame.logic;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.physics.PhysicsComponent;

public class StaticBlockBot extends StaticBlock {

	public StaticBlockBot(Rectangle rectangle) {
		super(rectangle);
	}

	public StaticBlockBot(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	void handleCollision(PhysicsComponent body, Vector2 pos) {
		pos.y += overlapY;
		body.getVelocity().y = 0;
	}

}
