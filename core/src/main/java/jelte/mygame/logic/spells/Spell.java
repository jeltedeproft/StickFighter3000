package jelte.mygame.logic.spells;

import java.util.Objects;
import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.Message;
import jelte.mygame.logic.character.physics.PhysicsComponent;
import jelte.mygame.logic.character.physics.StandardPhysicsComponent;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.collisions.Collidable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Spell implements Collidable {
	protected float currentHp;
	protected SpellData data;
	protected UUID id;
	protected boolean dead;
	protected boolean hasMoved;
	protected CharacterStateManager characterStateManager;
	protected PhysicsComponent physicsComponent;

	public Spell(SpellData data, UUID id) {
		this.id = id;
		this.data = data;
		physicsComponent = new StandardPhysicsComponent(id);
	}

	public void update(float delta) {
		physicsComponent.update(delta);
	}

	public String getSpriteName() {
		return data.getEntitySpriteName();
	}

	public String getName() {
		return data.getName();
	}

	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		default:
			break;

		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Spell other = (Spell) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public Rectangle getRectangle() {
		return physicsComponent.getRectangle();
	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.CHARACTER;
	}

	@Override
	public Vector2 getOldPosition() {
		return physicsComponent.getOldPosition();
	}

	@Override
	public boolean hasMoved() {
		return hasMoved;
	}

	@Override
	public boolean isStatic() {
		return false;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}

}
