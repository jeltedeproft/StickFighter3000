package jelte.mygame.graphical.specialEffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

import java.util.ArrayList;

public class SpecialEffectsFileReader {
	private static final String TAG = SpecialEffectsFileReader.class.getSimpleName();
	private static boolean statsLoaded = false;
	private static ObjectMap<Integer, SpecialEffectsData> specialEffectsData = new ObjectMap<>();

	private SpecialEffectsFileReader() {

	}

	@SuppressWarnings("unchecked")
	public static void loadSpecialEffectsInMemory(final String fileLocation) {
		if (!statsLoaded) {
			final Json json = new Json();
			ArrayList<SpecialEffectsData> specialEffectStats;
			try {
				specialEffectStats = (ArrayList<SpecialEffectsData>) json.fromJson(ClassReflection.forName("java.util.ArrayList"), ClassReflection.forName("jelte.mygame.graphical.specialEffects.SpecialEffectsData"), Gdx.files.internal(fileLocation));
				for (int i = 0; i < specialEffectStats.size(); i++) {
					final SpecialEffectsData data = specialEffectStats.get(i);
					specialEffectsData.put(data.getId(), data);
				}
				statsLoaded = true;
			} catch (final ReflectionException e) {
				e.printStackTrace();
			}
		}
	}

	public static ObjectMap<Integer, SpecialEffectsData> getSpecialEffectsData() {
		return specialEffectsData;
	}

	public static int getIdByCharacterActionDirection(String characterName, String action, String direction) {
		for (Entry<Integer, SpecialEffectsData> entry : specialEffectsData.entries()) {
			if (entry.value.getCharacterName().equals(characterName) && entry.value.getActionName().equals(action) && entry.value.getDirection().equals(direction)) {
				return entry.key;
			}
		}
		Gdx.app.debug(TAG, String.format("special effects id not found for: %s %s %s", characterName, action, direction));
		return -1;
	}

	public static int getIdBySpriteName(String spriteName) {
		for (Entry<Integer, SpecialEffectsData> entry : specialEffectsData.entries()) {
			if (entry.value.getSpriteName().equals(spriteName)) {
				return entry.key;
			}
		}
		Gdx.app.debug(TAG, String.format("special effects id not found for: %s", spriteName));
		return -1;
	}
}
