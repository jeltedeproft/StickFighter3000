package jelte.mygame.tests.graphical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.MapManager;
import jelte.mygame.graphical.map.EnemySpawnData;
import jelte.mygame.logic.collisions.collidable.StaticBlock;
import jelte.mygame.logic.collisions.collidable.StaticBlockBot;
import jelte.mygame.logic.collisions.collidable.StaticBlockPlatform;
import jelte.mygame.logic.collisions.collidable.StaticBlockTop;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestMapManager {

	private MapManager mapManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationLogger logger = new MultiFileLogger();
		Gdx.app.setApplicationLogger(logger);
	}

	@Before
	public void setup() {
		mapManager = new MapManager(null, Constants.MAP_TEST_FILE_LOCATION); // Pass null for the SpriteBatch parameter
	}

	@Test
	public void testGetStaticBlocksFromObjectLayer() {
		MapLayer objectLayer = mapManager.getCurrentMap().getLayers().get(Constants.LAYER_NAME_BLOCK);

		// Assuming there are three RectangleMapObjects in the objectLayer
		assertEquals(3, objectLayer.getObjects().getCount());

		Set<StaticBlock> staticBlocks = mapManager.getStatickBlocksFromObjectLayer(objectLayer);

		// Assuming that the three RectangleMapObjects have names corresponding to StaticBlock subclasses
		assertEquals(3, staticBlocks.size());
		assertTrue(containsInstanceOf(staticBlocks, StaticBlockTop.class));
		assertTrue(containsInstanceOf(staticBlocks, StaticBlockBot.class));
		assertTrue(containsInstanceOf(staticBlocks, StaticBlockPlatform.class));
	}

	@Test
	public void testInitEnemySpawnData() {
		MapObjects spawnObjects = mapManager.getCurrentMap().getLayers().get(Constants.LAYER_NAME_SPAWN).getObjects();
		MapObjects patrolObjects = mapManager.getCurrentMap().getLayers().get(Constants.LAYER_NAME_PATROL).getObjects();

		// Assuming there are three spawn objects and two patrol objects in the map
		assertEquals(1, spawnObjects.getCount());
		assertEquals(1, patrolObjects.getCount());

		Collection<EnemySpawnData> enemySpawnData = mapManager.getEnemySpawnData();

		// Assuming that the spawn objects and patrol objects are properly processed
		assertEquals(1, enemySpawnData.size());

		for (EnemySpawnData data : enemySpawnData) {
			assertEquals(1, data.getPatrolPoints().size);
			assertEquals(new Vector2(387.584f, 290.9577f), data.getSpawnPoint());
		}
	}

	@Test
	public void testCreateTypedStaticBlock() {
		Set<StaticBlock> blocks = mapManager.getBlockingRectangles();
		assertEquals(3, blocks.size());
	}

	@Test
	public void testChangeMap() {
		// Load the initial map
		AssetManagerUtility.loadMapAsset(Constants.DEFAULT_MAP_PATH);
		TiledMap initialMap = AssetManagerUtility.getMapAsset(Constants.DEFAULT_MAP_PATH);

		// Create a mock SpriteBatch for the MapManager constructor
		SpriteBatch spriteBatchMock = mock(SpriteBatch.class);

		// Create the MapManager instance and set the initial map
		MapManager mapManager = new MapManager(spriteBatchMock);
		mapManager.setCurrentMap(initialMap);

		// Verify that the initial map is set correctly
		assertSame(initialMap, mapManager.getCurrentMap());

		// Load the new map
		AssetManagerUtility.loadMapAsset(Constants.DARK1_MAP_PATH);
		TiledMap newMap = AssetManagerUtility.getMapAsset(Constants.DARK1_MAP_PATH);

		// Change the map
		mapManager.changemap(Constants.DARK1_MAP_PATH);

		// Verify that the map is changed correctly
		assertSame(newMap, mapManager.getCurrentMap());
		assertNotSame(initialMap, mapManager.getCurrentMap());
	}

	private boolean containsInstanceOf(Set<StaticBlock> staticBlocks, Class<? extends StaticBlock> clazz) {
		for (StaticBlock block : staticBlocks) {
			if (clazz.isInstance(block)) {
				return true;
			}
		}
		return false;
	}

}
