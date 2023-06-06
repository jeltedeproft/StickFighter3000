package jelte.mygame.logic.spells;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.utility.Constants;

public class SpellManager {
	private Array<Spell> spells;
	private Set<Collidable> bodies;
	private SpellFactoryRegistry registry;
	private SpellFactorySelector selector;
	private Vector2 mousePosition = new Vector2(0, 0);

	public SpellManager() {
		spells = new Array<>();
		bodies = new HashSet<>();
		registry = new SpellFactoryRegistry();
		selector = new SpellFactorySelector();
		registry.registerFactory(Constants.SPELL_CATEGORY_PROJECTILE, new ProjectileSpellFactory());
		registry.registerFactory(Constants.SPELL_CATEGORY_BUFF, new BuffSpellFactory());
		// TODO other factories

	}

	public void update(float delta, Vector2 mousePosition, Array<Character> characters) {
		characters.forEach(this::updateCharacter);
		this.mousePosition.x = mousePosition.x;
		this.mousePosition.y = mousePosition.y;
		spells.forEach(spell -> spell.update(delta));
		final Iterator<Spell> iterator = spells.iterator();
		while (iterator.hasNext()) {
			final Spell spell = iterator.next();
			if (spell.isComplete()) {
				bodies.remove(spell.getPhysicsComponent());
				iterator.remove();
			}
		}
	}

	private void updateCharacter(Character character) {
		if (!character.getSpellsreadyToCast().isEmpty()) {
			createSpell(character.getSpellsreadyToCast().removeFirst(), character, mousePosition, character.getId());
		}
	}

	public void createSpell(SpellData spellData, Character character, Vector2 mousePosition, UUID casterId) {
		SpellFactory factory = selector.selectFactory(spellData);
		Spell spell = factory.createSpell(spellData, character, mousePosition, casterId);
		spells.add(spell);
		bodies.add(spell.getPhysicsComponent());
	}

	public void removeSpell(Spell spell) {
		spells.removeValue(spell, false);
		bodies.remove(spell.getPhysicsComponent());
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

	public Set<Collidable> getAllSpellBodies() {
		return bodies;
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
