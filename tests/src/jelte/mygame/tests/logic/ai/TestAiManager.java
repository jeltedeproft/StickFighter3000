package jelte.mygame.tests.logic.ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

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
import jelte.mygame.graphical.map.PatrolPoint;
import jelte.mygame.logic.ai.AiManager;
import jelte.mygame.logic.ai.strategy.AiStrategy;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.EnemyFileReader;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestAiManager {

	private PlayerCharacter player;
	private AiCharacter aiCharacter1;
	private AiCharacter aiCharacter2;

	@Mock
	private MusicManagerInterface mockMusicManager;

	@BeforeClass
	public static void beforeAllTests() {
		PlayerFileReader.loadUnitStatsInMemory(Constants.PLAYER_STATS_TEST_FILE_LOCATION);
		EnemyFileReader.loadUnitStatsInMemory(Constants.ENEMY_STATS_TEST_FILE_LOCATION);
		ApplicationLogger logger = new MultiFileLogger();
		Gdx.app.setApplicationLogger(logger);
		AssetManagerUtility.loadTextureAtlas(Constants.SPRITES_ATLAS_PATH);
	}

	@Before
	public void beforeEverytest() {
		MockitoAnnotations.openMocks(this);
		MusicManager.setInstance(mockMusicManager);
		Array<PatrolPoint> patrolPoints = new Array<>();
		patrolPoints.add(new PatrolPoint(new Vector2(0, 0), "0"));
		player = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());
		aiCharacter1 = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);
		aiCharacter2 = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);
	}

	@Test
	public void testAddEnemies() {
		Array<AiCharacter> enemies = new Array<>();
		enemies.add(aiCharacter1);
		enemies.add(aiCharacter2);

		AiManager aiManager = new AiManager();
		aiManager.addEnemies(enemies);

		assertEquals(2, aiManager.getCharactersWithStrategies().size());
		assertTrue(aiManager.getCharactersWithStrategies().containsKey(aiCharacter1));
		assertTrue(aiManager.getCharactersWithStrategies().containsKey(aiCharacter2));
	}

	@Test
	public void testUpdate() {
		AiStrategy strategy = mock(AiStrategy.class);
		Set<Collidable> collidables = mock(Set.class);

		AiManager aiManager = new AiManager();
		aiManager.getCharactersWithStrategies().put(aiCharacter1, strategy);

		aiManager.update(0.5f, player, collidables);

		verify(strategy).getNextState(0.5f, aiCharacter1, player);
		verify(strategy).generateCommand(0.5f, aiCharacter1, player);
	}

	// Add more test cases for other methods as needed

}
