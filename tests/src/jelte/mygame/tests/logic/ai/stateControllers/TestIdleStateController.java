package jelte.mygame.tests.logic.ai.stateControllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.graphical.map.PatrolPoint;
import jelte.mygame.logic.ai.stateControllers.IdleStateController;
import jelte.mygame.logic.ai.strategy.AiStrategy.AI_STATE;
import jelte.mygame.logic.character.AiCharacter;
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
public class TestIdleStateController {

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
		self.getCharacterStateManager().transition(CHARACTER_STATE.ATTACKING);
	}

	@Test
	public void testGetNextStateIdling() {
		IdleStateController stateController = new IdleStateController();

		AI_STATE nextState = stateController.getNextState(0f, self, player);
		assertEquals(AI_STATE.IDLE, nextState);
	}

	@Test
	public void testGetNextStatePlayerSeen() {
		IdleStateController stateController = new IdleStateController();

		self.getVisionCollidable().setPlayerSeen(true);

		AI_STATE nextState = stateController.getNextState(0f, self, player);
		assertEquals(AI_STATE.CHASE, nextState);
	}

	@Test
	public void testGetNextStateTimeOut() {
		IdleStateController stateController = new IdleStateController();

		AI_STATE nextState = stateController.getNextState(10f, self, player);
		assertEquals(AI_STATE.PATROL, nextState);
	}

	@Test
	public void testGetNextCommandsFromThisStateAttacking() {
		IdleStateController stateController = new IdleStateController();
		assertNull(stateController.getNextCommandsFromThisState(0f, self, player));
	}
}
