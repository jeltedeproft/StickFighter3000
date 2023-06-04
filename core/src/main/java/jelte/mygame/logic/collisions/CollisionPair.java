package jelte.mygame.logic.collisions;

import java.util.Objects;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.collisions.collidable.Collidable;

public class CollisionPair implements Comparable {
	private final Collidable collidable1;
	private final Collidable collidable2;

	public CollisionPair(Collidable collidable1, Collidable collidable2) {
		this.collidable1 = collidable1;
		this.collidable2 = collidable2;
	}

	public Collidable getCollidable1() {
		return collidable1;
	}

	public Collidable getCollidable2() {
		return collidable2;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CollisionPair that = (CollisionPair) o;
		return Objects.equals(collidable1, that.collidable1) && Objects.equals(collidable2, that.collidable2)
				|| Objects.equals(collidable1, that.collidable2) && Objects.equals(collidable2, that.collidable1);
	}

	@Override
	public int hashCode() {
		return Objects.hash(collidable1, collidable2) + Objects.hash(collidable2, collidable1);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("collidable 1 : ");
		sb.append(collidable1.getType());
		sb.append("\n");

		sb.append("collidable 2 : ");
		sb.append(collidable2.getType());
		sb.append("\n");

		return sb.toString();
	}

	@Override
	public int compareTo(Object o) {
		if (this == o) {
			return 0;
		}
		if (o == null || getClass() != o.getClass()) {
			throw new IllegalArgumentException("Cannot compare different types.");
		}
		CollisionPair other = (CollisionPair) o;

		// Compare collidable1 types lexicographically
		int typeComparison = collidable1.getType().compareTo(other.collidable1.getType());
		if (typeComparison != 0) {
			return typeComparison;
		}

		// If collidable1 types are equal, compare collidable2 types lexicographically
		return collidable2.getType().compareTo(other.collidable2.getType());
	}

}
