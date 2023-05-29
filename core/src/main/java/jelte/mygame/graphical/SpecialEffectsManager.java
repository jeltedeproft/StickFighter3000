package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;

public interface SpecialEffectsManager {

	public void addSpecialEffect(Character character, Animation<NamedSprite> specialEffect);

	public void update(float delta, Character character);

	public Array<SpecialEffect> getAllEffects();

}
