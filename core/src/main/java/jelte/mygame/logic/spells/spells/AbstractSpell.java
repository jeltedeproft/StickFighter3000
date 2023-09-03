package jelte.mygame.logic.spells.spells;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.Objects;
import java.util.UUID;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.physics.NullPhysicsComponent;
import jelte.mygame.logic.physics.PhysicsComponent;
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
	protected PhysicsComponent physicsComponent = NullPhysicsComponent.getInstance();

	protected abstract void updateSpell(float delta, Character caster, Vector2 mousePosition);

	// TODO use a pool, because we have many objects here.
	protected AbstractSpell(SpellData spellData, Character caster) {
		id = UUID.randomUUID();
		casterId = caster.getId();
		data = spellData;

	}

	@Override
	public void update(float delta, Character caster, Vector2 mousePosition) {
		timeAlive += delta;
		if (timeAlive > data.getDuration()) {
			complete = true;
		}
		spellStateManager.update(delta);
		updateSpell(delta, caster, mousePosition);
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
	public void handleEvent(EVENT event) {
		spellStateManager.handleEvent(event);
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
		sb.append("name: ")
				.append(data.getName())
				.append(", id: ")
				.append(id)
				.append("\n");
		return sb.toString();
	}
}
