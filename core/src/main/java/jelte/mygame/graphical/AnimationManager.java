package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.g2d.Sprite;

import jelte.mygame.logic.character.Character;
import lombok.ToString;

@ToString
public class AnimationManager {
	private static final String TAG = AnimationManager.class.getSimpleName();
	private final AnimationNameManager animationNameManager;
	private final AnimationTextureManager animationTextureManager;

	public AnimationManager() {
		animationNameManager = new AnimationNameManager();
		animationTextureManager = new AnimationTextureManager();
	}

	public Sprite getSprite(Character character) {
		String animationName = animationNameManager.getAnimationName(character);
		return animationTextureManager.getSprite(animationName, character);
	}

	public void update(final float delta) {
		animationNameManager.update();
		animationTextureManager.update(delta);
	}

}
