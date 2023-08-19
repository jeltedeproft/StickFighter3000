package jelte.mygame.tests.logic.ai.stateControllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.ai.strategy.basic.stateControllers.BasicFleeStateController;
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
public class TestFleeStateController {

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
		self.getCharacterStateManager().pushState(CHARACTER_STATE.IDLE);
	}

	@Test
	public void testGetNextStateFleeing() {
		player.getPhysicsComponent().setPosition(new Vector2(0, 0));
		self.getPhysicsComponent().setPosition(new Vector2(50, 50));

		BasicFleeStateController stateController = new BasicFleeStateController();

		AI_STATE nextState = stateController.getNextState(0f, self, player);
		assertEquals(AI_STATE.FLEE, nextState);
	}

	@Test
	public void testGetNextStateFleeingFarEnoughForStopFleeing() {
		BasicFleeStateController stateController = new BasicFleeStateController();
		player.getPhysicsComponent().setPosition(new Vector2(0, 0));
		self.getPhysicsComponent().setPosition(new Vector2(BasicFleeStateController.FLEE_SAFETY_DISTANCE + 1, 0));

		AI_STATE nextState = stateController.getNextState(0f, self, player);
		assertEquals(AI_STATE.PATROL, nextState);
	}

	@Test
	public void testNeedToChangeDirectionLeft() {
		player.getPhysicsComponent().setPosition(new Vector2(0, 0));
		self.getPhysicsComponent().setPosition(new Vector2(50, 50));
		self.getPhysicsComponent().setDirection(Direction.left);
		BasicFleeStateController stateController = new BasicFleeStateController();

		Message expectedMessage = new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP);
		assertEquals(expectedMessage, stateController.getNextCommand(0f, self, player));

		InputBox inputBox = (InputBox) expectedMessage.getValue();
		assertTrue(inputBox.isPressed(BUTTONS.LEFT));
		assertFalse(inputBox.isPressed(BUTTONS.RIGHT));
	}

	@Test
	public void testDontNeedToChangeDirectionLeft() {
		player.getPhysicsComponent().setPosition(new Vector2(0, 0));
		self.getPhysicsComponent().setPosition(new Vector2(50, 50));
		self.getPhysicsComponent().setDirection(Direction.right);
		BasicFleeStateController stateController = new BasicFleeStateController();

		Array<Message> expectedMessages = new Array<>();
		assertEquals(expectedMessages, stateController.getNextCommand(0f, self, player));
	}

	@Test
	public void testNeedToChangeDirectionRight() {
		player.getPhysicsComponent().setPosition(new Vector2(50, 50));
		self.getPhysicsComponent().setPosition(new Vector2(0, 0));
		self.getPhysicsComponent().setDirection(Direction.right);
		BasicFleeStateController stateController = new BasicFleeStateController();

		Message expectedMessage = new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP);
		assertEquals(expectedMessage, stateController.getNextCommand(0f, self, player));

		InputBox inputBox = (InputBox) expectedMessage.getValue();
		assertTrue(inputBox.isPressed(BUTTONS.RIGHT));
		assertFalse(inputBox.isPressed(BUTTONS.LEFT));
	}

	@Test
	public void testDontNeedToChangeDirectionRight() {
		player.getPhysicsComponent().setPosition(new Vector2(50, 50));
		self.getPhysicsComponent().setPosition(new Vector2(0, 0));
		self.getPhysicsComponent().setDirection(Direction.left);
		BasicFleeStateController stateController = new BasicFleeStateController();

		Array<Message> expectedMessages = new Array<>();
		assertEquals(expectedMessages, stateController.getNextCommand(0f, self, player));
	}

}
