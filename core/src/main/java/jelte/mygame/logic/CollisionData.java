package jelte.mygame.logic;

import com.badlogic.gdx.utils.Array;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollisionData {
	Array<Collidable> dynamicCollidables;
	Array<Collidable> staticCollidables;

	public CollisionData(Array<Collidable> dynamicCollidables, Array<Collidable> staticCollidables) {
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
