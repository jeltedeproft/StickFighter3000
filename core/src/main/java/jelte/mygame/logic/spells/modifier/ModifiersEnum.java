package jelte.mygame.logic.spells.modifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//keep this in sync with JSON files. ORDER IS IMPORTANT
public enum ModifiersEnum {
	FIRE, // 0
	FROZEN, // 1
	STUNNED, // 2
	SILENCED, // 3
	TIMED_KILL, // 4
	SPEED_BUFF, // 5
	MOVE_ONLY_FORWARD, // 6
	SHIELDED, // 7
	MOVE_ONLY_BACKWARD, // 8
	RUPTURED, // 9
	ATTACK_BUFF, // 10
	ATTACK_DEBUFF, // 11
	SPEED_DEBUFF, // 12
	ARMOR_BUFF, // 13
	ARMOR_DEBUFF;// 14

	private static final List<ModifiersEnum> TYPES = Collections.unmodifiableList(Arrays.asList(ModifiersEnum.values()));
	private static final int SIZE = TYPES.size();

	public static List<ModifiersEnum> getTypes() {
		return TYPES;
	}

}
