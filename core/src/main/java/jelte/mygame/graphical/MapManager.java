package jelte.mygame.graphical;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.map.EnemySpawnData;
import jelte.mygame.graphical.map.PatrolPoint;
import jelte.mygame.logic.collisions.collidable.StaticBlock;
import jelte.mygame.logic.collisions.collidable.StaticBlockBot;
import jelte.mygame.logic.collisions.collidable.StaticBlockLeft;
import jelte.mygame.logic.collisions.collidable.StaticBlockPlatform;
import jelte.mygame.logic.collisions.collidable.StaticBlockRight;
import jelte.mygame.logic.collisions.collidable.StaticBlockTop;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapManager implements Disposable {
	private static final String TAG = MapManager.class.getSimpleName();
	private TiledMap currentMap;
	private OrthogonalTiledMapRenderer renderer;
	private MapProperties mapProperties;
	private float currentMapWidth;
	private float currentMapHeight;
	private Set<StaticBlock> blockingRectangles;
	private Collection<EnemySpawnData> enemySpawnData;

	public MapManager(SpriteBatch batch) {
		AssetManagerUtility.loadMapAsset(Constants.DEFAULT_MAP_PATH);
		currentMap = AssetManagerUtility.getMapAsset(Constants.DEFAULT_MAP_PATH);
		renderer = new OrthogonalTiledMapRenderer(currentMap, Constants.MAP_UNIT_SCALE, batch);
		mapProperties = currentMap.getProperties();
		currentMapWidth = mapProperties.get("width", Integer.class) * mapProperties.get("tilewidth", Integer.class);
		currentMapHeight = mapProperties.get("height", Integer.class) * mapProperties.get("tileheight", Integer.class);
		blockingRectangles = getStatickBlocksFromObjectLayer(currentMap.getLayers().get(Constants.LAYER_NAME_BLOCK));
		enemySpawnData = initEnemySpawnData();
	}

	public void changemap(String mapPath) {
		AssetManagerUtility.loadMapAsset(mapPath);
		currentMap = AssetManagerUtility.getMapAsset(mapPath);
		renderer.setMap(currentMap);
	}

	public void renderCurrentMap(OrthographicCamera camera) {
		renderer.setView(camera);// TODO optimize, only if camera changes
		renderer.render();
	}

	@Override
	public void dispose() {
		renderer.dispose();
	}

	public Set<StaticBlock> getStatickBlocksFromObjectLayer(MapLayer objectLayer) {
		Set<StaticBlock> rectangles = new HashSet<>();

		for (MapObject object : objectLayer.getObjects()) {
			if (object instanceof RectangleMapObject rectangleObject) {
				StaticBlock rectangle = createTypedStaticBlock(rectangleObject);
				rectangles.add(rectangle);
			}
		}

		return rectangles;
	}

	private Collection<EnemySpawnData> initEnemySpawnData() {
		MapObjects spawnObjects = currentMap.getLayers().get(Constants.LAYER_NAME_SPAWN).getObjects();
		MapObjects patrolObjects = currentMap.getLayers().get(Constants.LAYER_NAME_PATROL).getObjects();
		Map<String, EnemySpawnData> enemyDatas = new HashMap<>(spawnObjects.getCount());

		for (MapObject spawnObject : spawnObjects) {
			if (spawnObject instanceof RectangleMapObject rectangleObject) {
				String index = (String) spawnObject.getProperties().get(Constants.PROPERTY_DUPLICATE_INDEX);
				String type = (String) spawnObject.getProperties().get(Constants.PROPERTY_ENTITY_TYPE_INDEX);
				EnemySpawnData data = new EnemySpawnData();
				data.setSpawnPoint(new Vector2(rectangleObject.getRectangle().x, rectangleObject.getRectangle().y));
				data.setType(type);
				data.setPatrolPoints(new Array<>());
				enemyDatas.put(type + index, data);
			}
		}

		for (MapObject patrolObject : patrolObjects) {
			if (patrolObject instanceof RectangleMapObject rectangleObject) {
				String index = (String) patrolObject.getProperties().get(Constants.PROPERTY_DUPLICATE_INDEX);
				String type = (String) patrolObject.getProperties().get(Constants.PROPERTY_ENTITY_TYPE_INDEX);
				String pathIndex = (String) patrolObject.getProperties().get(Constants.PROPERTY_PATH_INDEX);
				EnemySpawnData data = enemyDatas.get(type + index);
				data.getPatrolPoints().add(new PatrolPoint(new Vector2(rectangleObject.getRectangle().x, rectangleObject.getRectangle().y), pathIndex));
				enemyDatas.put(type + index, data);
			}
		}

		return enemyDatas.values();
	}

	private StaticBlock createTypedStaticBlock(RectangleMapObject rectangleObject) {
		switch (rectangleObject.getName()) {
		case Constants.BLOCK_TYPE_TOP:
			return new StaticBlockTop(rectangleObject.getRectangle());
		case Constants.BLOCK_TYPE_BOT:
			return new StaticBlockBot(rectangleObject.getRectangle());
		case Constants.BLOCK_TYPE_LEFT:
			return new StaticBlockLeft(rectangleObject.getRectangle());
		case Constants.BLOCK_TYPE_RIGHT:
			return new StaticBlockRight(rectangleObject.getRectangle());
		case Constants.BLOCK_TYPE_PLATFORM:
			return new StaticBlockPlatform(rectangleObject.getRectangle());
		default:
			Gdx.app.debug(TAG, String.format("error: unknown type of static block: %s", rectangleObject.getName()));
			break;
		}

		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("currentMapWidth : ");
		sb.append(currentMapWidth);
		sb.append("\n");
		sb.append("currentMapHeight : ");
		sb.append(currentMapHeight);
		sb.append("\n");
		sb.append("mapName : ");
		sb.append(Constants.DEFAULT_MAP_PATH);
		sb.append("\n");

		return sb.toString();
	}

}
