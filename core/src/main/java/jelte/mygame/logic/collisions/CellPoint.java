package jelte.mygame.logic.collisions;

import java.awt.Point;

public class CellPoint {
	int x;
	int y;

	public CellPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public CellPoint() {
		this(0, 0);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void translate(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point pt) {
			return x == pt.x && y == pt.y;
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return String.format("%s[x=%d,y=%d]", getClass().getName(), x, y);
	}

}
