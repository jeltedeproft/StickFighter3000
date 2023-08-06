package jelte.mygame.logic.collisions.collidable;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.Objects;
import java.util.UUID;

import lombok.Getter;

@Getter
public abstract class StaticBlock implements Comparable<StaticBlock>, Collidable {
	private boolean fallTrough = false;
	protected float overlapX;
	protected float overlapY;
	protected String name;
	protected UUID id;
	protected Rectangle rectangle;
	protected Vector2 position;
	protected boolean collided;

	protected StaticBlock(Rectangle rectangle) {
		this.rectangle = rectangle;
		position = rectangle.getPosition(new Vector2(0, 0));
		id = UUID.randomUUID();
	}

	protected StaticBlock(int x, int y, int width, int height) {
		this.rectangle = new Rectangle(x, y, width, height);
		position = rectangle.getPosition(new Vector2(0, 0));
		id = UUID.randomUUID();
	}

	public void calculateOverlapPlayer(Rectangle playerRect) {
		Rectangle intersection = new Rectangle();
		Intersector.intersectRectangles(rectangle, playerRect, intersection);
		overlapX = intersection.width;
		overlapY = intersection.height;
	}

	@Override
	public Rectangle getRectangle() {
		return rectangle;
	}

	@Override
	public Rectangle getOldRectangle() {
		return rectangle;
	}

	@Override
	public boolean hasMoved() {
		return false;
	}

	@Override
	public boolean isStatic() {
		return true;
	}

	@Override
	public boolean isDynamic() {
		return false;
	}

	@Override
	public Vector2 getPosition() {
		return rectangle.getPosition(position);
	}

	@Override
	public void setPosition(Vector2 pos) {
		rectangle.setPosition(pos);
	}

	@Override
	public float getWidth() {
		return rectangle.getWidth();
	}

	@Override
	public float getHeight() {
		return rectangle.getHeight();
	}

	@Override
	public void setSize(float width, float height) {
		rectangle.setSize(width, height);
	}

	@Override
	public void setCollided(boolean b) {
		collided = b;
	}

	@Override
	public boolean isCollided() {
		return collided;
	}

	@Override
	public int compareTo(StaticBlock o) {
		float totalOverlapObject = Math.abs(o.getOverlapX()) + Math.abs(o.getOverlapY());
		Float totalOverlapThis = Math.abs(overlapX) + Math.abs(overlapY);
		return Float.compare(totalOverlapObject, totalOverlapThis);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || getClass() != obj.getClass()) {
			return false;
		}
		StaticBlock other = (StaticBlock) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("pos = ");
		sb.append("(");
		sb.append(rectangle.x);
		sb.append(",");
		sb.append(rectangle.y);
		sb.append(")");
		sb.append("\n");
		sb.append("width = ");
		sb.append(rectangle.width);
		sb.append("\n");
		sb.append("height = ");
		sb.append(rectangle.height);
		sb.append("\n");
		sb.append("overlapX = ");
		sb.append(overlapX);
		sb.append("\n");
		sb.append("overlapY = ");
		sb.append(overlapY);
		sb.append("\n");
		return sb.toString();
	}

}
