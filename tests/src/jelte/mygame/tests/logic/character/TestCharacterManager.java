package jelte.mygame.tests.logic.character;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.graphical.map.EnemySpawnData;
import jelte.mygame.graphical.map.PatrolPoint;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterManager;
import jelte.mygame.logic.character.EnemyFileReader;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestCharacterManager {

	private Collection<EnemySpawnData> spawnData;
	private Array<PatrolPoint> patrolPoints;

	@Mock
	private MusicManagerInterface mockMusicManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PlayerFileReader.loadUnitStatsInMemory(Constants.PLAYER_STATS_TEST_FILE_LOCATION);
		SpellFileReader.loadSpellsInMemory(Constants.SPELL_STATS_TEST_FILE_LOCATION);
		EnemyFileReader.loadUnitStatsInMemory(Constants.ENEMY_STATS_TEST_FILE_LOCATION);
		ApplicationLogger logger = new MultiFileLogger();
		Gdx.app.setApplicationLogger(logger);
	}

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		MusicManager.setInstance(mockMusicManager);

		// Create a collection of EnemySpawnData
		patrolPoints = new Array<>();
		patrolPoints.add(new PatrolPoint(new Vector2(0, 0), "0"));
		patrolPoints.add(new PatrolPoint(new Vector2(50, 50), "1"));
		EnemySpawnData spawnData1 = new EnemySpawnData();
		spawnData1.setSpawnPoint(new Vector2(0, 0));
		spawnData1.setType("0");
		spawnData1.setPatrolPoints(patrolPoints);
		EnemySpawnData spawnData2 = new EnemySpawnData();
		spawnData2.setSpawnPoint(new Vector2(0, 0));
		spawnData2.setType("0");
		spawnData2.setPatrolPoints(patrolPoints);

		spawnData = Arrays.asList(spawnData1, spawnData2);
	}

	@Test
	public void testSpawnEnemies() {
		// Create a PlayerCharacter instance for the CharacterManager
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());

		// Create a CharacterManager instance
		CharacterManager characterManager = new CharacterManager(player);

		// Call the spawnEnemies method
		characterManager.spawnEnemies(spawnData);

		// Assert the expected result after spawning enemies
		assertEquals(2, characterManager.getEnemies().size);
		assertEquals(3, characterManager.getBodies().size); // Player + 2 enemies
		assertEquals(3, characterManager.getAllCharacterbodies().size());
		assertEquals(3, characterManager.getAllCharacters().size);
	}

	@Test
	public void testAddEnemy() {
		// Create a PlayerCharacter instance for the CharacterManager
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());

		// Create a CharacterManager instance
		CharacterManager characterManager = new CharacterManager(player);

		// Create an AiCharacter instance to add as an enemy
		AiCharacter enemy = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);

		// Call the addEnemy method
		characterManager.addEnemy(enemy);

		// Assert the expected result after adding the enemy
		assertTrue(characterManager.getEnemies().contains(enemy, false));
		assertTrue(characterManager.getBodies().contains(enemy.getPhysicsComponent(), false));
	}

	@Test
	public void testRemoveEnemy() {
		// Create a PlayerCharacter instance for the CharacterManager
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());

		// Create a CharacterManager instance
		CharacterManager characterManager = new CharacterManager(player);

		// Create an AiCharacter instance to add as an enemy
		AiCharacter enemy = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);

		// Add the enemy to the CharacterManager
		characterManager.addEnemy(enemy);

		// Call the removeEnemy method
		characterManager.removeEnemy(enemy);

		// Assert the expected result after removing the enemy
		assertFalse(characterManager.getEnemies().contains(enemy, false));
		assertFalse(characterManager.getBodies().contains(enemy.getPhysicsComponent(), false));
	}

	@Test
	public void testGetCharacterById() {
		// Create a PlayerCharacter instance for the CharacterManager
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());

		// Create a CharacterManager instance
		CharacterManager characterManager = new CharacterManager(player);

		// Create an AiCharacter instance to add as an enemy
		UUID enemyId = UUID.randomUUID();
		AiCharacter enemy = new AiCharacter(EnemyFileReader.getUnitData().get(0), enemyId, new Vector2(0, 0), patrolPoints);

		// Add the enemy to the CharacterManager
		characterManager.addEnemy(enemy);

		// Call the getCharacterById method with the enemy's ID
		Character retrievedEnemy = characterManager.getCharacterById(enemyId);

		// Assert the expected result
		assertSame(enemy, retrievedEnemy);
	}

	@Test
	public void testGetAllCharacters() {
		// Create a PlayerCharacter instance for the CharacterManager
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());

		// Create a CharacterManager instance
		CharacterManager characterManager = new CharacterManager(player);

		// Create an AiCharacter instance to add as an enemy
		AiCharacter enemy1 = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);
		AiCharacter enemy2 = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);

		// Add the enemies to the CharacterManager
		characterManager.addEnemy(enemy1);
		characterManager.addEnemy(enemy2);

		// Call the getAllCharacters method
		Array<Character> allCharacters = characterManager.getAllCharacters();

		// Assert the expected result
		assertEquals(3, allCharacters.size); // Player + 2 enemies
		assertTrue(allCharacters.contains(player, true));
		assertTrue(allCharacters.contains(enemy1, true));
		assertTrue(allCharacters.contains(enemy2, true));
	}

	@Test
	public void testGetAllCharacterbodies() {
		// Create a PlayerCharacter instance for the CharacterManager
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());

		// Create a CharacterManager instance
		CharacterManager characterManager = new CharacterManager(player);

		// Create an AiCharacter instance to add as an enemy
		AiCharacter enemy1 = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);
		AiCharacter enemy2 = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);

		// Add the enemies to the CharacterManager
		characterManager.addEnemy(enemy1);
		characterManager.addEnemy(enemy2);

		// Call the getAllCharacterbodies method
		Set<Collidable> characterBodies = characterManager.getAllCharacterbodies();

		// Assert the expected result
		assertEquals(3, characterBodies.size()); // Player + 2 enemies
		assertTrue(characterBodies.contains(player.getPhysicsComponent()));
		assertTrue(characterBodies.contains(enemy1.getPhysicsComponent()));
		assertTrue(characterBodies.contains(enemy2.getPhysicsComponent()));
	}

	@Test
	public void testGetVisions() {
		// Create a PlayerCharacter instance for the CharacterManager
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());

		// Create a CharacterManager instance
		CharacterManager characterManager = new CharacterManager(player);

		// Create an AiCharacter instance to add as an enemy
		AiCharacter enemy1 = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);
		AiCharacter enemy2 = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);

		// Add the enemies to the CharacterManager
		characterManager.addEnemy(enemy1);
		characterManager.addEnemy(enemy2);

		// Call the getVisions method
		Set<Collidable> visions = characterManager.getVisions();

		// Assert the expected result
		assertEquals(2, visions.size());
		assertTrue(visions.contains(enemy1.getVisionCollidable()));
		assertTrue(visions.contains(enemy2.getVisionCollidable()));
	}

	@Test
	public void testUpdate() {
		// Create a PlayerCharacter instance for the CharacterManager
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());

		// Create a CharacterManager instance
		CharacterManager characterManager = new CharacterManager(player);

		// Create an AiCharacter instance to add as an enemy
		AiCharacter enemy1 = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);
		AiCharacter enemy2 = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);

		// Add the enemies to the CharacterManager
		characterManager.addEnemy(enemy1);
		characterManager.addEnemy(enemy2);

		// Call the update method
		characterManager.update(0.1f);

	}

	@Test
	public void testCheckCollision() {
		// Create a PlayerCharacter instance for the CharacterManager
		// Create a PlayerCharacter instance for the CharacterManager
		PlayerCharacter player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());

		// Create a CharacterManager instance
		CharacterManager characterManager = new CharacterManager(player);

		// Create an AiCharacter instance to add as an enemy
		AiCharacter enemy = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);

		// Add the enemy to the CharacterManager
		characterManager.addEnemy(enemy);

		// Set the enemy's physics component as collided
		enemy.getPhysicsComponent().setCollided(true);

		// Call the update method, which triggers checkCollision internally
		characterManager.update(0.1f);

		// Assert the expected result
		assertFalse(enemy.getPhysicsComponent().isCollided());
		// assertEquals(EVENT.NO_COLLISION, enemy.getCurrentCharacterState().getState());
	}

	// Add more test methods as needed to cover the behavior of the CharacterManager class

}
