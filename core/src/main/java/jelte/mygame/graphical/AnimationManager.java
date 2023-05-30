package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.spells.Spell;

public class AnimationManager {
	private static final String TAG = AnimationManager.class.getSimpleName();
	private final AnimationNameManager animationNameManager;
	private final AnimationTextureManager animationTextureManager;

	public AnimationManager() {
		animationNameManager = new AnimationNameManager();
		animationTextureManager = new AnimationTextureManager();
	}

	public NamedSprite getSprite(Character character) {
		String animationName = animationNameManager.getAnimationName(character);
		return animationTextureManager.getSprite(animationName, character);
	}

	public NamedSprite getSprite(Spell spell) {
		String animationName = animationNameManager.getAnimationName(spell);
		return animationTextureManager.getSprite(animationName, spell);
	}

	public Animation<NamedSprite> getSpecialEffect(Character character) {
		StringBuilder sb = new StringBuilder();
		sb.append(animationNameManager.getAnimationName(character));
		sb.append("-effect");
		String animationName = sb.toString();
		if (animationTextureManager.exists(animationName)) {
			return animationTextureManager.getAnimationNoCache(animationName, character);
		}
		return null;
	}

	public void update(final float delta) {
		animationNameManager.update();
		animationTextureManager.update(delta);
	}
}
