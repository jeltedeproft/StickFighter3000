package jelte.mygame.logic.spells;

public class SpellFactorySelector {
	public SpellFactory selectFactory(SpellData spellData) {
		return switch (SpellsEnum.values()[spellData.getId()]) {
		case FIREBALL -> new ProjectileSpellFactory();
		default -> new DefaultSpellFactory();
		};
	}
}
