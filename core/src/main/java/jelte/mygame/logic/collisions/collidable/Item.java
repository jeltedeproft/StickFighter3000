package jelte.mygame.logic.collisions.collidable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.Objects;
import java.util.UUID;

import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.collisions.items.ItemEnum;
import jelte.mygame.logic.spells.SpellsEnum;
import lombok.Getter;

@Getter
public class Item implements Collidable {
	private static final long serialVersionUID = 1L;
	private UUID id;
	private Rectangle rectangle;
	private ItemEnum itemEnum;
	private boolean toBeRemoved = false;
	private boolean collided;
	private Vector2 position;

	public Item(String itemName, Rectangle rectangle) {
		this.rectangle = rectangle;
		position = rectangle.getPosition(new Vector2(0, 0));
		id = UUID.randomUUID();
		this.itemEnum = ItemEnum.valueOf(itemName);
	}

	public Item(int x, int y, int width, int height) {
		rectangle = new Rectangle(x, y, width, height);
		id = UUID.randomUUID();
	}

	public void collidedWithPlayer(PlayerCharacter player) {
		switch (itemEnum) {
		case FIREBALL_ITEM:
			toBeRemoved = true;
			player.unlockSpell(SpellsEnum.FIREBALL);
			break;
		default:
			break;

		}
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public Rectangle getRectangle() {
		return rectangle;
	}

	@Override
	public Rectangle getOldRectangle() {
		return rectangle;
	}

	@Override
	public boolean hasMoved() {
		return false;
	}

	@Override
	public boolean isStatic() {
		return true;
	}

	@Override
	public boolean isDynamic() {
		return false;
	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.ITEM;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || getClass() != obj.getClass()) {
			return false;
		}
		Item other = (Item) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("pos = ");
		sb.append("(");
		sb.append(rectangle.x);
		sb.append(",");
		sb.append(rectangle.y);
		sb.append(")");
		sb.append("\n");
		sb.append("width = ");
		sb.append(rectangle.width);
		sb.append("\n");
		sb.append("height = ");
		sb.append(rectangle.height);
		sb.append("\n");
		return sb.toString();
	}

	public AudioEnum getAudioEvent() {
		return AudioEnum.valueOf("SOUND_" + itemEnum.name());
	}

	@Override
	public Vector2 getPosition() {
		return rectangle.getPosition(position);
	}

	@Override
	public void setPosition(Vector2 position) {
		rectangle.setPosition(position.x, position.y);
	}

	@Override
	public void setCollided(boolean b) {
		collided = b;
	}

	@Override
	public boolean isCollided() {
		return collided;
	}

	@Override
	public void setSize(float width, float height) {
		rectangle.setWidth(width);
		rectangle.setHeight(height);
	}

	@Override
	public float getWidth() {
		return rectangle.getWidth();
	}

	@Override
	public float getHeight() {
		return rectangle.getHeight();
	}

}
