package jelte.mygame.logic.character.movement;

import com.badlogic.gdx.utils.TimeUtils;

import jelte.mygame.logic.character.Character;
import jelte.mygame.utility.Constants;

public class MovementManagerImpl implements MovementManagerInterface {
	private Character character;
	private Long jumpStartTime;

	public MovementManagerImpl(Character character) {
		this.character = character;
	}

	@Override
	public void startMovingOnTheGround(float speed, boolean right) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopMovingOnTheGround(boolean right) {
		// TODO Auto-generated method stub
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
	public void setFallTrough(boolean fallTrough) {
		// TODO Auto-generated method stub

	}

	@Override
	public void grabLedge() {
		// TODO Auto-generated method stub

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
