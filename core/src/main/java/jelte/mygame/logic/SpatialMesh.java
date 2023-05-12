package jelte.mygame.logic;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.utility.Constants;

//creates squares inside the map, goal is to make collision detection more efficient
public class SpatialMesh {
	private float numberofCellsX;
	private float numberofCellsY;
	private Array<Collidable>[][] spatialMesh;

	public SpatialMesh(Vector2 mapBoundaries) {
		numberofCellsX = mapBoundaries.x / Constants.SPATIAL_MESH_CELL_SIZE;
		numberofCellsY = mapBoundaries.y / Constants.SPATIAL_MESH_CELL_SIZE;
	}

	public void initializeCollidables(Array<Collidable> collidables) {
		for (Collidable collidable : collidables) {
			addCollidable(collidable);
		}
	}

	public void addCollidable(Collidable collidable) {
		spatialMesh[getCellX(collidable)][getCellY(collidable)].add(collidable);
	}

	public void removeCollidable(Collidable collidable) {
		spatialMesh[getCellX(collidable)][getCellY(collidable)].removeValue(collidable, false);
	}

	public Array<Collidable> getCollidables(int x, int y) {
		return spatialMesh[getCellX(x)][getCellY(y)];
	}

	public void updateCollidable(Collidable collidable, Vector2 oldPosition) {
		final Iterator<Collidable> iterator = getCollidables((int) oldPosition.x, (int) oldPosition.y).iterator();
		while (iterator.hasNext()) {
			final Collidable currentCollidable = iterator.next();
			if (currentCollidable.equals(collidable)) {
				iterator.remove();
				break;
			}
		}
		addCollidable(collidable);
	}

	public Array<Array<Collidable>> getAllArraysOf2OrMoreCollidables() {
		// TODO keep track of cells who have filed arrays so that we only iterate over those, adds some overhead with insert and remove, but this gets called
		// every frame
		Array<Array<Collidable>> arrayOfArrayOfCollidables = new Array<>();

	}

	public int getCellX(int collidableX) {// TODO check for boundaries
		return collidableX >> (int) (Math.log(Constants.SPATIAL_MESH_CELL_SIZE) / Math.log(2));
	}

	public int getCellY(int collidableY) {
		return collidableY >> (int) (Math.log(Constants.SPATIAL_MESH_CELL_SIZE) / Math.log(2));
	}

	public int getCellX(Collidable collidable) {
		int x = (int) collidable.getRectangle().x;
		return x >> (int) (Math.log(Constants.SPATIAL_MESH_CELL_SIZE) / Math.log(2)); // Calculate the grid cell X index
	}

	public int getCellY(Collidable collidable) {
		int y = (int) collidable.getRectangle().y;
		return y >> (int) (Math.log(Constants.SPATIAL_MESH_CELL_SIZE) / Math.log(2)); // Calculate the grid cell Y index
	}

	public boolean isPositionInsideField(int x, int y) {
		return x < numberofCellsX && x >= 0 && y < numberofCellsY && y >= 0;
	}

	public boolean isXInsideField(int x) {
		return x < numberofCellsX && x >= 0;
	}

	public boolean isYInsideField(int y) {
		return y < numberofCellsY && y >= 0;
	}

}
