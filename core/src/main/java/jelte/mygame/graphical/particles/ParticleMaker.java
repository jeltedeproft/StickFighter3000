package jelte.mygame.graphical.particles;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.UUID;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.utility.AssetManagerUtility;

public class ParticleMaker {
	private EnumMap<ParticleType, ParticlePool> particlePools;
	private EnumMap<ParticleType, ArrayList<Particle>> allParticles;
	private boolean particlesChanged;

	public ParticleMaker() {
		particlesChanged = false;
		particlePools = new EnumMap<>(ParticleType.class);
		allParticles = new EnumMap<>(ParticleType.class);
	}

	public void drawAllActiveParticles(final SpriteBatch spriteBatch, final float delta) {
		// spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE); // performance-optim.: manually set blend function to additive!, cant use thise wit
		// hframeBuffer
		for (final ArrayList<Particle> particleTypeList : allParticles.values()) {
			for (final Particle particle : particleTypeList) {
				if (particle.isActive()) {
					particle.update(delta);
					particle.draw(spriteBatch, delta);
				}

				if (particle.isComplete()) {
					particlesChanged = true;
					particle.deactivate();
				}
			}
		}
		// spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // performance-optim.: manually reset blend function!
	}

	public void deactivateAllParticlesOfType(final ParticleType particletype) {
		if (allParticles.get(particletype) != null) {
			for (final Particle particle : allParticles.get(particletype)) {
				particle.deactivate();
			}
			particlesChanged = true;
			AssetManagerUtility.unloadAsset(particletype.getParticleFileLocation());
		}
	}

	public void deactivateAllParticles() {
		for (ArrayList<Particle> particles : allParticles.values()) {
			for (final Particle particle : particles) {
				particle.deactivate();
			}
			particlesChanged = true;
		}
	}

	public void deactivateParticle(final ParticleType particletype, final Vector2 pos, final UUID id) {
		if (allParticles.get(particletype) != null) {
			for (final Particle particle : allParticles.get(particletype)) {
				if (particle.getPosition().equals(pos) && particle.getId().equals(id)) {
					particle.deactivate();
				}
			}
			particlesChanged = true;
		}
	}

	public void addParticle(final ParticleType particletype, final Vector2 pos) {
		final ParticlePool particlePool = initiatePool(particletype);
		final Particle newParticle = createPooledParticle(particletype, pos, particlePool, UUID.randomUUID());
		addParticleToTypedParticles(particletype, newParticle);
	}

	private ParticlePool initiatePool(final ParticleType particletype) {
		ParticlePool particlePool;

		if (particlePools.containsKey(particletype)) {
			particlePool = particlePools.get(particletype);
		} else {
			particlePool = new ParticlePool(particletype);
		}
		return particlePool;
	}

	private Particle createPooledParticle(final ParticleType particletype, final Vector2 pos, final ParticlePool particlePool, final UUID id) {
		final PooledEffect particle = particlePool.getParticleEffect();
		particle.setPosition(pos.x, pos.y);
		return new Particle(pos, particle, particletype, id);
	}

	private void addParticleToTypedParticles(final ParticleType particletype, final Particle newParticle) {
		allParticles.computeIfAbsent(particletype, k -> new ArrayList<>());
		allParticles.get(particletype).add(newParticle);
		newParticle.start();
	}

	public Particle getParticle(final ParticleType particletype, final Vector2 pos) {
		for (final Particle particle : allParticles.get(particletype)) {
			if (particle.getPosition().equals(pos)) {
				return particle;
			}
		}
		return null;
	}

	public Particle getParticle(final ParticleType particletype, final Vector2 pos, final int id) {
		for (final Particle particle : allParticles.get(particletype)) {
			if (particle.getPosition().equals(pos) && particle.getId().equals(id)) {
				return particle;
			}
		}
		return null;
	}

	public Particle getParticle(final ParticleType particletype) {
		return allParticles.get(particletype).get(0);
	}

	public boolean isParticleTypeEmpty(final ParticleType particletype) {
		return particlePools.get(particletype) != null;
	}

	public void deactivateParticle(final Particle particle) {
		if (particle != null) {
			particlesChanged = true;
			particle.deactivate();
		}
	}

	public void cleanUpUnactiveParticles() {
		if (particlesChanged) {
			for (final ArrayList<Particle> particleTypeList : allParticles.values()) {
				for (final Particle particle : particleTypeList) {
					if (!particle.isActive()) {
						particle.delete();
						allParticles.get(particle.getParticleType()).remove(particle);
					}
				}
			}
			particlesChanged = false;
		}
	}
}
