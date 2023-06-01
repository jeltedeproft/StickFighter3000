package jelte.mygame.logic.collisions;

import java.util.Set;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.collisions.collidable.Collidable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollisionData {
	Set<Collidable> dynamicCollidables;
	Set<Collidable> staticCollidables;

	public CollisionData(Set<Collidable> dynamicCollidables, Set<Collidable> staticCollidables) {
		this.dynamicCollidables = dynamicCollidables;
		this.staticCollidables = staticCollidables;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Collidable coll : dynamicCollidables) {
			sb.append("dynamic : ");
			sb.append(coll);
			sb.append("\n");
		}
		for (Collidable coll : staticCollidables) {
			sb.append("static : ");
			sb.append(coll);
			sb.append("\n");
		}
		return sb.toString();
	}

}
