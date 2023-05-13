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

}
