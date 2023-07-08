package jelte.mygame.logic.spells.modifier;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class ModifierFileReader {
	private static final String TAG = ModifierFileReader.class.getSimpleName();
	private static boolean statsLoaded = false;
	private static ObjectMap<Integer, ModifierData> modifierData = new ObjectMap<>();

	private ModifierFileReader() {

	}

	@SuppressWarnings("unchecked")
	public static void loadModifiersInMemory(final String fileLocation) {
		if (!statsLoaded) {
			final Json json = new Json();
			ArrayList<ModifierData> modifierStats;
			try {
				modifierStats = (ArrayList<ModifierData>) json.fromJson(ClassReflection.forName("java.util.ArrayList"), ClassReflection.forName("jelte.mygame.logic.spells.modifier.ModifierData"), Gdx.files.internal(fileLocation));
				for (int i = 0; i < modifierStats.size(); i++) {
					final ModifierData data = modifierStats.get(i);
					modifierData.put(data.getId(), data);
				}
				statsLoaded = true;
			} catch (final ReflectionException e) {
				e.printStackTrace();
			}
		}
	}

	public static ObjectMap<Integer, ModifierData> getModifierData() {
		return modifierData;
	}

	public static int getIdByName(String name) {
		for (Entry<Integer, ModifierData> entry : modifierData.entries()) {
			if (entry.value.getName().equals(name)) {
				return entry.key;
			}
		}
		Gdx.app.debug(TAG, String.format("modifier id not found for: %s", name));
		return -1;
	}
}
