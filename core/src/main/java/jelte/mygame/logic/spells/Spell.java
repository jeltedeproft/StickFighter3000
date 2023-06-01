package jelte.mygame.logic.spells;

import java.util.Objects;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.Message;
import jelte.mygame.logic.physics.SpellPhysicsComponent;
import jelte.mygame.logic.spells.state.SpellStateManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Spell {
	protected float currentHp;
	protected SpellData data;
	protected UUID id;
	protected boolean dead = false;
	protected boolean hasMoved;
	protected SpellStateManager spellStateManager;
	protected SpellPhysicsComponent physicsComponent;
	protected Vector2 startPosition;
	protected Vector2 targetPosition;
	protected float timeAlive = 0f;

	// TODO use a pool, bc we have many objects here.
	public enum SPELL_TYPE {
		LASER, ON_MOUSE, PROJECTILE, OBJECT, SHIELD, EFFECT, SUMMON
	}

	public enum AFFECTS {
		FRIENDLY, ENEMY, BOTH, NONE
	}

	public Spell(SpellData spellData, Vector2 casterPosition, Vector3 mousePosition) {
		id = UUID.randomUUID();
		this.data = spellData;
		spellStateManager = new SpellStateManager(this);
		targetPosition = new Vector2(mousePosition.x, mousePosition.y);
		startPosition = casterPosition.cpy();

		Vector2 mouse = new Vector2(mousePosition.x, mousePosition.y);
		Vector2 direction = mouse.cpy().sub(casterPosition).nor();
		Vector2 velocity = direction.scl(spellData.getSpeed());

		physicsComponent = new SpellPhysicsComponent(id, SpellsEnum.values()[spellData.getId()], casterPosition.cpy(), data.isGoesTroughObstacles());// TODO switch on type here
		physicsComponent.setVelocity(velocity);
	}

	public void update(float delta) {
		physicsComponent.update(delta);
		timeAlive += delta;
		if (timeAlive > data.getDuration()) {
			dead = true;
		}
	}

	public String getSpriteName() {
		return data.getSpriteName();
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
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name: ");
		sb.append(data.getName());
		sb.append("id: ");
		sb.append(id);
		return sb.toString();
	}

}
