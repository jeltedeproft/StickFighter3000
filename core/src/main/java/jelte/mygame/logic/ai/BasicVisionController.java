package jelte.mygame.logic.ai;

import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.physics.EnemyPhysicsComponent;

public class BasicVisionController implements AiVisionController {
	private static final String TAG = BasicVisionController.class.getSimpleName();

	@Override
	public boolean checkPlayerVisibility(PlayerCharacter player) {
		EnemyPhysicsComponent physics = (EnemyPhysicsComponent) physicsComponent;
		return physics.getVisionRectangle().overlaps(player.getPhysicsComponent().getRectangle());
	}

}
