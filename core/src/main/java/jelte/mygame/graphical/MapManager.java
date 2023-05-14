package jelte.mygame.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import jelte.mygame.logic.StaticBlock;
import jelte.mygame.logic.StaticBlockBot;
import jelte.mygame.logic.StaticBlockLeft;
import jelte.mygame.logic.StaticBlockPlatform;
import jelte.mygame.logic.StaticBlockRight;
import jelte.mygame.logic.StaticBlockTop;
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
	private Array<StaticBlock> blockingRectangles;

	public MapManager(SpriteBatch batch) {
		AssetManagerUtility.loadMapAsset(Constants.DEFAULT_MAP_PATH);
		currentMap = AssetManagerUtility.getMapAsset(Constants.DEFAULT_MAP_PATH);
		renderer = new OrthogonalTiledMapRenderer(currentMap, Constants.MAP_UNIT_SCALE, batch);
		mapProperties = currentMap.getProperties();
		currentMapWidth = mapProperties.get("width", Integer.class) * mapProperties.get("tilewidth", Integer.class);
		currentMapHeight = mapProperties.get("height", Integer.class) * mapProperties.get("tileheight", Integer.class);
		MapLayer objectLayer = currentMap.getLayers().get("blocks");
		blockingRectangles = getRectanglesFromObjectLayer(objectLayer);
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

	public Array<StaticBlock> getRectanglesFromObjectLayer(MapLayer objectLayer) {
		Array<StaticBlock> rectangles = new Array<>();

		for (MapObject object : objectLayer.getObjects()) {
			if (object instanceof RectangleMapObject) {
				RectangleMapObject rectangleObject = (RectangleMapObject) object;
				StaticBlock rectangle = createTypedStaticBlock(rectangleObject);
				rectangles.add(rectangle);
			}
		}

		return rectangles;
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
			Gdx.app.log(TAG, "error : unknown type of static block : " + rectangleObject.getName());
			break;
		}

		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("currentMapWidth");
		sb.append(" --> ");
		sb.append(currentMapWidth);
		sb.append("\n");
		sb.append("currentMapHeight");
		sb.append(" --> ");
		sb.append(currentMapHeight);
		sb.append("\n");
		sb.append("mapName");
		sb.append(" --> ");
		sb.append(Constants.DEFAULT_MAP_PATH);
		sb.append("\n");

		return sb.toString();
	}

}
