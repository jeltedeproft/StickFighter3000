package jelte.mygame.logic.character.movement;

public interface MovementManagerInterface {

	public void startMovingOnTheGround(float speed, boolean right);

	public void stopMovingOnTheGround();

	public void startMovingInTheAir(float speed, boolean right);

	public void stopMovingInTheAir();

	public void startJump();

	public void applyJumpForce();

	public void setFallTrough(boolean fallTrough);

	public void grabLedge();

	public boolean characterIsFalltrough();

	public boolean characterHaslanded();

	public boolean characterisFalling();

	public boolean characterIsAtHighestPoint();

	public boolean characterisStandingStill();

	public boolean characterisRunning();

}
