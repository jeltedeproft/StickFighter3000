package jelte.mygame.logic;

import java.util.EnumMap;

public class CharacterStateManager {
	private Action currentAction;
	private float timeInCurrentAction = 0f;
	private Character character;
	private EnumMap<Action, Float> actionTimes = new EnumMap<>(Action.class);

	public enum Action {
		cast, cast2, die, idle, idle2, move, move2, shoot, shoot2, hurt, teleport, crouch, shield, jump, latch, appear, movestop, hook;
	}

	public CharacterStateManager(Character character) {
		this.character = character;
		CharacterData data = character.getData();
		currentAction = Action.appear;
		actionTimes.put(Action.die, data.getDieAnimationFullTime());
		actionTimes.put(Action.shoot, data.getAttackAnimationFullTime());
		actionTimes.put(Action.shoot2, data.getAttack2AnimationFullTime());
		actionTimes.put(Action.hurt, data.getHurtAnimationFullTime());
		actionTimes.put(Action.teleport, data.getTeleportAnimationFullTime());
		actionTimes.put(Action.jump, data.getJumpAnimationFullTime());
		actionTimes.put(Action.appear, data.getAppearAnimationFullTime());
		actionTimes.put(Action.idle, 0f);
		actionTimes.put(Action.idle2, 0f);
		actionTimes.put(Action.move, 0f);
		actionTimes.put(Action.move2, 0f);
		actionTimes.put(Action.crouch, 0f);
		actionTimes.put(Action.shield, 0f);
		actionTimes.put(Action.latch, 0f);
	}

	public void update(float delta) {
		timeInCurrentAction += delta;
		Float currentActionFullTime = actionTimes.get(currentAction);
		if ((currentActionFullTime != 0) && (timeInCurrentAction >= currentActionFullTime)) {
			if (currentAction != Action.die) {
				currentAction = Action.idle;
			}
			// wizard.getData().setEntitySpriteName("cross"); TODO keep this or not? has trouble in playground
			timeInCurrentAction = 0f;
		}
	}

	public void setCurrentAction(Action action) {
		Float currentActionFullTime = actionTimes.get(currentAction);
		if (timeInCurrentAction >= currentActionFullTime) {
			currentAction = action;
			timeInCurrentAction = 0f;
		}
	}

	public Action getCurrentAction() {
		return currentAction;
	}

	public void setCurrentActionForcefully(Action action) {
		currentAction = action;
		timeInCurrentAction = 0f;
	}

}

