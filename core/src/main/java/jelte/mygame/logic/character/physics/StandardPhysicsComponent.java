package jelte.mygame.logic.character.physics;

import java.util.Objects;
import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.Direction;
import jelte.mygame.utility.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardPhysicsComponent implements PhysicsComponent {
	private UUID playerReference;
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;
	private Rectangle rectangle;
	private Direction direction;
	private boolean fallTrough;
	private boolean collided;

	public StandardPhysicsComponent(UUID playerReference) {
		this.playerReference = playerReference;
		this.position = Constants.PLAYER_START.cpy();
		this.velocity = new Vector2(0, 0);
		this.acceleration = new Vector2(0, 0);
		direction = Direction.right;
		rectangle = new Rectangle(position.x - Constants.PLAYER_WIDTH / 2, position.y - Constants.PLAYER_HEIGHT / 2, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);// TODO dynamically change the size of thre player, idea : add size for every animation in character json
	}

	@Override
	public void update(float delta) {
		velocity.add(acceleration);
		velocity.add(Constants.GRAVITY);

		// Limit speed
		if (velocity.len2() > Constants.MAX_SPEED * Constants.MAX_SPEED) {
			velocity.setLength(Constants.MAX_SPEED);
		}

		setPosition(position.add(velocity.cpy().scl(delta)));
	}

	@Override
	public void setPosition(Vector2 pos) {
		rectangle.x = pos.x - Constants.PLAYER_WIDTH / 2;
		rectangle.y = pos.y - Constants.PLAYER_HEIGHT / 2;
		this.position = pos;
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

}
