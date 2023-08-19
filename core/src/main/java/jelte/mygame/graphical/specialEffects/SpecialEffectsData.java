package jelte.mygame.graphical.specialEffects;

import com.badlogic.gdx.utils.StringBuilder;

import lombok.Data;

@Data
public class SpecialEffectsData {

	private int id;
	private String characterName;
	private String actionName;
	private float frameDuration;
	private String playMode;
	private String direction;
	private String alignment;
	private String spriteName;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("characterName: ");
		sb.append(characterName);
		sb.append("actionName: ");
		sb.append(actionName);
		sb.append("id: ");
		sb.append(id);
		return sb.toString();
	}

}
