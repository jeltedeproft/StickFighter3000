package jelte.mygame.logic.physics;

import java.util.Objects;
import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.collisions.collidable.Collidable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PhysicsComponentImpl implements PhysicsComponent, Collidable {
	private static final String TAG = PhysicsComponentImpl.class.getSimpleName();
	protected UUID playerReference;
	protected Vector2 oldPosition;
	protected Vector2 position;
	protected Vector2 newPosition;
	protected Vector2 velocity;
	protected Vector2 acceleration;
	protected Rectangle rectangle;
	protected boolean collided;
	protected boolean hasMoved;
	protected float width;
	protected float height;

	protected PhysicsComponentImpl(UUID playerReference, Vector2 startPosition) {
		this.playerReference = playerReference;
		this.position = startPosition.cpy();
		this.oldPosition = startPosition.cpy();
		this.newPosition = new Vector2();
		this.velocity = new Vector2(0, 0);
		this.acceleration = new Vector2(0, 0);
		rectangle = new Rectangle(position.x, position.y, width, height);
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

	protected float clampXCoordinate(float x) {
		if (x < rectangle.width) {
			return rectangle.width;
		}
		return x;
	}

	protected float clampYCoordinate(float y) {
		if (y < rectangle.height) {
			return rectangle.height;
		}
		return y;
	}

	protected boolean shouldUpdatePosition(Vector2 pos, float newX, float newY) {
		return !pos.equals(position) || newX == 0 && newY == 0;
	}

	protected abstract void updatePosition(float newX, float newY);

	@Override
	public void move(float x, float y) {
		newPosition.x += x;
		newPosition.y += y;
		setPosition(newPosition);
	}

	@Override
	public int hashCode() {
		return Objects.hash(playerReference);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		PhysicsComponentImpl other = (PhysicsComponentImpl) obj;
		return Objects.equals(playerReference, other.playerReference);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("position : ");
		sb.append(position);
		sb.append("\n");

		sb.append("oldPosition : ");
		sb.append(oldPosition);
		sb.append("\n");

		sb.append("velocity : ");
		sb.append(velocity);
		sb.append("\n");

		sb.append("acceleration : ");
		sb.append(acceleration);
		sb.append("\n");

		sb.append("collided : ");
		sb.append(collided);
		sb.append("\n");

		sb.append("hasMoved : ");
		sb.append(hasMoved);
		sb.append("\n");

		return sb.toString();
	}

	@Override
	public UUID getId() {
		return playerReference;
	}

	@Override
	public Rectangle getRectangle() {
		return rectangle;
	}

	@Override
	public Vector2 getOldPosition() {
		return oldPosition;
	}

	@Override
	public boolean hasMoved() {
		return hasMoved;
	}

	@Override
	public boolean isStatic() {
		return false;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public void setDimensions(float width, float height) {
		if (this.width != width || this.height != height) {
			this.width = width;
			this.height = height;
			rectangle.setWidth(width);
			rectangle.setHeight(height);
		}
	}
}
