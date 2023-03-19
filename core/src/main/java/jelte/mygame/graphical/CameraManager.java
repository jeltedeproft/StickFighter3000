package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraManager {
	private OrthographicCamera camera;
	private Vector2 cameraPosition;

	public CameraManager(Camera camera) {
		this.camera = (OrthographicCamera) camera;
		cameraPosition = new Vector2(0, 0);
	}

	public void update(float mapWidth, float mapHeight, float viewportWidth, float viewportHeight) {
		camera.position.x = MathUtils.clamp(cameraPosition.x, viewportWidth / 2, mapWidth - (viewportWidth / 2));
		camera.position.y = MathUtils.clamp(cameraPosition.y, viewportHeight / 2, mapHeight - (viewportHeight / 2));
		camera.update();
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void moveCamera(float moveX, float moveY) {
		camera.translate(moveX, moveY, 0);
		camera.update();
	}

	public void zoomCamera(float value) {
		camera.zoom += value;
		camera.update();
	}

	public void updateCameraPos(Vector2 value) {
		cameraPosition = value;
	}

}
