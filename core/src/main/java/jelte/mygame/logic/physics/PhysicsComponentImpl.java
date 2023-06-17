package jelte.mygame.logic.physics;

import java.util.Objects;
import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.collisions.collidable.Collidable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PhysicsComponentImpl implements PhysicsComponent, Collidable {
	private static final String TAG = PhysicsComponentImpl.class.getSimpleName();
	protected UUID ownerReference;
	protected Rectangle oldRectangle;
	protected Vector2 position;
	protected Vector2 newPosition;
	protected Vector2 velocity;
	protected Vector2 acceleration;
	protected Rectangle rectangle;
	protected boolean collided;
	protected Array<COLLIDABLE_TYPE> collidedWith;
	protected boolean hasMoved;
	protected boolean fallTrough;
	protected float width;
	protected float height;

	protected PhysicsComponentImpl(UUID playerReference, Vector2 startPosition) {
		collidedWith = new Array<>();
		this.ownerReference = playerReference;
		this.position = startPosition.cpy();
		this.newPosition = new Vector2();
		this.velocity = new Vector2(0, 0);
		this.acceleration = new Vector2(0, 0);
		rectangle = new Rectangle(position.x, position.y, width, height);
		this.oldRectangle = new Rectangle(rectangle);
	}

	protected abstract void onDimensionsUpdated();

	@Override
	public void setVelocityY(float y) {
		velocity.y = y;
	}

	public void setCollided(boolean collided, COLLIDABLE_TYPE type) {
		this.collided = collided;
		collidedWith.add(type);
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
		if (x < 0) {
			return 0;
		}
		return x;
	}

	protected float clampYCoordinate(float y) {
		if (y < 0) {
			return 0;
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
		return Objects.hash(ownerReference);
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
		return Objects.equals(ownerReference, other.ownerReference);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("id : ");
		sb.append(ownerReference);
		sb.append("\n");

		sb.append("position : ");
		sb.append(position);
		sb.append("\n");

		sb.append("oldrectangle : ");
		sb.append(oldRectangle);
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
		return ownerReference;
	}

	@Override
	public Rectangle getRectangle() {
		return rectangle;
	}

	@Override
	public Rectangle getOldRectangle() {
		return oldRectangle;
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
			onDimensionsUpdated();
		}
	}
}
