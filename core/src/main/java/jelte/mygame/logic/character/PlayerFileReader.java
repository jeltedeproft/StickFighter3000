package jelte.mygame.logic.character;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class PlayerFileReader {
	private static final String TAG = PlayerFileReader.class.getSimpleName();
	private static boolean statsLoaded = false;
	private static ObjectMap<Integer, PlayerData> unitData = new ObjectMap<>();
	private static final Array<String> allCharacterNames = new Array<>();

	private PlayerFileReader() {

	}

	@SuppressWarnings("unchecked")
	public static void loadUnitStatsInMemory(final String fileLocation) {
		if (!statsLoaded) {
			final Json json = new Json();
			ArrayList<PlayerData> unitStats;
			try {
				unitStats = (ArrayList<PlayerData>) json.fromJson(ClassReflection.forName("java.util.ArrayList"), ClassReflection.forName("jelte.mygame.logic.character.PlayerData"), Gdx.files.internal(fileLocation));
				for (int i = 0; i < unitStats.size(); i++) {
					final PlayerData data = unitStats.get(i);
					unitData.put(data.getId(), data);
					allCharacterNames.add(data.getEntitySpriteName());
				}
			} catch (final ReflectionException e) {
				e.printStackTrace();
			}
		}
	}

	public static ObjectMap<Integer, PlayerData> getUnitData() {
		return unitData;
	}

	public static int getIdByName(String name) {
		for (Entry<Integer, PlayerData> entry : unitData.entries()) {
			if (entry.value.getEntitySpriteName().equals(name)) {
				return entry.key;
			}
		}
		Gdx.app.debug(TAG, String.format("character id not found for: %s", name), null);
		return -1;
	}

	public static Array<String> getAllCharacterNames() {
		return allCharacterNames;
	}
}
