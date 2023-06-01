package jelte.mygame.logic.collisions;

import java.awt.Point;
import java.io.Serial;

public class CellPoint implements java.io.Serializable {
	int x;
	int y;

	/**
	 * Use serialVersionUID from JDK 1.1 for interoperability.
	 */
	@Serial
	private static final long serialVersionUID = -5276940640259749850L;

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
		Point point = new Point();
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
		if (obj instanceof CellPoint pt) {
			return x == pt.x && y == pt.y;
		}
		return super.equals(obj);
	}

	/**
	 * Returns the hashcode for this {@code Point2D}.
	 *
	 * @return a hash code for this {@code Point2D}.
	 */
	@Override
	public int hashCode() {
		long bits = java.lang.Double.doubleToLongBits(getX());
		bits ^= java.lang.Double.doubleToLongBits(getY()) * 31;
		return (int) bits ^ (int) (bits >> 32);
	}

	@Override
	public String toString() {
		return String.format("%s[x=%d,y=%d]", getClass().getName(), x, y);
	}

}
