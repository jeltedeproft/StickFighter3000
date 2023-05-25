package jelte.mygame.logic.character.physics;

import java.util.Objects;
import java.util.UUID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.collisions.Collidable;
import jelte.mygame.utility.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardPhysicsComponent implements PhysicsComponent, Collidable {
	private static final String TAG = StandardPhysicsComponent.class.getSimpleName();
	private UUID playerReference;
	private Vector2 oldPosition;
	private Vector2 position;
	private Vector2 newPosition;
	private Vector2 velocity;
	private Vector2 acceleration;
	private Rectangle rectangle;
	private Direction direction;
	private boolean fallTrough;
	private boolean collided;
	private boolean hasMoved;
	private float width = Constants.PLAYER_WIDTH;
	private float height = Constants.PLAYER_HEIGHT;

	public StandardPhysicsComponent(UUID playerReference, Vector2 startPosition) {
		this.playerReference = playerReference;
		this.position = startPosition.cpy();
		this.oldPosition = startPosition.cpy();
		this.newPosition = new Vector2();
		this.velocity = new Vector2(0, 0);
		this.acceleration = new Vector2(0, 0);
		direction = Direction.right;
		rectangle = new Rectangle(position.x, position.y, width, height);// TODO dynamically change the size of thre player, idea : add size for every animation in character json
	}

	@Override
	public void update(float delta) {
		hasMoved = false;

		velocity.add(acceleration);
		velocity.add(Constants.GRAVITY);

		// Limit speed
		if (velocity.len2() > Constants.MAX_SPEED * Constants.MAX_SPEED) {
			velocity.setLength(Constants.MAX_SPEED);
		}

		newPosition.x = position.x + velocity.x * delta;
		newPosition.y = position.y + velocity.y * delta;

		// Clamp position to map bounds
//	    float minX = 0; // Minimum x position of the map
//	    float maxX = MAP_WIDTH; // Maximum x position of the map
//	    float minY = 0; // Minimum y position of the map
//	    float maxY = MAP_HEIGHT; // Maximum y position of the map

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
		Gdx.app.debug(TAG, "setting pos to : " + pos);
		if (pos.x < 0) {
			pos.x = 0;
		}
		if (pos.y < 0) {
			pos.y = 0;
		}
		if (!pos.equals(position)) {
			hasMoved = true;
			oldPosition.x = position.x;
			oldPosition.y = position.y;
			position.x = pos.x;
			position.y = pos.y;
			rectangle.x = pos.x;
			rectangle.y = pos.y;
		}
	}

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
		StandardPhysicsComponent other = (StandardPhysicsComponent) obj;
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

		sb.append("direction : ");
		sb.append(direction);
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
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.CHARACTER;
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

	@Override
	public void setDimensions(float width, float height) {
		this.width = width;
		this.height = height;
		rectangle.setWidth(width);
		rectangle.setHeight(height);
	}
}
