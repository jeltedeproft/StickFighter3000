package jelte.mygame.logic.spells.factories;

import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellsEnum;

public class SpellFactorySelector {
	public SpellFactory selectFactory(SpellData spellData) {
		return switch (SpellsEnum.values()[spellData.getId()]) {
		case ATTACK -> new AoeSpellFactory();
		case FALLATTACK -> new AoeSpellFactory();
		case FIREBALL -> new ProjectileSpellFactory();
		default -> new DefaultSpellFactory();
		};
	}
}
