package jelte.mygame.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GraphicalUtility {
	private static ShapeRenderer debugRenderer = new ShapeRenderer();

	public static void drawDebugLine(Vector2 start, Vector2 end, int lineWidth, Color color, Matrix4 projectionMatrix) {
		Gdx.gl.glLineWidth(lineWidth);
		debugRenderer.setProjectionMatrix(projectionMatrix);
		debugRenderer.begin(ShapeRenderer.ShapeType.Line);
		debugRenderer.setColor(color);
		debugRenderer.line(start, end);
		debugRenderer.end();
		Gdx.gl.glLineWidth(1);
	}

	public static void drawDebugPolygon(float[] vertices, int lineWidth, Color color, Matrix4 projectionMatrix) {
		Gdx.gl.glLineWidth(lineWidth);
		debugRenderer.setProjectionMatrix(projectionMatrix);
		debugRenderer.begin(ShapeRenderer.ShapeType.Line);
		debugRenderer.setColor(color);
		debugRenderer.polygon(vertices);
		debugRenderer.end();
		Gdx.gl.glLineWidth(1);
	}

	public static void drawDebugRectangle(float x, float y, float width, float height, int lineWidth, Color color, Matrix4 projectionMatrix) {
		Gdx.gl.glLineWidth(lineWidth);
		debugRenderer.setProjectionMatrix(projectionMatrix);
		debugRenderer.begin(ShapeRenderer.ShapeType.Filled);
		debugRenderer.setColor(color);
		debugRenderer.rect(x, y, width, height);
		debugRenderer.end();
		Gdx.gl.glLineWidth(1);
	}

	public static void drawDebugRectangle(Rectangle rect, int lineWidth, Color color, Matrix4 projectionMatrix) {
		Gdx.gl.glLineWidth(lineWidth);
		debugRenderer.setProjectionMatrix(projectionMatrix);
		debugRenderer.begin(ShapeRenderer.ShapeType.Filled);
		debugRenderer.setColor(color);
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		debugRenderer.end();
		Gdx.gl.glLineWidth(1);
	}

	public static void drawDebugLine(Vector2 start, Vector2 end, Matrix4 projectionMatrix) {
		Gdx.gl.glLineWidth(1);
		debugRenderer.setProjectionMatrix(projectionMatrix);
		debugRenderer.begin(ShapeRenderer.ShapeType.Line);
		debugRenderer.setColor(Color.BLACK);
		final Color color = debugRenderer.getColor();
		color.a = 0.2f;
		debugRenderer.setColor(color);
		debugRenderer.line(start, end);
		debugRenderer.end();
		Gdx.gl.glLineWidth(1);
	}

	private GraphicalUtility() {
		// static class
	}
}
