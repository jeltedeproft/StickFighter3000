package jelte.mygame.graphical.specialEffects;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.graphical.animations.NamedSprite;
import jelte.mygame.logic.character.Character;

public class SpecialEffectsManagerImpl implements SpecialEffectsManager {
	private Array<SpecialEffect> specialEffects;

	public SpecialEffectsManagerImpl() {
		specialEffects = new Array<>();
	}

	@Override
	public void addSpecialEffect(Character character, Animation<NamedSprite> specialEffect) {
		specialEffects.add(new SpecialEffect(specialEffect, character));
	}

	@Override
	public void update(float delta, Character character) {
		specialEffects.forEach(effect -> effect.update(delta));
		clean();
	}

	private void clean() {
		final Iterator<SpecialEffect> iterator = specialEffects.iterator();
		while (iterator.hasNext()) {
			final SpecialEffect specialEffect = iterator.next();
			if (specialEffect.isDead()) {
				iterator.remove();
			}
		}
	}

	@Override
	public Array<SpecialEffect> getAllEffects() {
		return specialEffects;
	}
}
