package jelte.mygame.logic.collisions;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.utility.Constants;
import jelte.mygame.utility.exceptions.OutOfBoundsException;
import lombok.Getter;

//creates squares inside the map, goal is to make collision detection more efficient
@Getter
public class SpatialMesh {
	private static final String TAG = SpatialMesh.class.getSimpleName();
	private int numberofCellsX;
	private int numberofCellsY;
	private float mapWidth;
	private float mapHeight;
	private SpatialMeshCell[][] spatialMesh;
	private Set<Point> cellsWithDynamicCollidables;

	public SpatialMesh(Vector2 mapBoundaries) {
		mapWidth = mapBoundaries.x;
		mapHeight = mapBoundaries.y;
		cellsWithDynamicCollidables = new HashSet<>();
		numberofCellsX = (int) Math.ceil(mapBoundaries.x / Constants.SPATIAL_MESH_CELL_SIZE);
		numberofCellsY = (int) Math.ceil(mapBoundaries.y / Constants.SPATIAL_MESH_CELL_SIZE);
		spatialMesh = new SpatialMeshCell[numberofCellsX][numberofCellsY];
		for (int x = 0; x < numberofCellsX; x++) {
			for (int y = 0; y < numberofCellsY; y++) {
				spatialMesh[x][y] = new SpatialMeshCell();
			}
		}
	}

	public void addCollidables(Array<Collidable> collidables) {
		for (Collidable collidable : collidables) {
			addCollidable(collidable);
		}
	}

	public void addCollidable(Collidable collidable) {
		Rectangle rect = collidable.getRectangle();
		Set<Point> collidedPoints = getCollidingCells(rect);
		for (Point point : collidedPoints) {
			spatialMesh[point.x][point.y].addCollidable(collidable);
			if (collidable.isDynamic()) {
				cellsWithDynamicCollidables.add(point);
			}
		}
	}

	public Set<Point> getCollidingCells(Rectangle rect) {
		Set<Point> collidingCells = new HashSet<>();

		int startCol = (int) Math.floor((double) rect.x / Constants.SPATIAL_MESH_CELL_SIZE);
		int endCol = (int) Math.ceil((double) (rect.x + rect.width) / Constants.SPATIAL_MESH_CELL_SIZE) - 1;
		int startRow = (int) Math.floor((double) rect.y / Constants.SPATIAL_MESH_CELL_SIZE);
		int endRow = (int) Math.ceil((double) (rect.y + rect.height) / Constants.SPATIAL_MESH_CELL_SIZE) - 1;

		for (int row = startRow; row <= endRow; row++) {
			for (int col = startCol; col <= endCol; col++) {
				if (row >= 0 && row < numberofCellsY && col >= 0 && col < numberofCellsX) {
					collidingCells.add(new Point(col, row));
				}
			}
		}

		return collidingCells;
	}

	public void removeAllCollidables() {
		for (int x = 0; x < numberofCellsX; x++) {
			for (int y = 0; y < numberofCellsY; y++) {
				spatialMesh[x][y].removeAll();
			}
		}
	}

	public void removeDynamicCollidable(Collidable collidable) {
		Rectangle rect = collidable.getRectangle();
		Set<Point> collidedPoints = getCollidingCells(rect);
		removeCollidableFrom(collidable, collidedPoints);
	}

	public void removeStaticCollidable(Collidable collidable) {
		Rectangle rect = collidable.getRectangle();
		Set<Point> collidedPoints = getCollidingCells(rect);
		removeCollidableFrom(collidable, collidedPoints);
	}

	private void removeCollidableFrom(Collidable collidable, Set<Point> collidedPoints) {
		for (Point point : collidedPoints) {
			Gdx.app.log(TAG, "removing from : " + point);
			spatialMesh[point.x][point.y].removeCollidable(collidable);
			if (!spatialMesh[point.x][point.y].isContainsDynamic()) {
				cellsWithDynamicCollidables.remove(new Point(point.x, point.y));
			}
		}
	}

	public Set<Collidable> getDynamicCollidables(int x, int y) {
		return spatialMesh[getCellX(x)][getCellY(y)].getDynamicCollidables();
	}

	public Set<Collidable> getStaticCollidables(int x, int y) {
		return spatialMesh[getCellX(x)][getCellY(y)].getStaticCollidables();
	}

	public void updateCollidables(Array<Collidable> collidables) {
		for (Collidable collidable : collidables) {
			if (collidable.hasMoved()) {
				updateCollidable(collidable);
			}
		}
	}

	public void updateCollidable(Collidable collidable) {
		// old cells
		Vector2 oldPosition = collidable.getOldPosition();
		Rectangle oldRect = new Rectangle(collidable.getRectangle());
		oldRect.setPosition(oldPosition);
		Set<Point> oldCollidedPoints = getCollidingCells(oldRect);
		// new cells
		Set<Point> newCollidedPoints = getCollidingCells(collidable.getRectangle());
		if (!oldCollidedPoints.equals(newCollidedPoints)) {
			removeCollidableFrom(collidable, oldCollidedPoints);
			addCollidable(collidable);
		}
	}

	public Array<CollisionData> getAllPossibleCollisions() {
		Array<CollisionData> collisionDatas = new Array<>();
		// Gdx.app.log(TAG, "cells with dynamic : " + cellsWithDynamicCollidables);

		for (Point point : cellsWithDynamicCollidables) {
			Set<Collidable> dynamicCollidables = spatialMesh[point.x][point.y].getDynamicCollidables();
			Set<Collidable> staticCollidables = spatialMesh[point.x][point.y].getStaticCollidables();
			int totalSize = dynamicCollidables.size() + staticCollidables.size();
			if (totalSize >= 2) {
				CollisionData collisionData = new CollisionData(dynamicCollidables, staticCollidables);
				collisionDatas.add(collisionData);
			}
		}
		return collisionDatas;
	}

	public int getCellX(int collidableX) {
		if (collidableX < 0) {
			throw new OutOfBoundsException(collidableX + " is a negative number, can't get spatialMeshCell");
		}
		if (collidableX >= mapWidth) {
			throw new OutOfBoundsException(collidableX + " is bigger than map size " + mapWidth + ", can't get spatialMeshCell");
		}
		return collidableX >> (int) (Math.log(Constants.SPATIAL_MESH_CELL_SIZE) / Math.log(2));
	}

	public int getCellY(int collidableY) {
		if (collidableY < 0) {
			throw new OutOfBoundsException(collidableY + " is a negative number, can't get spatialMeshCell");
		}
		if (collidableY >= mapHeight) {
			throw new OutOfBoundsException(collidableY + " is bigger than map size " + mapHeight + ", can't get spatialMeshCell");
		}
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("cells X");
		sb.append(" --> ");
		sb.append(numberofCellsX);
		sb.append("\n");
		sb.append("cells Y");
		sb.append(" --> ");
		sb.append(numberofCellsY);
		sb.append("\n");
		for (int i = 0; i < spatialMesh.length; i++) {
			for (int j = 0; j < spatialMesh[i].length; j++) {
				sb.append("dynamic(");
				sb.append(spatialMesh[i][j].getDynamicCollidables().size());
				sb.append(") static(");
				sb.append(spatialMesh[i][j].getStaticCollidables().size());
				sb.append(")\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}