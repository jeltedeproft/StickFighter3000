package jelte.mygame.logic.physics;

import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.spells.SpellsEnum;
import jelte.mygame.utility.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpellPhysicsComponent extends PhysicsComponentImpl {
	private static final String TAG = SpellPhysicsComponent.class.getSimpleName();
	private Vector2 direction;
	private boolean goesTroughObjects;
	private SpellsEnum spellsEnum;

	public SpellPhysicsComponent(UUID playerReference, SpellsEnum spellsEnum, Vector2 startPosition, boolean goesTroughObjects) {
		super(playerReference, startPosition);
		this.goesTroughObjects = goesTroughObjects;
		this.spellsEnum = spellsEnum;
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

		System.out.println("direction in update = " + direction);
		if (direction.x < 0) {
			rectangle.x -= rectangle.width;
		}
	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.SPELL;
	}

	@Override
	public boolean goesTroughObjects() {
		return goesTroughObjects;
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
	public Rectangle getRectangle() {
		return rectangle;
	}

	@Override
	public Vector2 getOldPosition() {
		return oldPosition;
	}

}
