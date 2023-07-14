package jelte.mygame.logic.spells;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.spells.factories.SpellFactory;
import jelte.mygame.logic.spells.factories.SpellFactoryRegistry;
import jelte.mygame.logic.spells.spells.AbstractSpell;
import jelte.mygame.logic.spells.spells.Spell;

public class SpellManager {
	private Map<Character, Array<AbstractSpell>> charactersWithSpells;
	private Set<Collidable> bodies;
	private SpellFactoryRegistry registry;
	private Vector2 mousePosition = new Vector2(0, 0);

	public SpellManager() {
		charactersWithSpells = new HashMap<>();
		bodies = new HashSet<>();
		registry = new SpellFactoryRegistry();
	}

	public void update(float delta, Vector2 mousePosition, Array<Character> characters) {
		characters.forEach(this::updateCharacter);
		this.mousePosition.x = mousePosition.x;
		this.mousePosition.y = mousePosition.y;
		charactersWithSpells.forEach((k, v) -> v.forEach(spell -> spell.update(delta, k, mousePosition)));
		for (Entry<Character, Array<AbstractSpell>> entry : charactersWithSpells.entrySet()) {
			final Iterator<AbstractSpell> iterator = entry.getValue().iterator();
			while (iterator.hasNext()) {
				final Spell spell = iterator.next();
				if (spell.isComplete()) {
					bodies.remove(spell.getPhysicsComponent());
					iterator.remove();
				}
			}
		}
	}

	private void updateCharacter(Character character) {
		if (!character.getSpellsreadyToCast().isEmpty()) {
			createSpell(character.getSpellsreadyToCast().removeFirst(), character);
		}
	}

	public void createSpell(SpellData spellData, Character character) {
		SpellFactory factory = registry.getFactory(spellData);
		AbstractSpell spell = factory.createSpell(spellData, character, mousePosition);
		charactersWithSpells.computeIfAbsent(character, value -> new Array<AbstractSpell>());
		charactersWithSpells.get(character).add(spell);
		bodies.add(spell.getPhysicsComponent());
	}

	public Array<AbstractSpell> getAllSpells() {
		Array<AbstractSpell> spells = new Array<>();
		charactersWithSpells.forEach((k, v) -> spells.addAll(v));
		return spells;
	}

	public Set<Collidable> getAllSpellBodies() {
		return bodies;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		for (Entry<Character, Array<AbstractSpell>> entry : charactersWithSpells.entrySet()) {
			sb.append("character : ");
			sb.append(entry.getKey().toString());
			sb.append("\n");
			sb.append("spell : ");
			sb.append(entry.getValue().toString());
			sb.append("\n");
		}
		return sb.toString();
	}

}
