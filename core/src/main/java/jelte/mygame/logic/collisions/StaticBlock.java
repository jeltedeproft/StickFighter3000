package jelte.mygame.logic.collisions;

import java.util.Objects;
import java.util.UUID;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.physics.PhysicsComponent;
import lombok.Getter;

@Getter
public abstract class StaticBlock extends Rectangle implements Comparable<StaticBlock>, Collidable {
	private static final long serialVersionUID = 1L;
	private boolean fallTrough = false;
	protected float overlapX;
	protected float overlapY;
	private String name;
	private UUID id;
	protected boolean contains = false;

	protected StaticBlock(Rectangle rectangle) {
		super(rectangle);
		id = UUID.randomUUID();
	}

	protected StaticBlock(int x, int y, int width, int height) {
		super(x, y, width, height);
		id = UUID.randomUUID();
	}

	abstract void handleCollision(PhysicsComponent body, Vector2 pos);

	public void calculateOverlapPlayer(Rectangle playerRect) {
		Rectangle intersection = new Rectangle();
		Intersector.intersectRectangles(this, playerRect, intersection);
		overlapX = intersection.width;
		overlapY = intersection.height;
	}

	public boolean areRectanglesOverlapping(Rectangle rect1, Rectangle rect2) {
		if (rect1.overlaps(rect2)) {
			return true; // Rectangles overlap
		}

		// Check for shared edges
		boolean sharedEdgeX = rect1.x == rect2.x + rect2.width || rect2.x == rect1.x + rect1.width;
		boolean sharedEdgeY = rect1.y == rect2.y + rect2.height || rect2.y == rect1.y + rect1.height;

		// Check for containment
		boolean rect1ContainsRect2 = rect1.contains(rect2.x, rect2.y) && rect1.contains(rect2.x + rect2.width, rect2.y + rect2.height);
		boolean rect2ContainsRect1 = rect2.contains(rect1.x, rect1.y) && rect2.contains(rect1.x + rect1.width, rect1.y + rect1.height);

		// Determine if rectangles are overlapping
		if (sharedEdgeX && rect1ContainsRect2 || sharedEdgeY && rect2ContainsRect1) {
			return true; // Rectangles overlap
		}

		return false; // Rectangles do not overlap
	}

	@Override
	public Rectangle getRectangle() {
		return this;
	}

	@Override
	public Vector2 getOldPosition() {
		return new Vector2(this.x, this.y);
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
		sb.append("(" + this.x + "," + this.y + ")");
		sb.append("\n");
		sb.append("width = ");
		sb.append(width);
		sb.append("\n");
		sb.append("height = ");
		sb.append(height);
		sb.append("\n");
		sb.append("overlapX = ");
		sb.append(overlapX);
		sb.append("\n");
		sb.append("overlapY = ");
		sb.append(overlapY);
		sb.append("\n");
		return sb.toString();
	}

	public void contains() {
		contains = true;
	}

}
