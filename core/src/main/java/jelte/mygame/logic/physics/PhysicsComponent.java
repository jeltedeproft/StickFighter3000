package jelte.mygame.logic.physics;

import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

import jelte.mygame.logic.collisions.collidable.Collidable;

public interface PhysicsComponent extends Collidable {

	public UUID getOwnerReference();

	public void update(float delta);

	public void move(float x, float y);

	public Vector2 getVelocity();

	public void setVelocity(Vector2 velocity);

	public void setVelocityY(float y);

	public void setVelocityX(float x);

	public Vector2 getAcceleration();

	public void setAcceleration(Vector2 acceleration);

	public boolean isOnGround();

	public boolean isMoved();

	public boolean goesTroughWalls();

}
