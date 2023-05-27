package jelte.mygame.logic.spells;

import java.util.UUID;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.physics.PhysicsComponent;

public class SpellManager {
	private Array<Spell> spells;
	private Array<PhysicsComponent> bodies;

	public SpellManager() {
		spells = new Array<>();
		bodies = new Array<>();
	}

	public void addSpell(Spell spell) {
		spells.add(spell);
		bodies.add(spell.getPhysicsComponent());
	}

	public void removeEnemy(Spell spell) {
		spells.removeValue(spell, false);
		bodies.removeValue(spell.getPhysicsComponent(), false);
	}

	public Spell getSpellById(UUID id) {
		for (Spell spell : spells) {
			if (spell.getId().equals(id)) {
				return spell;
			}
		}
		return null;
	}

	public Array<Spell> getAllSpells() {
		return spells;
	}

	public Array<PhysicsComponent> getAllSpellBodies() {
		return bodies;
	}

	public void update(float delta) {
		spells.forEach(spell -> spell.update(delta));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		for (Spell spell : spells) {
			sb.append("spell");
			sb.append(" --> ");
			sb.append(spell.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

}
