package jelte.mygame.tests.graphical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;

@RunWith(GdxTestRunner.class)
public class TestTiledMapProcessing {

	private Array<TiledMap> maps = new Array<>();

	@Before
	public void beforeEveryTest() {
		TmxMapLoader mapLoader = new TmxMapLoader();

		String folderPath = Gdx.files.internal("../assets/map/dark").file().getAbsolutePath();
		File folder = new File(folderPath);
		File[] files = folder.listFiles((dir, name) -> name.endsWith(".tmx"));

		for (File file : files) {
			TiledMap tiledMap = mapLoader.load(file.getPath());
			maps.add(tiledMap);
		}

	}

	@Test
	public void testTiledMapProperties() {
		for (TiledMap map : maps) {
			MapProperties mapProperties = map.getProperties();
			assertTrue(mapProperties.containsKey("width"));
			assertTrue(mapProperties.containsKey("tilewidth"));
			assertTrue(mapProperties.containsKey("height"));
			assertTrue(mapProperties.containsKey("tileheight"));
			assertTrue(mapProperties.containsKey("hexsidelength"));
			assertTrue(mapProperties.containsKey("orientation"));
			assertEquals(mapProperties.get("orientation"), "orthogonal");
		}
	}

	@Test
	public void testTiledMapLayersExist() {
		for (TiledMap map : maps) {
			assertNotNull(map.getLayers().get(Constants.LAYER_NAME_BLOCK));
			assertNotNull(map.getLayers().get(Constants.LAYER_NAME_PATROL));
			assertNotNull(map.getLayers().get(Constants.LAYER_NAME_SPAWN));
		}
	}

	@Test
	public void testTiledMapContainsAtLeastOneBlock() {
		for (TiledMap map : maps) {
			assertNotNull(map.getLayers().get(Constants.LAYER_NAME_BLOCK));
			MapObjects objects = map.getLayers().get(Constants.LAYER_NAME_BLOCK).getObjects();
			assertTrue("Array should not be empty", objects != null && objects.getCount() > 0);
		}
	}

	@Test
	public void testCorrectSpawnIfPresent() {
		for (TiledMap map : maps) {
			if (map.getLayers().get(Constants.LAYER_NAME_SPAWN) != null) {
				MapObjects spawnObjects = map.getLayers().get(Constants.LAYER_NAME_SPAWN).getObjects();
				for (MapObject spawnObject : spawnObjects) {
					assertTrue(spawnObject instanceof RectangleMapObject);
					assertTrue(spawnObject.getProperties().containsKey(Constants.PROPERTY_DUPLICATE_INDEX));
					assertTrue(spawnObject.getProperties().containsKey(Constants.PROPERTY_ENTITY_TYPE_INDEX));
					RectangleMapObject rectangle = (RectangleMapObject) spawnObject;
					assertTrue(rectangle.getRectangle().x >= 0);
					assertTrue(rectangle.getRectangle().y >= 0);
				}
			}
		}
	}

	@Test
	public void testCorrectPatrolIfPresent() {
		for (TiledMap map : maps) {
			if (map.getLayers().get(Constants.LAYER_NAME_PATROL) != null) {
				MapObjects patrolObjects = map.getLayers().get(Constants.LAYER_NAME_PATROL).getObjects();
				for (MapObject patrolObject : patrolObjects) {
					assertTrue(patrolObject instanceof RectangleMapObject);
					assertTrue(patrolObject.getProperties().containsKey(Constants.PROPERTY_DUPLICATE_INDEX));
					assertTrue(patrolObject.getProperties().containsKey(Constants.PROPERTY_ENTITY_TYPE_INDEX));
					assertTrue(patrolObject.getProperties().containsKey(Constants.PROPERTY_PATH_INDEX));
					RectangleMapObject rectangle = (RectangleMapObject) patrolObject;
					assertTrue(rectangle.getRectangle().x >= 0);
					assertTrue(rectangle.getRectangle().y >= 0);
				}
			}
		}
	}

}
