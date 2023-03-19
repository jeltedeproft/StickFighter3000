package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CameraManager {
	private OrthographicCamera camera;
	private Vector2 cameraPosition;

	public CameraManager(Camera camera,float maxWidth,float maxHeight) {
		this.camera = (OrthographicCamera) camera;
	}
	
	
	public void update() {
		camera.position.set(cameraPosition.x, cameraPosition.y, 0f);
		camera.position.x = MathUtils.clamp(cameraPosition.x, 0, mapManager.getCurrentMapWidth());
		camera.position.y = MathUtils.clamp(cameraPosition.y, 0, mapManager.getCurrentMapHeight());
		camera.update();
	}


	public OrthographicCamera getCamera() {
		return camera;
	}

}
