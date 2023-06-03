package jelte.mygame.logic.collisions;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.Collidable.COLLIDABLE_TYPE;
import jelte.mygame.logic.collisions.strategy.CharacterCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.CollisionStrategy;
import jelte.mygame.logic.collisions.strategy.SpellCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticBotCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticLeftCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticPlatformCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticRightCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticTopCollisionStrategy;
import jelte.mygame.logic.spells.Spell;

//TODO refacotr this, its ugly
public class CollisionHandlingSystem {
	private Map<COLLIDABLE_TYPE, CollisionStrategy> collisionStrategies;

	public CollisionHandlingSystem() {
		collisionStrategies = new EnumMap<>(COLLIDABLE_TYPE.class);
		collisionStrategies.put(COLLIDABLE_TYPE.CHARACTER, new CharacterCollisionStrategy());
		collisionStrategies.put(COLLIDABLE_TYPE.SPELL, new SpellCollisionStrategy());
		collisionStrategies.put(COLLIDABLE_TYPE.STATIC_TOP, new StaticTopCollisionStrategy());
		collisionStrategies.put(COLLIDABLE_TYPE.STATIC_BOT, new StaticBotCollisionStrategy());
		collisionStrategies.put(COLLIDABLE_TYPE.STATIC_LEFT, new StaticLeftCollisionStrategy());
		collisionStrategies.put(COLLIDABLE_TYPE.STATIC_RIGHT, new StaticRightCollisionStrategy());
		collisionStrategies.put(COLLIDABLE_TYPE.STATIC_PLATFORM, new StaticPlatformCollisionStrategy());
	}

	public void handleCollisions(Set<CollisionPair> pairs, Array<Character> allCharacters, Array<Spell> allSpells) {
		for (CollisionPair pair : pairs) {
			Collidable collidable1 = pair.getCollidable1();
			Collidable collidable2 = pair.getCollidable2();

			COLLIDABLE_TYPE type1 = collidable1.getType();
			COLLIDABLE_TYPE type2 = collidable2.getType();

			if (COLLIDABLE_TYPE.isStatic(type1) || COLLIDABLE_TYPE.isStatic(type2)) {

				CollisionStrategy collisionStrategy1 = collisionStrategies.get(type1);
				CollisionStrategy collisionStrategy2 = collisionStrategies.get(type2);

				if (collisionStrategy1 != null && collisionStrategy2 != null) {
					collisionStrategy1.resolvePossibleCollision(collidable1, collidable2);
					collisionStrategy2.resolvePossibleCollision(collidable2, collidable1);
				}
			} else if (COLLIDABLE_TYPE.isSpell(type1)) {
				SpellCollisionStrategy collisionStrategy = (SpellCollisionStrategy) collisionStrategies.get(type1);
				collisionStrategy.resolvePossibleCollision(getSpellById(allSpells, collidable1.getId()), getCharacterById(allCharacters, collidable2.getId()));
			} else if (COLLIDABLE_TYPE.isSpell(type2)) {
				SpellCollisionStrategy collisionStrategy = (SpellCollisionStrategy) collisionStrategies.get(type2);
				collisionStrategy.resolvePossibleCollision(getSpellById(allSpells, collidable2.getId()), getCharacterById(allCharacters, collidable1.getId()));
			}
		}

	}

	private Character getCharacterById(Array<Character> characters, UUID id) {
		for (Character character : characters) {
			if (character.getId().equals(id)) {
				return character;
			}
		}
		return null;
	}

	private Spell getSpellById(Array<Spell> spells, UUID id) {
		for (Spell spell : spells) {
			if (spell.getId().equals(id)) {
				return spell;
			}
		}
		return null;
	}
}
