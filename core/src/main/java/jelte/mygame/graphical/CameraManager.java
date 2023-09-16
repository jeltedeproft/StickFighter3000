package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraManager {
	private OrthographicCamera camera;
	private Vector2 cameraPosition;
	private Vector2 targetCameraPosition;

	public CameraManager(Camera camera) {
		this.camera = (OrthographicCamera) camera;
		cameraPosition = new Vector2(0, 0);
		this.camera.zoom += 2.5f;// TODO, move this out constructor, or not?
		this.camera.update();
	}

	// TODO smooth camera move not working yet
	public void update(float mapWidth, float mapHeight, float viewportWidth, float viewportHeight) {
		camera.position.x = MathUtils.clamp(0.9f * cameraPosition.x + 0.1f * targetCameraPosition.x, viewportWidth / 2, mapWidth - viewportWidth / 2);
		camera.position.y = MathUtils.clamp(0.9f * cameraPosition.y + 0.1f * targetCameraPosition.x, viewportHeight / 2, mapHeight - viewportHeight / 2);
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
		targetCameraPosition = value;
	}

	public Vector2 getCameraPosition() {
		return cameraPosition;
	}

	@Override
	public String toString() {
		return cameraPosition.toString();
	}

}
