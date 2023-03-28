package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapManager implements Disposable {
	private TiledMap currentMap;
	private OrthogonalTiledMapRenderer renderer;
	private MapProperties mapProperties;
	private float currentMapWidth;
	private float currentMapHeight;
	private Array<Rectangle> blockingRectangles;

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

	public Array<Rectangle> getRectanglesFromObjectLayer(MapLayer objectLayer) {
		Array<Rectangle> rectangles = new Array<>();

		for (MapObject object : objectLayer.getObjects()) {
			if (object instanceof RectangleMapObject) {
				RectangleMapObject rectangleObject = (RectangleMapObject) object;
				Rectangle rectangle = rectangleObject.getRectangle();
				rectangle.y = currentMapHeight - rectangle.y - rectangle.height;
				rectangles.add(rectangle);
			}
		}

		return rectangles;
	}

}
