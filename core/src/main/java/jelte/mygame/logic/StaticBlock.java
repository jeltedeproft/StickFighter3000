package jelte.mygame.logic;

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

	@Override
	public Rectangle getRectangle() {
		return this;
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
		if (!super.equals(obj) || (getClass() != obj.getClass())) {
			return false;
		}
		StaticBlock other = (StaticBlock) obj;
		return Objects.equals(id, other.id);
	}

}
