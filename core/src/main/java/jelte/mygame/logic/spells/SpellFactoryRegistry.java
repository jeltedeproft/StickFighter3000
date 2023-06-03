package jelte.mygame.logic.spells;

import java.util.HashMap;
import java.util.Map;

public class SpellFactoryRegistry {
	private Map<String, SpellFactory> factories;

	public SpellFactoryRegistry() {
		factories = new HashMap<>();
	}

	public void registerFactory(String category, SpellFactory factory) {
		factories.put(category, factory);
	}

	public SpellFactory getFactory(String category) {
		return factories.get(category);
	}
}
