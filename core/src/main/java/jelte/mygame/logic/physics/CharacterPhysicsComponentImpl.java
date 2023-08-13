package jelte.mygame.logic.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

import jelte.mygame.logic.character.Direction;
import jelte.mygame.utility.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CharacterPhysicsComponentImpl extends PhysicsComponentImpl {

	private static final String TAG = PlayerPhysicsComponent.class.getSimpleName();
	protected Direction direction;
	protected boolean fallTrough;
	protected boolean stopping;
	protected boolean starting;

	protected CharacterPhysicsComponentImpl(UUID playerReference, Vector2 startPosition) {
		super(playerReference, startPosition);
		direction = Direction.right;
		rectangle = new Rectangle(position.x, position.y, width, height);
	}

	@Override
	public void update(float delta) {
		moved = false;

		velocity.add(acceleration);
		velocity.add(Constants.GRAVITY);

		if (velocity.len2() > Constants.MAX_SPEED * Constants.MAX_SPEED) {
			velocity.setLength(Constants.MAX_SPEED);
		}

		if (starting) {
			velocity.x += direction == Direction.right ? Constants.STARTUP_SPEED : -Constants.STARTUP_SPEED;
			if (Math.abs(velocity.x) > Constants.VELOCITY_STARTUP_TRESHOLD) {
				starting = false;
			}
		}

		if (stopping) {
			acceleration.x = 0;
			velocity.x *= Constants.FRICTION;
			if (Math.abs(velocity.x) < Constants.VELOCITY_MIN_TRESHOLD) {
				velocity.x = 0;
				stopping = false;
			}
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
		moved = true;
		oldRectangle.set(rectangle);
		position.set(newX, newY);
		rectangle.setPosition(newX, newY);

		if (direction == Direction.left) {
			// rectangle.x -= rectangle.width;
		}
	}

	@Override
	public boolean goesTroughWalls() {
		return false;
	}

	@Override
	protected void onDimensionsUpdated() {
		// TODO Auto-generated method stub

	}

}
