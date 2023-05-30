package jelte.mygame.logic.spells;

import com.badlogic.gdx.utils.StringBuilder;

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
