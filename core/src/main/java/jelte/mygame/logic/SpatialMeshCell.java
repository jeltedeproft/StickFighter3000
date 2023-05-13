package jelte.mygame.logic;

import com.badlogic.gdx.utils.Array;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpatialMeshCell {
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
}
