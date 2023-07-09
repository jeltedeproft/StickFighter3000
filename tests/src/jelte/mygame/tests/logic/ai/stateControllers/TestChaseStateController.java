package jelte.mygame.tests.logic.ai.stateControllers;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.graphical.map.PatrolPoint;
import jelte.mygame.logic.ai.stateControllers.ChaseStateController;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.EnemyFileReader;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestChaseStateController {

	private PlayerCharacter player;
	private AiCharacter self;

	@Mock
	private MusicManagerInterface mockMusicManager;

	@BeforeClass
	public static void beforeAllTests() {
		PlayerFileReader.loadUnitStatsInMemory(Constants.PLAYER_STATS_TEST_FILE_LOCATION);
		EnemyFileReader.loadUnitStatsInMemory(Constants.ENEMY_STATS_TEST_FILE_LOCATION);
		SpellFileReader.loadSpellsInMemory(Constants.SPELL_STATS_TEST_FILE_LOCATION);
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
		self = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);
		self.getCharacterStateManager().transition(CHARACTER_STATE.IDLE);
	}

	@Test
	public void testGetNextStateChasing() {
		player.getPhysicsComponent().setPosition(new Vector2(0, 0));
		self.getPhysicsComponent().setPosition(new Vector2(50, 50));

		ChaseStateController stateController = new ChaseStateController();

		AI_STATE nextState = stateController.getNextState(0f, self, player);
		assertEquals(AI_STATE.CHASE, nextState);
	}

	@Test
	public void testGetNextStateChasingCloseEnoughForAttack() {
		player.getPhysicsComponent().setPosition(new Vector2(0, 0));
		self.getPhysicsComponent().setPosition(new Vector2(2, 2));
		ChaseStateController stateController = new ChaseStateController();

		AI_STATE nextState = stateController.getNextState(0f, self, player);
		assertEquals(AI_STATE.ATTACK, nextState);
	}

	@Test
	public void testGetNextStateChasingFleeWhenLowHp() {
		player.getPhysicsComponent().setPosition(new Vector2(0, 0));
		self.getPhysicsComponent().setPosition(new Vector2(50, 50));
		self.damage(self.getCurrentHp() - 2);
		ChaseStateController stateController = new ChaseStateController();

		AI_STATE nextState = stateController.getNextState(0f, self, player);
		assertEquals(AI_STATE.FLEE, nextState);
	}

	@Test
	public void testNeedToChangeDirectionLeft() {
		player.getPhysicsComponent().setPosition(new Vector2(0, 0));
		self.getPhysicsComponent().setPosition(new Vector2(50, 50));
		self.getPhysicsComponent().setDirection(Direction.right);
		ChaseStateController stateController = new ChaseStateController();

		Array<Message> expectedMessages = new Array<>();
		expectedMessages.add(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_UNPRESSED));
		expectedMessages.add(new Message(RECIPIENT.LOGIC, ACTION.LEFT_PRESSED));
		assertEquals(expectedMessages, stateController.getNextCommandsFromThisState(0f, self, player));
	}

	@Test
	public void testDontNeedToChangeDirectionLeft() {
		player.getPhysicsComponent().setPosition(new Vector2(0, 0));
		self.getPhysicsComponent().setPosition(new Vector2(50, 50));
		self.getPhysicsComponent().setDirection(Direction.left);
		ChaseStateController stateController = new ChaseStateController();

		Array<Message> expectedMessages = new Array<>();
		assertEquals(expectedMessages, stateController.getNextCommandsFromThisState(0f, self, player));
	}

	@Test
	public void testNeedToChangeDirectionRight() {
		player.getPhysicsComponent().setPosition(new Vector2(50, 50));
		self.getPhysicsComponent().setPosition(new Vector2(0, 0));
		self.getPhysicsComponent().setDirection(Direction.left);
		ChaseStateController stateController = new ChaseStateController();

		Array<Message> expectedMessages = new Array<>();
		expectedMessages.add(new Message(RECIPIENT.LOGIC, ACTION.LEFT_UNPRESSED));
		expectedMessages.add(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_PRESSED));
		assertEquals(expectedMessages, stateController.getNextCommandsFromThisState(0f, self, player));
	}

	@Test
	public void testDontNeedToChangeDirectionRight() {
		player.getPhysicsComponent().setPosition(new Vector2(50, 50));
		self.getPhysicsComponent().setPosition(new Vector2(0, 0));
		self.getPhysicsComponent().setDirection(Direction.right);
		ChaseStateController stateController = new ChaseStateController();

		Array<Message> expectedMessages = new Array<>();
		assertEquals(expectedMessages, stateController.getNextCommandsFromThisState(0f, self, player));
	}

}
