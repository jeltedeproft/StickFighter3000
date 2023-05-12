package jelte.mygame.logic;

import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;

public interface Collidable {

	public UUID getId();

	public Rectangle getRectangle();
}
