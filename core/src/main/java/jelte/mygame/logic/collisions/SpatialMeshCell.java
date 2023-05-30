package jelte.mygame.logic.collisions;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.utils.StringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpatialMeshCell {
	private static final String TAG = SpatialMeshCell.class.getSimpleName();
	private Set<Collidable> staticCollidables;
	private Set<Collidable> dynamicCollidables;
	private boolean containsStatic;
	private boolean containsDynamic;

	public SpatialMeshCell() {
		staticCollidables = new HashSet<>();
		dynamicCollidables = new HashSet<>();
		containsStatic = false;
		containsDynamic = false;
	}

	public void addCollidable(Collidable collidable) {
		if (collidable.isStatic()) {
			staticCollidables.add(collidable);
			containsStatic = true;
		}

		if (collidable.isDynamic()) {
			dynamicCollidables.add(collidable);
			containsDynamic = true;
		}
	}

	public void removeAll() {
		staticCollidables.clear();
		dynamicCollidables.clear();
	}

	public void removeCollidable(Collidable collidable) {
		if (collidable.isStatic()) {
			staticCollidables.remove(collidable);
			if (staticCollidables.isEmpty()) {
				containsStatic = false;
			}
		}

		if (collidable.isDynamic()) {
			dynamicCollidables.remove(collidable);
			if (dynamicCollidables.isEmpty()) {
				containsDynamic = false;
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("contains static : ");
		sb.append(containsStatic);
		sb.append("\n");
		sb.append("contains dynamic : ");
		sb.append(containsDynamic);
		sb.append("\n");
		for (Collidable coll : staticCollidables) {
			sb.append("static : ");
			sb.append("\n");
			sb.append(coll);
		}
		for (Collidable coll : dynamicCollidables) {
			sb.append("dynamic : ");
			sb.append("\n");
			sb.append(coll);
		}
		return sb.toString();
	}
}
