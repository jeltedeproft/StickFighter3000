package jelte.mygame.tests.graphical.particles;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.particles.ParticleMaker;
import jelte.mygame.graphical.particles.ParticleType;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestParticleMaker {

	@Test
	public void testDrawAllActiveParticles() {
		ParticleMaker particleMaker = new ParticleMaker();
		SpriteBatch spriteBatch = mock(SpriteBatch.class);
		float delta = 0.1f;

		// Add test setup code to populate particles
		// ...

		particleMaker.drawAllActiveParticles(spriteBatch, delta);

		// Add assertions or verify interactions
		// ...
	}

	@Test
	public void testDeactivateAllParticlesOfType() {
		ParticleMaker particleMaker = new ParticleMaker();
		ParticleType particleType = ParticleType.DUST;

		// Add test setup code to populate particles of the given type
		// ...

		particleMaker.deactivateAllParticlesOfType(particleType);

		// Add assertions or verify interactions
		// ...
	}

	@Test
	public void testDeactivateAllParticles() {
		ParticleMaker particleMaker = new ParticleMaker();

		// Add test setup code to populate particles of different types
		// ...

		particleMaker.deactivateAllParticles();

		// Add assertions or verify interactions
		// ...
	}

	@Test
	public void testDeactivateParticle() {
		ParticleMaker particleMaker = new ParticleMaker();
		ParticleType particleType = ParticleType.DUST;
		Vector2 pos = new Vector2(10, 10);
		int id = 1;

		// Add test setup code to populate particles with the given position and id
		// ...

		// particleMaker.deactivateParticle(particleType, pos, id);

		// Add assertions or verify interactions
		// ...
	}

	@Test
	public void testAddParticle() {
		ParticleMaker particleMaker = new ParticleMaker();
		ParticleType particleType = ParticleType.DUST;
		Vector2 pos = new Vector2(20, 20);

		particleMaker.addParticle(particleType, pos);

		// Add assertions or verify interactions
		// ...
	}

	// Add more test cases for other methods as needed
}
