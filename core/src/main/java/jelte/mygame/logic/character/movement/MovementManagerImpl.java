package jelte.mygame.logic.character.movement;

import jelte.mygame.logic.character.Character;

public class MovementManagerImpl implements MovementManagerInterface {
	Character character;

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
	public void jump(float durationPressed) {
		// TODO Auto-generated method stub

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
