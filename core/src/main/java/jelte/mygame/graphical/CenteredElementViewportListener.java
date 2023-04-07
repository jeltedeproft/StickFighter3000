package jelte.mygame.graphical;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class CenteredElementViewportListener extends ScalingViewport {

	private final Actor element;

	public CenteredElementViewportListener(float worldWidth, float worldHeight, Actor element) {
		super(Scaling.stretch, worldWidth, worldHeight);
		this.element = element;
		element.setOrigin(Align.center);
	}

	@Override
	public void update(int screenWidth, int screenHeight, boolean centerCamera) {
		super.update(screenWidth, screenHeight, centerCamera);
		float elementX = (getWorldWidth() - element.getWidth()) / 2f;
		float elementY = (getWorldHeight() - element.getHeight()) / 2f;
		element.setPosition(elementX, elementY);
	}
}