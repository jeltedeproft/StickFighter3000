package jelte.mygame.logic.collisions;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.Collidable.COLLIDABLE_TYPE;
import jelte.mygame.logic.collisions.strategy.CollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticBotCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticLeftCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticPlatformCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticRightCollisionStrategy;
import jelte.mygame.logic.collisions.strategy.StaticTopCollisionStrategy;
import jelte.mygame.logic.spells.Spell;

public class CollisionHandlingSystem {
	private Map<COLLIDABLE_TYPE, CollisionStrategy> collisionStrategies;

	public CollisionHandlingSystem() {
		collisionStrategies = new EnumMap<>(COLLIDABLE_TYPE.class);
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

			if (COLLIDABLE_TYPE.isStatic(type1)) {
				CollisionStrategy collisionStrategy = collisionStrategies.get(type1);
				collisionStrategy.resolvePossibleCollision(collidable1, collidable2);
			} else if (COLLIDABLE_TYPE.isStatic(type2)) {
				CollisionStrategy collisionStrategy = collisionStrategies.get(type2);
				collisionStrategy.resolvePossibleCollision(collidable2, collidable1);
			} else if (isCollisionWithSpell(type1)) {
				Spell spell = getSpellById(allSpells, collidable1.getId());
				Character character = getCharacterById(allCharacters, collidable2.getId());
				spell.applyCollisionEffect(character);
			} else if (isCollisionWithSpell(type2)) {
				Spell spell = getSpellById(allSpells, collidable2.getId());
				Character character = getCharacterById(allCharacters, collidable1.getId());
				spell.applyCollisionEffect(character);
			}
		}
	}

	private boolean isCollisionWithSpell(COLLIDABLE_TYPE type) {
		return COLLIDABLE_TYPE.isSpell(type);
	}

	private Character getCharacterById(Array<Character> characters, UUID id) {
		Stream<Character> stream = StreamSupport.stream(characters.spliterator(), false);
		return stream
				.filter(character -> character.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

	private Spell getSpellById(Array<Spell> spells, UUID id) {
		Stream<Spell> stream = StreamSupport.stream(spells.spliterator(), false);
		return stream
				.filter(spell -> spell.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

}
