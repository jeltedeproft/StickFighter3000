package jelte.mygame.logic.spells;

import lombok.Data;

@Data
public class ModifierData {

	private int id;
	private String name;
	private String modifierSpriteName;
	private String modifierType;
	private float tickInterval;
	private int amount;
	private String polygonPoints;
	private String infoText;

}
