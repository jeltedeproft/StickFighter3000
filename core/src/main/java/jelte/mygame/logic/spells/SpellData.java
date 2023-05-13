package jelte.mygame.logic.spells;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SpellData {
	private int id;
	private String name;
	private String entitySpriteName;
	private float movementSpeed;
	private int damage;
	private String spellExplanation;
	private float frameDuration;
	private float fullTime;
}
