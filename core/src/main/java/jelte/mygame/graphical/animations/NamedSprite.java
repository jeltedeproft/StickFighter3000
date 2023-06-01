package jelte.mygame.graphical.animations;

import com.badlogic.gdx.graphics.g2d.Sprite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NamedSprite extends Sprite {
	private String name;

	public NamedSprite() {
		super();
	}

	public NamedSprite(Sprite sprite, String name) {
		super(sprite);
		this.name = name;

	}
}
