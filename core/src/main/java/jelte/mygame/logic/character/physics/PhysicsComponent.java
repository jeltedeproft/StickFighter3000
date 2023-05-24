package jelte.mygame.logic.character.physics;

import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.collisions.Collidable;

public interface PhysicsComponent extends Collidable {

	public void update(float delta);

	public Vector2 getPosition();

	public void setPosition(Vector2 pos);

	public Vector2 getVelocity();

	public void setVelocity(Vector2 velocity);

	public void setVelocityY(float y);

	public void setVelocityX(float x);

	public Vector2 getAcceleration();

	public void setAcceleration(Vector2 acceleration);

	@Override
	public Rectangle getRectangle();

	public boolean isFallTrough();

	public boolean isCollided();

	public boolean isHasMoved();

	public void setFallTrough(boolean falltrough);

	public void setCollided(boolean b);

	public void setDimensions(float width, float height);

	public float getWidth();

	public float getHeight();

	public UUID getPlayerReference();

	@Override
	public boolean equals(Object obj);

	@Override
	public int hashCode();

	public Direction getDirection();

	public void setDirection(Direction direction);

	@Override
	public Vector2 getOldPosition();

	public void move(float x, float y);

	public void collided(COLLIDABLE_TYPE type);

}
