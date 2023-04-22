package jelte.mygame.logic;

import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.Constants;

public class CollisionSystem {
	private Array<TypedRectangle> blockingRectangles = new Array<>();

	public void updateCollisions(float delta, Map<Character, Vector2> futurePositions, Array<Character> enemies) {

		updateCollisionsStaticObjects(futurePositions);
		updateCollisionsDynamicObjects(enemies);
	}

	private void updateCollisionsDynamicObjects(Array<Character> enemies) {
		for (Character enemy : enemies) {
			if (enemy.get) {

			}
		}

	}

	private void updateCollisionsStaticObjects(Map<Character, Vector2> futurePositions) {
		for (Entry<Character, Vector2> characterWithFuturePosition : futurePositions.entrySet()) {
			Character character = characterWithFuturePosition.getKey();
			Vector2 futurePlayerPos = characterWithFuturePosition.getValue();

			Rectangle playerRect = new Rectangle(futurePlayerPos.x, futurePlayerPos.y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);// TODO dynamically change the size of thre player, idea : add size for every animation in character json
			Array<TypedRectangle> overlappingObstacles = getOverlappingObstacles(playerRect);

			if (!overlappingObstacles.isEmpty()) {
				handleCollision(character, futurePlayerPos, overlappingObstacles, playerRect);
			} else {
				character.getCurrentCharacterState().handleEvent(EVENT.NO_COLLISION);
			}

			character.setPositionVector(futurePlayerPos);
		}
	}

	private Array<TypedRectangle> getOverlappingObstacles(Rectangle playerRect) {
		Array<TypedRectangle> overlappingObstacles = new Array<>();

		for (TypedRectangle obstacle : blockingRectangles) {
			if (obstacle.overlaps(playerRect)) {
				obstacle.setCollisionData(playerRect);
				overlappingObstacles.add(obstacle);
			}
		}

		return overlappingObstacles;
	}

	private void handleCollision(Character character, Vector2 futurePlayerPos, Array<TypedRectangle> overlappingObstacles, Rectangle playerRect) {
		overlappingObstacles.sort();

		TypedRectangle obstacle = overlappingObstacles.first();

		if (obstacle.isBlocksTop()) {
			futurePlayerPos.y -= obstacle.getOverlapY();
			character.getMovementVector().y = 0;
		}

		if (obstacle.isBlocksBot()) {
			futurePlayerPos.y += obstacle.getOverlapY();
			character.getMovementVector().y = 0;
		}

		if (obstacle.isBlocksLeft()) {
			futurePlayerPos.x += obstacle.getOverlapX();
			character.getMovementVector().y = 0;
		}

		if (obstacle.isBlocksRight()) {
			futurePlayerPos.x -= obstacle.getOverlapX();
			character.getMovementVector().y = 0;
		}

		if (obstacle.isFallTrough() && character.getCurrentCharacterState().getState() != STATE.CROUCHED) {
			futurePlayerPos.y += obstacle.getOverlapY();
			character.getMovementVector().y = 0;
		}

	}

	public void setBlockingRectangles(Array<TypedRectangle> blockingRectangles) {
		this.blockingRectangles = blockingRectangles;
	}
}