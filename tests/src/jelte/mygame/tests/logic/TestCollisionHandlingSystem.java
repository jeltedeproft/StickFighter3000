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
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.logic.collisions.CollisionHandlingSystem;
import jelte.mygame.logic.collisions.CollisionPair;
import jelte.mygame.logic.spells.spells.Spell;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;

@RunWith(GdxTestRunner.class)
public class TestCollisionHandlingSystem {
	private static CollisionHandlingSystem collisionhandlingSystem;
	private static Array<Character> bodies;
	private static Array<Spell> spells;
	private static Set<CollisionPair> pairs;

	@BeforeClass
	public static void beforeAllTests() {
		PlayerFileReader.loadUnitStatsInMemory(Constants.PLAYER_STATS_TEST_FILE_LOCATION);
		bodies = new Array<>();
		spells = new Array<>();
		pairs = new HashSet<>();
		collisionhandlingSystem = new CollisionHandlingSystem();
	}

	private void addPlayer() {
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());
		bodies.add(player);
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
