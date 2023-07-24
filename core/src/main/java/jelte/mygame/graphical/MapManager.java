package jelte.mygame.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	private FrameBuffer minimapFrameBuffer;
	private float minimapZoomLevel;

	public MapManager(SpriteBatch batch) {
		this(batch, Constants.DEFAULT_MAP_PATH);
	}

	public MapManager(SpriteBatch batch, final String mapPath) {
		loadMapData(mapPath, batch);
	}

	public void changeMap(String mapPath) {
		loadMapData(mapPath, (SpriteBatch) renderer.getBatch());
	}

	private void loadMapData(String mapPath, SpriteBatch batch) {
		AssetManagerUtility.loadMapAsset(mapPath);
		currentMap = AssetManagerUtility.getMapAsset(mapPath);
		renderer = new OrthogonalTiledMapRenderer(currentMap, Constants.MAP_UNIT_SCALE, batch);
		mapProperties = currentMap.getProperties();
		currentMapWidth = mapProperties.get("width", Integer.class) * mapProperties.get("tilewidth", Integer.class);
		currentMapHeight = mapProperties.get("height", Integer.class) * mapProperties.get("tileheight", Integer.class);
		minimapFrameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, (int) Constants.VISIBLE_WIDTH, (int) Constants.VISIBLE_HEIGHT, false);
		// minimapZoomLevel = Math.min((int) Constants.VISIBLE_WIDTH / currentMapWidth, (int) Constants.VISIBLE_HEIGHT / currentMapHeight);
		minimapZoomLevel = 5f;
		blockingRectangles = extractStaticBlocksFromObjectLayer(currentMap.getLayers().get(Constants.LAYER_NAME_BLOCK));
		enemySpawnData = initializeEnemySpawnData();
	}

	public void renderCurrentMap(OrthographicCamera camera) {
		renderer.setView(camera);// TODO optimize, only if camera changes
		renderer.render();
	}

	@Override
	public void dispose() {
		renderer.dispose();
	}

	public Set<StaticBlock> extractStaticBlocksFromObjectLayer(MapLayer objectLayer) {
		Set<StaticBlock> rectangles = new HashSet<>();

		for (MapObject object : objectLayer.getObjects()) {
			if (object instanceof RectangleMapObject rectangleObject) {
				StaticBlock rectangle = createTypedStaticBlock(rectangleObject);
				rectangles.add(rectangle);
			}
		}

		return rectangles;
	}

	private Collection<EnemySpawnData> initializeEnemySpawnData() {
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
		String blockType = rectangleObject.getName();

		switch (blockType) {
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
			Gdx.app.debug(TAG, "Error: unknown type of static block: " + blockType);
			break;
		}

		return null;
	}

	public Texture getMinimaptexture(OrthographicCamera camera, Vector2 playerPosition) {
		// get old values camera
		float oldZoom = camera.zoom;
		Vector3 cameraOldPosition = camera.position;

		minimapFrameBuffer.begin();

		// Clear the screen (optional if you want a transparent background)
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.zoom = minimapZoomLevel;
		camera.position.set(currentMapWidth * 0.5f, currentMapHeight * 0.5f, 0); // Center the camera on the map;
		camera.update();

		// Render the TiledMap
		renderer.setView(camera); // Set the camera for the current view
		renderer.render();

		// Draw the dot at the player's position
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Constants.MINIMAP_DOT_COLOR);

		// Assuming you have the player's position as a Vector2
		Vector3 convertedPlayerPosition = camera.project(new Vector3(playerPosition.x, playerPosition.y, 0));
		shapeRenderer.circle(convertedPlayerPosition.x, convertedPlayerPosition.y, Constants.MINIMAP_DOT_SIZE);

		shapeRenderer.end();

		// Unbind the FrameBuffer
		minimapFrameBuffer.end();

		// Get the texture from the FrameBuffer
		Texture minimapTexture = minimapFrameBuffer.getColorBufferTexture();
		minimapTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		// Create a TextureRegion to flip the texture vertically
		TextureRegion minimapRegion = new TextureRegion(minimapTexture);

		// Reset old values for the camera
		camera.zoom = oldZoom;
		camera.position.set(cameraOldPosition.x, cameraOldPosition.y, cameraOldPosition.z);
		camera.update();
		renderer.setView(camera);

		return minimapRegion.getTexture();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("currentMapWidth: ").append(currentMapWidth).append("\n");
		sb.append("currentMapHeight: ").append(currentMapHeight).append("\n");
		sb.append("mapName: ").append(Constants.DEFAULT_MAP_PATH).append("\n");
		return sb.toString();
	}
}
