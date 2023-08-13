package jelte.mygame.logic.character.movement;

import com.badlogic.gdx.utils.TimeUtils;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.utility.Constants;

public class MovementManagerImpl implements MovementManagerInterface {
	Character character;
	private Long jumpStartTime;

	public MovementManagerImpl(Character character) {
		this.character = character;
	}

	@Override
	public void startMovingOnTheGround(float speed) {
		character.getPhysicsComponent().setVelocityX(speed);
		character.getPhysicsComponent().setDirection(speed > 0 ? Direction.right : Direction.left);
		character.getPhysicsComponent().setStarting(true);
	}

	@Override
	public void stopMovingOnTheGround() {
		character.getPhysicsComponent().setStopping(true);
	}

	@Override
	public void startMovingInTheAir(float speed, boolean right) {
		character.getPhysicsComponent().setVelocityX(right ? speed : -speed);
		character.getPhysicsComponent().setStarting(true);
	}

	@Override
	public void stopMovingInTheAir() {
		character.getPhysicsComponent().setStopping(true);
	}

	@Override
	public void climb(float climbSpeed) {
		character.getPhysicsComponent().setVelocityY(climbSpeed);
	}

	@Override
	public void startJump() {
		jumpStartTime = TimeUtils.nanoTime(); // Record the start time in nanoseconds
		character.getPhysicsComponent().getVelocity().y = Constants.JUMP_SPEED.y;
	}

	@Override
	public void applyJumpForce() {
		float jumpDuration = (TimeUtils.nanoTime() - jumpStartTime) / 1000000000f; // Convert nanoseconds to seconds

		character.getPhysicsComponent().getVelocity().y = Constants.JUMP_SPEED.y;

		if (jumpDuration < Constants.MAX_JUMP_DURATION && character.getPhysicsComponent().getVelocity().y > 0) {
			character.getPhysicsComponent().getVelocity().y = Math.min(character.getPhysicsComponent().getVelocity().y, Constants.MAX_JUMP_SPEED.y);
		}
	}

	@Override
	public void applyHorizontalForce(float distance) {
		if (character.getPhysicsComponent().getDirection().equals(Direction.right)) {
			character.getPhysicsComponent().move(distance, 0);
		} else {
			character.getPhysicsComponent().move(-distance, 0);
		}
	}

	@Override
	public void pullDown(float speed) {
		character.getPhysicsComponent().getVelocity().y -= speed;
		character.getPhysicsComponent().getAcceleration().y -= speed;
	}

	@Override
	public void setFallTrough(boolean fallTrough) {
		character.getPhysicsComponent().setFallTrough(fallTrough);
	}

	@Override
	public void grabLedge() {
		character.getPhysicsComponent().getVelocity().y = -Constants.GRAVITY.y;
	}

	@Override
	public boolean characterIsFalltrough() {
		return character.getPhysicsComponent().isFallTrough();
	}

	@Override
	public boolean characterHaslanded() {
		return Math.abs(character.getPhysicsComponent().getVelocity().y) == 0;
	}

	@Override
	public boolean characterisFalling() {
		return character.getPhysicsComponent().getVelocity().y <= 0;
	}

	@Override
	public boolean characterIsAtHighestPoint() {
		return character.getPhysicsComponent().getVelocity().y < 0.1f;
	}

	@Override
	public boolean characterisStandingStill() {
		return character.getPhysicsComponent().getVelocity().epsilonEquals(0, 0);
	}

	@Override
	public boolean characterisRunning() {
		return Math.abs(character.getPhysicsComponent().getVelocity().x) > 0;
	}

}
