package jelte.mygame.logic.spells.spells;

import java.util.Objects;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.physics.PhysicsComponent;
import jelte.mygame.logic.physics.SpellPhysicsComponent;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.state.SpellStateManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractSpell implements Spell {
	protected SpellData data;
	protected UUID id;
	protected UUID casterId;
	protected float timeAlive = 0f;
	protected boolean complete = false;
	protected SpellStateManager spellStateManager;
	protected SpellPhysicsComponent physicsComponent;

	// TODO use a pool, bc we have many objects here.

	protected AbstractSpell(SpellData spellData, Character caster, Vector2 mousePosition) {
		id = UUID.randomUUID();
		this.casterId = caster.getId();
		this.data = spellData;
		spellStateManager = new SpellStateManager(this);
		physicsComponent = createSpellPhysicsComponent(id, caster.getPhysicsComponent().getPosition().cpy(), mousePosition, spellData.getSpeed(), data.isGoesTroughObstacles());
	}

	protected abstract SpellPhysicsComponent createSpellPhysicsComponent(UUID spellId, Vector2 casterPosition, Vector2 mousePosition, float speed, boolean goesTroughObstacles);

	protected abstract void updateSpell(Vector2 casterPosition, Vector2 mousePosition);

	@Override
	public void update(float delta, Character caster, Vector2 mousePosition) {
		updateSpell(caster.getPhysicsComponent().getPosition(), mousePosition);
		physicsComponent.update(delta);
		timeAlive += delta;
		if (timeAlive > data.getDuration()) {
			complete = true;
		}
	}

	@Override
	public boolean isComplete() {
		return complete;
	}

	@Override
	public PhysicsComponent getPhysicsComponent() {
		return physicsComponent;
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public String getSpriteName() {
		return data.getSpriteName();
	}

	@Override
	public String getName() {
		return data.getName();
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
		AbstractSpell other = (AbstractSpell) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name: ");
		sb.append(data.getName());
		sb.append(", id: ");
		sb.append(id);
		sb.append("\n");
		return sb.toString();
	}

}
