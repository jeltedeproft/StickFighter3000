package jelte.mygame.logic.character.physics;

import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.spells.SpellsEnum;
import jelte.mygame.utility.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterPhysicsComponent extends PhysicsComponentImpl {
	private static final String TAG = CharacterPhysicsComponent.class.getSimpleName();
	private Direction direction;
	private boolean fallTrough;
	private Queue<SpellsEnum> hitBy;

	public CharacterPhysicsComponent(UUID playerReference, Vector2 startPosition) {
		super(playerReference, startPosition);
		direction = Direction.right;
		rectangle = new Rectangle(position.x, position.y, width, height);
	}

	@Override
	public void update(float delta) {
		hasMoved = false;

		velocity.add(acceleration);
		velocity.add(Constants.GRAVITY);

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

		if (direction == Direction.left) {
			rectangle.x -= rectangle.width;
		}
	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.CHARACTER;
	}

	@Override
	public boolean goesTroughObjects() {
		return false;
	}

	public void hitBySpell(SpellsEnum spellsEnum) {
		hitBy.addLast(spellsEnum);
	}

}
