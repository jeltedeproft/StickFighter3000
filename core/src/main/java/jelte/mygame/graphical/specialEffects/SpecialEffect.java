package jelte.mygame.graphical.specialEffects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.graphical.animations.NamedSprite;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.Direction;
import lombok.Data;

@Data
public class SpecialEffect {

	private float timeAlive;
	private boolean dead;
	private Vector2 position;
	private Animation<NamedSprite> animation;

	public SpecialEffect(Animation<NamedSprite> animation, Character character) {
		this.animation = animation;
		position = character.getPhysicsComponent().getPosition().cpy();
		adjustPosition(character);
		timeAlive = 0f;
		dead = false;
	}

	private void adjustPosition(Character character) {
		if (character.getPhysicsComponent().getDirection() == Direction.left) {
			position.add(-animation.getKeyFrame(timeAlive).getWidth(), 0);
		}
	}

	public NamedSprite getSprite() {
		NamedSprite sprite = animation.getKeyFrame(timeAlive);
		sprite.setPosition(position.x, position.y);
		return sprite;
	}

	public void update(float delta) {
		timeAlive += delta;
		if (animation.isAnimationFinished(timeAlive)) {
			dead = true;
		}
	}

	public void draw(SpriteBatch batch) {
		getSprite().draw(batch);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("special effect for : ");
		sb.append(animation.getKeyFrame(0).getName());
		sb.append("alive for ");
		sb.append(timeAlive);
		return sb.toString();
	}

}
