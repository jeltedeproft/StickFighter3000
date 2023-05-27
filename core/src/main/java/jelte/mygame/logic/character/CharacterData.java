package jelte.mygame.logic.character;

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
	private float jumpFrameDuration;
	private float hurtFrameDuration;
	private float attackFrameDuration;
	private float dieFrameDuration;
	private float appearFrameDuration;
	private float idleFrameDuration;
	private float runningFrameDuration;
	private float attackFullTime;
	private float hurtFullTime;
	private float appearFullTime;
	private float landingFullTime;
	private float stopRunningFullTime;
	private float dashingFullTime;
	private float defaultCastFullTime;
	private float defaultPreCastFullTime;
	private float teleportFullTime;
	private float rollAttackFullTime;
	private float rollFullTime;
	private float wallSlidingStopFullTime;
	private float holdToSlideFullTime;
	private float fallAttackingFullTime;
	private float blockingFullTime;
}
