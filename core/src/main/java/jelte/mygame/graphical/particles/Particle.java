package jelte.mygame.graphical.particles;

import java.util.UUID;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Particle extends Actor {
	private UUID id;
	private Vector2 pos;
	private Boolean active;
	private Boolean shown;
	private final ParticleType type;
	private final PooledEffect particleEffect;

	public Particle(final Vector2 pos, final PooledEffect pe, final ParticleType type, final UUID id) {
		super();
		this.pos = pos;
		shown = true;
		active = true;
		this.type = type;
		particleEffect = pe;
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void deactivate() {
		active = false;
	}

	public void draw(final SpriteBatch spriteBatch, final float delta) {
		if (Boolean.TRUE.equals(shown)) {
			particleEffect.draw(spriteBatch, delta);
		}
	}

	public void delete() {
		particleEffect.free();
	}

	public ParticleType getParticleType() {
		return type;
	}

	public Vector2 getPosition() {
		return pos;
	}

	public void setPosition(Vector2 pos) {
		this.pos = pos;
		particleEffect.setPosition(pos.x, pos.y);
	}

	public void update(final float delta) {
		if (Boolean.TRUE.equals(shown)) {
			particleEffect.update(delta);
		}
	}

	public boolean isComplete() {
		return particleEffect.isComplete();
	}

	public PooledEffect getParticleEffect() {
		return particleEffect;
	}

	public void start() {
		particleEffect.start();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Boolean isShown() {
		return shown;
	}

	public void setShown(Boolean shown) {
		this.shown = shown;
	}

	@Override
	public String toString() {
		return "Particle : " + type + " at pos : (" + pos.x + "," + pos.y + ")";
	}
}
