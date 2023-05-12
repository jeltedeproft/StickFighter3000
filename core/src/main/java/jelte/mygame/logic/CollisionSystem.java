package jelte.mygame.logic;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;

public interface CollisionSystem {

	public void setBlockingRectangles(Array<StaticBlock> blockingRectangles);

	public void updateCollisions(Array<Character> characters);

}
