package jelte.mygame.logic.spells.modifier;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Modifier {
	private ModifiersEnum type;
	private ModifierData data;
	private float maxTime;
	private float timeLeft;
	private float currentTickTime;
	private UUID id;
	private boolean completed = false;

	public enum MODIFIER_TYPE {
		DOT, NO_MOVE, STUNNED, NO_SPELL, DAMAGE, BUFF, DEBUFF, NO_DIRECTION
	}

	public Modifier() {
		type = ModifiersEnum.FIRE;
		this.data = ModifierFileReader.getModifierData().get(type.ordinal());
		currentTickTime = data.getTickInterval();
		maxTime = 0;
		timeLeft = 0;
		id = UUID.randomUUID();
		completed = true;
	}

	public Modifier(ModifiersEnum type, float maxTime, UUID uuid) {
		this.type = type;
		this.maxTime = maxTime;
		this.timeLeft = maxTime;
		this.id = uuid;
		this.data = ModifierFileReader.getModifierData().get(type.ordinal());
	}

	public Modifier(ModifiersEnum type, UUID uuid) {
		this.type = type;
		this.timeLeft = maxTime;
		this.id = uuid;
		this.data = ModifierFileReader.getModifierData().get(type.ordinal());
		this.maxTime = data.getDuration();
	}

	public void update(float delta) {
		if (timeLeft > 0) {
			timeLeft -= delta;
		} else {
			completed = true;
		}
	}

	public String getName() {
		return data.getName();
	}

	public String getModifierSpriteName() {
		return data.getModifierSpriteName();
	}

	public String getModifierType() {
		return data.getModifierType();
	}

	public float getTickInterval() {
		return data.getTickInterval();
	}

	public int getAmount() {
		return data.getAmount();
	}

	public String getPolygonPoints() {
		return data.getPolygonPoints();
	}

	public String getInfoText() {
		return data.getInfoText();
	}

}
