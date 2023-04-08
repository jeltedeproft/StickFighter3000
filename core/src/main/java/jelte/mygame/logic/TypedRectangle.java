package jelte.mygame.logic;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import jelte.mygame.utility.Constants;
import lombok.Getter;

@Getter
public class TypedRectangle extends Rectangle implements Comparable<TypedRectangle> {
	private static final long serialVersionUID = 1L;
	private boolean blocksTop = false;
	private boolean blocksBot = false;
	private boolean blocksLeft = false;
	private boolean blocksRight = false;
	private boolean fallTrough = false;
	private float overlapX;
	private float overlapY;
	private String name;

	public TypedRectangle(Rectangle rectangle, String name) {
		super(rectangle);
		setBooleans(name);
	}

	private void setBooleans(String name) {
		switch (name) {
		case Constants.BLOCK_TYPE_TOP:
			blocksTop = true;
			break;
		case Constants.BLOCK_TYPE_BOT:
			blocksBot = true;
			break;
		case Constants.BLOCK_TYPE_LEFT:
			blocksLeft = true;
			break;
		case Constants.BLOCK_TYPE_RIGHT:
			blocksRight = true;
			break;
		case Constants.BLOCK_TYPE_PLATFORM:
			fallTrough = true;
			break;
		default:
			break;
		}
		this.name = name;
	}

	public TypedRectangle(int x, int y, int width, int height, String name) {
		super(x, y, width, height);
		setBooleans(name);
	}

	@Override
	public int compareTo(TypedRectangle o) {
		float totalOverlapObject = Math.abs(o.getOverlapX()) + Math.abs(o.getOverlapY());
		Float totalOverlapThis = Math.abs(overlapX) + Math.abs(overlapY);
		return Float.compare(totalOverlapObject, totalOverlapThis);
	}

	public void setCollisionData(Rectangle playerRect) {
		Rectangle intersection = new Rectangle();
		Intersector.intersectRectangles(this, playerRect, intersection);
		overlapX = intersection.width;
		overlapY = intersection.height;
	}

}
