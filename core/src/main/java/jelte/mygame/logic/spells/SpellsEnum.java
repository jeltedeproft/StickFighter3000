package jelte.mygame.logic.spells;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//keep this in sync with JSON files. ORDER IS IMPORTANT
public enum SpellsEnum {
	ATTACK("attack"), // 0
	FALLATTACK("fallattack"), // 1
	FIREBALL("fireball"), // 2
	RANGED_ATTACK("rangedAttack"); // 3

	private static final List<SpellsEnum> TYPES = Collections.unmodifiableList(Arrays.asList(SpellsEnum.values()));
	private static final int SIZE = TYPES.size();
	private static final Random random = new Random();

	private String spellName;

	public String getSpellName() {
		return spellName;
	}

	SpellsEnum(final String spellName) {
		this.spellName = spellName;
	}

	public static SpellsEnum randomSpellType() {
		return TYPES.get(random.nextInt(SIZE));
	}

	public static List<SpellsEnum> getTypes() {
		return TYPES;
	}
}
