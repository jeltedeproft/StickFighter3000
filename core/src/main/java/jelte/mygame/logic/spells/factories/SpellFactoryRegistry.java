package jelte.mygame.logic.spells.factories;

import java.util.EnumMap;
import java.util.Map;

import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.spells.Spell.SPELL_TYPE;

public class SpellFactoryRegistry {
	private Map<SPELL_TYPE, SpellFactory> factories;

	public SpellFactoryRegistry() {
		factories = new EnumMap<>(SPELL_TYPE.class);
		for (SPELL_TYPE type : SPELL_TYPE.values()) {
			factories.put(type, getFactoryForType(type));
		}
	}

	private SpellFactory getFactoryForType(SPELL_TYPE type) {
		return switch (type) {
		case AOE -> new AoeSpellFactory();
		case BUFF -> new BuffSpellFactory();
		case PROJECTILE -> new ProjectileSpellFactory();
		case SUMMON -> new SummonSpellFactory();
		default -> new DefaultSpellFactory();
		};
	}

	public SpellFactory getFactory(SpellData spellData) {
		String type = spellData.getType();
		SPELL_TYPE spellType = SPELL_TYPE.valueOf(type);
		return factories.get(spellType);
	}
}
