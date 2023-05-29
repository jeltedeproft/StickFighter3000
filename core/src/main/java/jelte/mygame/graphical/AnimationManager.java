package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.g2d.Animation;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.spells.Spell;
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

	public NamedSprite getSprite(Character character) {
		String animationName = animationNameManager.getAnimationName(character);
		return animationTextureManager.getSprite(animationName, character);
	}

	public NamedSprite getSprite(Spell spell) {
		String animationName = animationNameManager.getAnimationName(spell);
		return animationTextureManager.getSprite(animationName, spell);
	}

	public Animation<NamedSprite> getSpecialEffect(Character character) {
		String animationName = animationNameManager.getAnimationName(character);
		animationName += "-effect";
		return animationTextureManager.getAnimationNoCache(animationName, character);
	}

	public void update(final float delta) {
		animationNameManager.update();
		animationTextureManager.update(delta);
	}
}
