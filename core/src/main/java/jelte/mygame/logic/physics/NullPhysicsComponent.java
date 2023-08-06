package jelte.mygame.logic.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

import jelte.mygame.logic.spells.SpellsEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NullPhysicsComponent implements PhysicsComponent {
	private static final NullPhysicsComponent INSTANCE = new NullPhysicsComponent();
	private static final String TAG = NullPhysicsComponent.class.getSimpleName();
	private Vector2 direction;
	private boolean goesTroughObjects;
	private SpellsEnum spellsEnum;

	private NullPhysicsComponent() {
	}

	public static NullPhysicsComponent getInstance() {
		return INSTANCE;
	}

	@Override
	public Vector2 getPosition() {
		return Vector2.Zero;
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void setVelocityY(float y) {

	}

	@Override
	public void setVelocityX(float x) {

	}

	@Override
	public void setPosition(Vector2 pos) {

	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.SPELL;
	}

	@Override
	public boolean goesTroughWalls() {
		return goesTroughObjects;
	}

	@Override
	public boolean hasMoved() {
		return false;
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
		return Rectangle.tmp;
	}

	@Override
	public Rectangle getOldRectangle() {
		return Rectangle.tmp;
	}

	@Override
	public UUID getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector2 getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVelocity(Vector2 velocity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector2 getAcceleration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAcceleration(Vector2 acceleration) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCollided() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCollided(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSize(float width, float height) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UUID getOwnerReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void move(float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isOnGround() {
		return false;
	}

}
