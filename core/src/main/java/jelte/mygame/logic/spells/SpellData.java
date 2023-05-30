package jelte.mygame.logic.spells;

import com.badlogic.gdx.utils.StringBuilder;

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
	private float windupFullTime;
	private float loopFullTime;
	private float endFullTime;
	private float windupFrameDuration;
	private float loopFrameDuration;
	private float endFrameDuration;
	private float animationSpeed;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name: ");
		sb.append(name);
		sb.append("id: ");
		sb.append(id);
		return sb.toString();
	}

}
