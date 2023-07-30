package jelte.mygame.logic.collisions.collidable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.Objects;
import java.util.UUID;

import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.collisions.items.ItemEnum;
import jelte.mygame.logic.spells.SpellsEnum;
import lombok.Getter;

@Getter
public class ItemCollidable extends Rectangle implements Collidable {
	private static final long serialVersionUID = 1L;
	private UUID id;
	private ItemEnum item;
	private boolean toBeRemoved = false;

	public ItemCollidable(String itemName, Rectangle rectangle) {
		super(rectangle);
		id = UUID.randomUUID();
		this.item = ItemEnum.valueOf(itemName);
	}

	public ItemCollidable(int x, int y, int width, int height) {
		super(x, y, width, height);
		id = UUID.randomUUID();
	}

	public void collidedWithPlayer(PlayerCharacter player) {
		switch (item) {
		case SPELL0:
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
		return this;
	}

	@Override
	public Rectangle getOldRectangle() {
		return this;
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
	public boolean goesTroughObjects() {
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
		ItemCollidable other = (ItemCollidable) obj;
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
		sb.append(this.x);
		sb.append(",");
		sb.append(this.y);
		sb.append(")");
		sb.append("\n");
		sb.append("width = ");
		sb.append(width);
		sb.append("\n");
		sb.append("height = ");
		sb.append(height);
		sb.append("\n");
		return sb.toString();
	}

}
