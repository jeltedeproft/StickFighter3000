package jelte.mygame.logic;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.Constants;

public class MovementSystem {
	private Array<TypedRectangle> blockingRectangles = new Array<>();

	public void update(float delta, Character player) {
		Vector2 position = player.getPositionVector();
		Vector2 velocity = player.getMovementVector();
		Vector2 acceleration = player.getAccelerationVector();

		velocity.add(acceleration);
		velocity.add(Constants.GRAVITY);

		// Limit speed
		if (velocity.len2() > Constants.MAX_SPEED * Constants.MAX_SPEED) {
			velocity.setLength(Constants.MAX_SPEED);
		}

		Vector2 futurePlayerPos = position.cpy().add(velocity.cpy().scl(delta));
		Rectangle playerRect = new Rectangle(futurePlayerPos.x, futurePlayerPos.y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);// TODO dynamically change the size of thre player, idea : add size for every animation in character json

		Array<TypedRectangle> overlappingObstacles = getOverlappingObstacles(playerRect);

		if (!overlappingObstacles.isEmpty()) {
			handleCollision(player, futurePlayerPos, velocity, overlappingObstacles, playerRect);
		} else {
			player.getCurrentCharacterState().handleEvent(EVENT.NO_COLLISION);
		}

		player.setPositionVector(futurePlayerPos);
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

	private void handleCollision(Character player, Vector2 futurePlayerPos, Vector2 velocity, Array<TypedRectangle> overlappingObstacles, Rectangle playerRect) {
		overlappingObstacles.sort();

		TypedRectangle obstacle = overlappingObstacles.first();

		if (obstacle.isBlocksTop()) {
			futurePlayerPos.y -= obstacle.getOverlapY();
			velocity.y = 0;
		}

		if (obstacle.isBlocksBot()) {
			futurePlayerPos.y += obstacle.getOverlapY();
			velocity.y = 0;
		}

		if (obstacle.isBlocksLeft()) {
			futurePlayerPos.x += obstacle.getOverlapX();
			velocity.x = 0;
		}

		if (obstacle.isBlocksRight()) {
			futurePlayerPos.x -= obstacle.getOverlapX();
			velocity.x = 0;
		}

		if (obstacle.isFallTrough() && player.getCurrentCharacterState().getState() != STATE.CROUCHED) {
			futurePlayerPos.y += obstacle.getOverlapY();
			velocity.y = 0;
		}

	}

	public void setBlockingRectangles(Array<TypedRectangle> blockingRectangles) {
		this.blockingRectangles = blockingRectangles;
	}
}