package jelte.mygame.logic.physics;

import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Direction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnemyPhysicsComponent extends CharacterPhysicsComponentImpl {
	private static final String TAG = EnemyPhysicsComponent.class.getSimpleName();
	private Rectangle visionRectangle;

	public EnemyPhysicsComponent(UUID playerReference, Vector2 startPosition, int visionWidth, int visionheight) {
		super(playerReference, startPosition);
		visionRectangle = new Rectangle(position.x, position.y, visionWidth, visionheight);
	}

	@Override
	protected void updatePosition(float newX, float newY) {
		super.updatePosition(newX, newY);
		visionRectangle.setPosition(newX, newY);

		if (direction == Direction.left) {
			visionRectangle.x -= visionRectangle.width;
		}
	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.ENEMY;
	}

}
