package jelte.mygame.tests.utility;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.UUID;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.graphical.map.PatrolPoint;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.EnemyFileReader;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.AiUtility;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestAiUtility {

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
	public void testGenerateMoveInputToGoal() {
		Vector2 aiPosition = new Vector2(0, 0);
		Vector2 goal = new Vector2(10, 0);
		Direction currentDirection = Direction.left;

		aiCharacter1.getPhysicsComponent().setPosition(aiPosition);
		aiCharacter1.getPhysicsComponent().setDirection(currentDirection);

		Array<Message> input = AiUtility.generateMoveInputToGoal(aiCharacter1, goal);

		assertEquals(2, input.size);
		assertEquals(new Message(RECIPIENT.LOGIC, ACTION.LEFT_UNPRESSED), input.get(0));
		assertEquals(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_PRESSED), input.get(1));
	}

	@Test
	public void testGenerateMoveInputAwayFromGoal() {
		Vector2 aiPosition = new Vector2(10, 0);
		Vector2 goal = new Vector2(0, 0);
		Direction currentDirection = Direction.left;

		aiCharacter1.getPhysicsComponent().setPosition(aiPosition);
		aiCharacter1.getPhysicsComponent().setDirection(currentDirection);

		Array<Message> input = AiUtility.generateMoveInputAwayFromGoal(aiCharacter1, goal);

		assertEquals(2, input.size);
		assertEquals(new Message(RECIPIENT.LOGIC, ACTION.LEFT_UNPRESSED), input.get(0));
		assertEquals(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_PRESSED), input.get(1));
	}
}
