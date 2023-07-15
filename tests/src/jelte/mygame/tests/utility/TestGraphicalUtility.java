package jelte.mygame.tests.utility;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.GraphicalUtility;

@RunWith(GdxTestRunner.class)
public class TestGraphicalUtility {

	@Ignore // TODO fix this
	public void testDrawDebugLine() {
		Vector2 start = new Vector2(0, 0);
		Vector2 end = new Vector2(10, 10);
		int lineWidth = 2;
		Color color = Color.RED;
		Matrix4 projectionMatrix = new Matrix4();

		ShapeRenderer shapeRenderer = mock(ShapeRenderer.class);

		GraphicalUtility.setShapeRenderer(shapeRenderer);

		GraphicalUtility.drawDebugLine(start, end, lineWidth, color, projectionMatrix);

		verify(shapeRenderer).setProjectionMatrix(projectionMatrix);
		verify(shapeRenderer).begin(ShapeRenderer.ShapeType.Line);
		verify(shapeRenderer).setColor(color);
		verify(shapeRenderer).line(start, end);
		verify(shapeRenderer).end();
	}

	@Test
	public void testDrawDebugPolygon() {
		// Similar test logic as testDrawDebugLine, but for drawDebugPolygon method
	}

	@Test
	public void testDrawDebugRectangle() {
		// Similar test logic as testDrawDebugLine, but for drawDebugRectangle method
	}

	// Add more test methods for other utility methods as needed

}
