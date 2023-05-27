package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.g2d.Sprite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpellSprite extends Sprite {
	private String name;

	public SpellSprite() {
		super();
	}

	public SpellSprite(Sprite sprite, String name) {
		super(sprite);
		this.name = name;
	}
}
