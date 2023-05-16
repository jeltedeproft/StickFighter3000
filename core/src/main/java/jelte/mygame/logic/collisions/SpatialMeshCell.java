package jelte.mygame.logic.collisions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpatialMeshCell {
	private static final String TAG = SpatialMeshCell.class.getSimpleName();
	private Array<Collidable> staticCollidables;
	private Array<Collidable> dynamicCollidables;
	private boolean containsStatic;
	private boolean containsDynamic;

	public SpatialMeshCell() {
		staticCollidables = new Array<>();
		dynamicCollidables = new Array<>();
		containsStatic = false;
		containsDynamic = false;
	}

	public void addCollidable(Collidable collidable) {
		if (collidable.isStatic()) {
			staticCollidables.add(collidable);
			containsStatic = true;
		}

		if (collidable.isDynamic()) {
			Gdx.app.debug(TAG, "adding dynamic collidable : )" + collidable);
			dynamicCollidables.add(collidable);
			containsDynamic = true;
		}
	}

	public void removeCollidable(Collidable collidable) {
		if (collidable.isStatic()) {
			staticCollidables.removeValue(collidable, false);
			if (staticCollidables.isEmpty()) {
				containsStatic = false;
			}
		}

		if (collidable.isDynamic()) {
			dynamicCollidables.removeValue(collidable, false);
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
