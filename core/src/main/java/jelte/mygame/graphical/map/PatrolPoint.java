package jelte.mygame.graphical.map;

import com.badlogic.gdx.math.Vector2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatrolPoint {

	private String pathIndex;
	private Vector2 position;

	public PatrolPoint(Vector2 position, String pathIndex) {
		this.position = position;
		this.pathIndex = pathIndex;
	}

}
