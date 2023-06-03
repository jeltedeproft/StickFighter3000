package jelte.mygame.logic.spells;

public class SpellFactorySelector {
	public SpellFactory selectFactory(SpellData spellType) {
		return switch (SpellsEnum.values()[spellType.getId()]) {
		case FIREBALL -> new ProjectileSpellFactory();
		default -> new DefaultSpellFactory();
		};
	}
}
