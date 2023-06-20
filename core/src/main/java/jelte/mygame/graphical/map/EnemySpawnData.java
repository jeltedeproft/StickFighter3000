package jelte.mygame.graphical.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnemySpawnData {
	private Vector2 spawnPoint;
	private String type;
	private Array<PatrolPoint> patrolPoints;

}
