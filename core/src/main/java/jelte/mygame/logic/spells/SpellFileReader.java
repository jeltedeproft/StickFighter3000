package jelte.mygame.logic.spells;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class SpellFileReader {
	private static final String TAG = SpellFileReader.class.getSimpleName();
	private static boolean statsLoaded = false;
	private static ObjectMap<Integer, SpellData> spellData = new ObjectMap<>();

	private SpellFileReader() {

	}

	@SuppressWarnings("unchecked")
	public static void loadSpellsInMemory(final String fileLocation) {
		if (!statsLoaded) {
			final Json json = new Json();
			ArrayList<SpellData> unitStats;
			try {
				unitStats = (ArrayList<SpellData>) json.fromJson(ClassReflection.forName("java.util.ArrayList"), ClassReflection.forName("jelte.mygame.logic.spells.SpellData"), Gdx.files.internal(fileLocation));
				for (int i = 0; i < unitStats.size(); i++) {
					final SpellData data = unitStats.get(i);
					spellData.put(data.getId(), data);
				}
				statsLoaded = true;
			} catch (final ReflectionException e) {
				e.printStackTrace();
			}
		}
	}

	public static ObjectMap<Integer, SpellData> getSpellData() {
		return spellData;
	}

	public static int getIdByName(String name) {
		for (Entry<Integer, SpellData> entry : spellData.entries()) {
			if (entry.value.getName().equals(name)) {
				return entry.key;
			}
		}
		Gdx.app.debug(TAG, String.format("spell id not found for: %s", name));
		return -1;
	}
}
