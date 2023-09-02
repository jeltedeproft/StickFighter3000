package jelte.mygame.tests.logic.ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

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
import jelte.mygame.logic.ai.VisionCollidable;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.EnemyFileReader;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestVisionCollidable {

	private PlayerCharacter player;
	private AiCharacter aiCharacter;
	private VisionCollidable visionCollidable;

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
		aiCharacter = new AiCharacter(EnemyFileReader.getUnitData().get(0), UUID.randomUUID(), new Vector2(0, 0), patrolPoints);
		visionCollidable = new VisionCollidable(aiCharacter);
	}

	@Test
	public void testConstructor() {
		assertNotNull(visionCollidable.getId());
		assertNotNull(visionCollidable.getRectangle());
		assertNotNull(visionCollidable.getOldRectangle());
		assertFalse(visionCollidable.hasMoved());
	}

	@Test
	public void testCreateVisionRectangleForCharacter() {
		aiCharacter.getData().setVisionShapeWidth(10);
		aiCharacter.getData().setVisionShapeHeight(20);
		aiCharacter.getPhysicsComponent().setPosition(new Vector2(5f, 5f));
		aiCharacter.getPhysicsComponent().setDirection(Direction.left);

		Rectangle expectedRectangle = new Rectangle(0f, 0f, 700f, 700f);

		assertEquals(expectedRectangle, visionCollidable.getRectangle());
	}

	@Test
	public void testUpdate() {
		aiCharacter.getPhysicsComponent().setPosition(new Vector2(5f, 5f));
		aiCharacter.getPhysicsComponent().setDirection(Direction.right);

		// Perform the update
		visionCollidable.update(aiCharacter);

		// Assert the changes in the vision collidable
		assertTrue(visionCollidable.hasMoved());
		assertEquals(5f, visionCollidable.getRectangle().x, 0.001f);
		assertEquals(5f, visionCollidable.getRectangle().y, 0.001f);
	}

	@Test
	public void testIsStatic() {
		assertFalse(visionCollidable.isStatic());
	}

	@Test
	public void testIsDynamic() {
		assertTrue(visionCollidable.isDynamic());
	}

	@Test
	public void testGetType() {
		assertEquals(Collidable.COLLIDABLE_TYPE.VISION, visionCollidable.getType());
	}
}
