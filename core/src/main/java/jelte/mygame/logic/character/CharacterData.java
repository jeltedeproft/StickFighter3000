package jelte.mygame.logic.character;

public interface CharacterData {

	float getMaxHP();

	String getEntitySpriteName();

	String getName();

	float getAttackFullTime();

	float getHurtFullTime();

	float getStopRunningFullTime();

	float getDashingFullTime();

	float getLandingFullTime();

	float getAppearFullTime();

	float getDefaultPreCastFullTime();

	float getDefaultCastFullTime();

	float getBlockingFullTime();

	float getTeleportFullTime();

	float getRollAttackFullTime();

	float getRollFullTime();

	float getWallSlidingStopFullTime();

	float getLandAttackingFullTime();

	float getHoldToSlideFullTime();

	float getAppearFrameDuration();

	float getAttackFrameDuration();

	float getDieFrameDuration();

	float getHurtFrameDuration();

	float getIdleFrameDuration();

	float getJumpFrameDuration();

	float getRunningFrameDuration();

	float getFallAttackingFrameDuration();

}
