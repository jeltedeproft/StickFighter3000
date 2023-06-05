package jelte.mygame.logic.spells;

public class SpellFactorySelector {
	public SpellFactory selectFactory(SpellData spellData) {
		return switch (SpellsEnum.values()[spellData.getId()]) {
		case ATTACK -> new DamageAreaSpellFactory();
		case FIREBALL -> new ProjectileSpellFactory();
		default -> new DefaultSpellFactory();
		};
	}
}
