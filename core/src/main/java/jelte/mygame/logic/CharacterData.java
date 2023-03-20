package jelte.mygame.logic;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CharacterData {

	private int id;
	private String name;
	private ArrayList<Integer> spells;
	private String entitySpriteName;
	private boolean melee;
	private float maxHP;
	private float movementSpeed;
	private int attackPower;
	private int defense;
	private String unitExplanation;
	private float idleAnimationSpeed;
	private float castAnimationSpeed;
	private float hurtAnimationSpeed;
	private float dieAnimationSpeed;
	private float moveAnimationSpeed;
	private float teleportAnimationSpeed;
	private float shootAnimationSpeed;
	private float jumpAnimationFullTime;
	private float teleportAnimationFullTime;
	private float hurtAnimationFullTime;
	private float attack2AnimationFullTime;
	private float attackAnimationFullTime;
	private float dieAnimationFullTime;
	private float AppearAnimationFullTime;
}
