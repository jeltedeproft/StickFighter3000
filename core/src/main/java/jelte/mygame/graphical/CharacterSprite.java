package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.g2d.Sprite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterSprite extends Sprite {
	private String name;
	private boolean isRight;
	private float offset;

	public CharacterSprite() {
		super();
	}

	public CharacterSprite(Sprite sprite, String name, float offset) {
		super(sprite);
		this.name = name;
		this.offset = offset;
		isRight = name.contains("right");
	}
}
