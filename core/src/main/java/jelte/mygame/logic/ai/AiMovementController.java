package jelte.mygame.logic.ai;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.AiCharacter;

public interface AiMovementController {

	public void moveTowardsGoal(AiCharacter self, Vector2 goal);

}
