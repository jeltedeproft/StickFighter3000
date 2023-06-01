package jelte.mygame.logic.collisions;

import java.util.Objects;

import jelte.mygame.logic.collisions.collidable.Collidable;

public class CollisionPair {
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
}
