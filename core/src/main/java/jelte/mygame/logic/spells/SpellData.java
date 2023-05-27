package jelte.mygame.logic.spells;

import lombok.Data;

@Data
public class SpellData {

	private int id;
	private String name;
	private String spriteName;
	private String iconSpriteName;
	private int coolDown;
	private String type;
	private boolean goesTroughObstacles;
	private String affects;
	private float damage;
	private int speed;
	private String spellSound;
	private float duration;
	private int range;
	private int areaOfEffectRange;
	private String polygonPoints;
	private String infoText;
	private float animationSpeed;

}
