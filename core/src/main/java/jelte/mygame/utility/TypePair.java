package jelte.mygame.utility;

import java.util.Objects;

public class TypePair<COLLIDABLE_TYPE> {
	private final COLLIDABLE_TYPE first;
	private final COLLIDABLE_TYPE second;

	public TypePair(COLLIDABLE_TYPE first, COLLIDABLE_TYPE second) {
		this.first = first;
		this.second = second;
	}

	public COLLIDABLE_TYPE getFirst() {
		return first;
	}

	public COLLIDABLE_TYPE getSecond() {
		return second;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TypePair that = (TypePair) o;
		return Objects.equals(first, that.first) && Objects.equals(second, that.second)
				|| Objects.equals(first, that.second) && Objects.equals(second, that.first);
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, second) + Objects.hash(second, first);
	}
}
