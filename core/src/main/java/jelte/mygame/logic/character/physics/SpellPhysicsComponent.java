package jelte.mygame.logic.character.physics;

import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.utility.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpellPhysicsComponent extends PhysicsComponentImpl {
	private static final String TAG = SpellPhysicsComponent.class.getSimpleName();
	private Vector2 direction;

	public SpellPhysicsComponent(UUID playerReference, Vector2 startPosition) {
		super(playerReference, startPosition);
		direction = new Vector2(1, 0);
		rectangle = new Rectangle(position.x, position.y, width, height);
	}

	@Override
	public void update(float delta) {
		hasMoved = false;

		velocity.add(acceleration);

		if (velocity.len2() > Constants.MAX_SPEED * Constants.MAX_SPEED) {
			velocity.setLength(Constants.MAX_SPEED);
		}

		newPosition.x = position.x + velocity.x * delta;
		newPosition.y = position.y + velocity.y * delta;

		setPosition(newPosition);
	}

	@Override
	public void setVelocityY(float y) {
		velocity.y = y;
	}

	@Override
	public void setVelocityX(float x) {
		velocity.x = x;
	}

	@Override
	public void setPosition(Vector2 pos) {
		float newX = clampXCoordinate(pos.x);
		float newY = clampYCoordinate(pos.y);

		if (shouldUpdatePosition(pos, newX, newY)) {
			updatePosition(newX, newY);
		}
	}

	@Override
	protected void updatePosition(float newX, float newY) {
		hasMoved = true;
		oldPosition.set(position);
		position.set(newX, newY);
		rectangle.setPosition(newX, newY);

		if (direction.x < 0) {
			rectangle.x -= rectangle.width;
		}
	}

	@Override
	public void collided(COLLIDABLE_TYPE type) {
		collided = true;
		switch (type) {
		case CHARACTER:
			break;
		case SPELL:
			break;
		case STATIC:
			break;
		default:
			break;

		}
	}

}
