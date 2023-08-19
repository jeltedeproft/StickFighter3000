package jelte.mygame.logic.character.movement;

public interface MovementManagerInterface {

	public void startMovingOnTheGround(float speed);

	public void continueMovingOnTheGround(float speed);

	public void stopMovingOnTheGround();

	public void startMovingInTheAir(float speed);

	public void stopMovingInTheAir();

	public void climb(float climbSpeed);

	public void startJump();

	public void applyJumpForce();

	public void applyHorizontalForce(float distance);

	public void pullDown(float speed);

	public void setFallTrough(boolean fallTrough);

	public void grabLedge();

	public boolean characterIsFalltrough();

	public boolean characterHaslanded();

	public boolean characterisFalling();

	public boolean characterIsAtHighestPoint();

	public boolean characterisStandingStill();

	public boolean characterisRunning();

}
