package jelte.mygame.logic.character.physics;

import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.Direction;

public interface PhysicsComponent {

	public void update(float delta);

	public Vector2 getPosition();

	public void setPosition(Vector2 pos);

	public Vector2 getVelocity();

	public void setVelocity(Vector2 velocity);

	public void setVelocityY(float y);

	public void setVelocityX(float x);

	public Vector2 getAcceleration();

	public void setAcceleration(Vector2 acceleration);

	public Rectangle getRectangle();

	public boolean isFallTrough();

	public boolean isCollided();

	public boolean isHasMoved();

	public void setFallTrough(boolean falltrough);

	public void setCollided(boolean b);

	public UUID getPlayerReference();

	@Override
	public boolean equals(Object obj);

	@Override
	public int hashCode();

	public Direction getDirection();

	public void setDirection(Direction direction);

	public Vector2 getOldPosition();

}
