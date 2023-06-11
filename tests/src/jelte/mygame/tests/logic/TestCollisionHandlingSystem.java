package jelte.mygame.tests.logic;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterFileReader;
import jelte.mygame.logic.collisions.CollisionHandlingSystem;
import jelte.mygame.logic.collisions.CollisionPair;
import jelte.mygame.logic.spells.spells.Spell;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestCollisionHandlingSystem {
	private static CollisionHandlingSystem collisionhandlingSystem;
	private static Array<Character> bodies;
	private static Array<Spell> spells;
	private static Set<CollisionPair> pairs;

	@BeforeClass
	public static void beforeAllTests() {
		CharacterFileReader.loadUnitStatsInMemory();
		bodies = new Array<>();
		spells = new Array<>();
		pairs = new HashSet<>();
		collisionhandlingSystem = new CollisionHandlingSystem();
	}

	private void addCharacter() {
		Character character = new Character(CharacterFileReader.getUnitData().get(4), UUID.randomUUID());
		bodies.add(character);
	}

	private void addSpell() {
		// TODO
	}

	private void addStatickBlock() {
		// TODO
	}

	@Test
	public void testPlayerStatickBlockLeftCollision() {
		// TODO
	}

	@After
	public void reset() {
		bodies.clear();
		spells.clear();
		pairs.clear();
	}

}
