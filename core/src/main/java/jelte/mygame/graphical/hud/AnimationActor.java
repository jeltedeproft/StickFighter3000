package jelte.mygame.graphical.hud;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import jelte.mygame.graphical.animations.NamedSprite;

// Create a custom Actor class for handling the animation
public class AnimationActor extends Actor {
	private Animation<NamedSprite> animation;
	private float stateTime;

	public AnimationActor(Animation<NamedSprite> animation) {
		this.animation = animation;
		stateTime = 0f;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		stateTime += delta;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
		batch.draw(currentFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
}
