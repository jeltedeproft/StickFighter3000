package jelte.mygame.tests.graphical;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.CameraManager;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestCameraManager {

	private OrthographicCamera camera;
	private CameraManager cameraManager;

	@Before
	public void setUp() {
		camera = new OrthographicCamera();
		cameraManager = new CameraManager(camera);
	}

	@Test
	public void testUpdate() {
		float mapWidth = 1000f;
		float mapHeight = 800f;
		float viewportWidth = 400f;
		float viewportHeight = 300f;

		cameraManager.update(mapWidth, mapHeight, viewportWidth, viewportHeight);

		float expectedCameraX = MathUtils.clamp(cameraManager.getCameraPosition().x, viewportWidth / 2, mapWidth - viewportWidth / 2);
		float expectedCameraY = MathUtils.clamp(cameraManager.getCameraPosition().y, viewportHeight / 2, mapHeight - viewportHeight / 2);

		assertEquals(expectedCameraX, camera.position.x, 0.001f);
		assertEquals(expectedCameraY, camera.position.y, 0.001f);
		assertEquals(0f, camera.position.z, 0.001f);
		assertEquals(1.5f, camera.zoom, 0.001f);
	}

	@Test
	public void testMoveCamera() {
		float moveX = 50f;
		float moveY = -30f;

		float initialCameraX = camera.position.x;
		float initialCameraY = camera.position.y;

		cameraManager.moveCamera(moveX, moveY);

		assertEquals(initialCameraX + moveX, camera.position.x, 0.001f);
		assertEquals(initialCameraY + moveY, camera.position.y, 0.001f);
		assertEquals(0f, camera.position.z, 0.001f);
		assertEquals(1.5f, camera.zoom, 0.001f);// camera starts by adding 0.5 zoom in constructor
	}

	@Test
	public void testZoomCamera() {
		float value = 0.5f;

		float initialZoom = camera.zoom;

		cameraManager.zoomCamera(value);

		assertEquals(initialZoom + value, camera.zoom, 0.001f);
		assertEquals(0f, camera.position.x, 0.001f);
		assertEquals(0f, camera.position.y, 0.001f);
		assertEquals(0f, camera.position.z, 0.001f);
	}

	@Test
	public void testUpdateCameraPos() {
		Vector2 value = new Vector2(100f, -50f);

		cameraManager.updateCameraPos(value);

		assertEquals(value.x, cameraManager.getCameraPosition().x, 0.001f);
		assertEquals(value.y, cameraManager.getCameraPosition().y, 0.001f);
		assertEquals(0f, camera.position.x, 0.001f);
		assertEquals(0f, camera.position.y, 0.001f);
		assertEquals(0f, camera.position.z, 0.001f);
	}

	@Test
	public void testToString() {
		Vector2 value = new Vector2(100f, -50f);
		cameraManager.updateCameraPos(value);

		String expectedString = value.toString();

		assertEquals(expectedString, cameraManager.toString());
	}
}
