package jelte.mygame.logic.character;

import java.util.ArrayList;

import com.badlogic.gdx.utils.StringBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnemyData implements CharacterData {

	private int id;
	private String name;
	private ArrayList<Integer> spells;
	private String entitySpriteName;
	private String aiStrategyName;
	private boolean melee;
	private float maxHP;
	private float movementSpeed;
	private int attackPower;
	private float attackCooldown;
	private int defense;
	private int attackrange;
	private int castrange;
	private int visionShapeWidth;
	private int visionShapeHeight;
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
	private float fallAttackingFrameDuration;
	private float landAttackingFullTime;
	private float blockingFullTime;

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
