package jelte.mygame.logic.physics;

import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.collisions.collidable.Collidable;

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

	public boolean isCollided();

	public boolean isHasMoved();

	public void setCollided(boolean b);

	public void setDimensions(float width, float height);

	public float getWidth();

	public float getHeight();

	public UUID getOwnerReference();

	@Override
	public boolean equals(Object obj);

	@Override
	public int hashCode();

	@Override
	public Vector2 getOldPosition();

	public void move(float x, float y);

}
