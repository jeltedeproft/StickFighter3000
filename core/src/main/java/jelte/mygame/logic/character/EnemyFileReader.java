package jelte.mygame.logic.character;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

import jelte.mygame.utility.Constants;

public class EnemyFileReader {
	private static final String TAG = EnemyFileReader.class.getSimpleName();
	private static boolean statsLoaded = false;
	private static ObjectMap<Integer, EnemyData> unitData = new ObjectMap<>();
	private static final Array<String> allEnemyNames = new Array<>();

	private EnemyFileReader() {

	}

	@SuppressWarnings("unchecked")
	public static void loadUnitStatsInMemory() {
		if (!statsLoaded) {
			final Json json = new Json();
			ArrayList<EnemyData> unitStats;
			try {
				unitStats = (ArrayList<EnemyData>) json.fromJson(ClassReflection.forName("java.util.ArrayList"), ClassReflection.forName("jelte.mygame.logic.character.EnemyData"), Gdx.files.internal(Constants.ENEMY_STATS_FILE_LOCATION));
				for (int i = 0; i < unitStats.size(); i++) {
					final EnemyData data = unitStats.get(i);
					unitData.put(data.getId(), data);
					allEnemyNames.add(data.getEntitySpriteName());
				}
			} catch (final ReflectionException e) {
				e.printStackTrace();
			}
		}
	}

	public static ObjectMap<Integer, EnemyData> getUnitData() {
		return unitData;
	}

	public static int getIdByName(String name) {
		for (Entry<Integer, EnemyData> entry : unitData.entries()) {
			if (entry.value.getEntitySpriteName().equals(name)) {
				return entry.key;
			}
		}
		Gdx.app.debug(TAG, String.format("enemy id not found for: %s", name), null);
		return -1;
	}

	public static Array<String> getAllEnemyNames() {
		return allEnemyNames;
	}
}
