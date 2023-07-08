package jelte.mygame.tests.graphical.particles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.particles.Particle;
import jelte.mygame.graphical.particles.ParticleType;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestParticle {

	@Mock
	private PooledEffect mockPooledEffect;

	@Mock
	private SpriteBatch mockSpriteBatch;

	private Particle particle;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Vector2 pos = new Vector2(10, 20);
		ParticleType type = ParticleType.DUST;
		particle = new Particle(pos, mockPooledEffect, type, null);
	}

	@Test
	public void testIsActive() {
		assertTrue(particle.isActive());
	}

	@Test
	public void testDeactivate() {
		particle.deactivate();
		assertFalse(particle.isActive());
	}

	@Test
	public void testDrawShown() {
		particle.setShown(true);
		particle.draw(mockSpriteBatch, 0.5f);
		verify(mockPooledEffect).draw(mockSpriteBatch, 0.5f);
	}

	@Test
	public void testDrawHidden() {
		particle.setShown(false);
		particle.draw(mockSpriteBatch, 0.5f);
		verify(mockPooledEffect, never()).draw(any(SpriteBatch.class), anyFloat());
	}

	@Test
	public void testDelete() {
		particle.delete();
		verify(mockPooledEffect).free();
	}

	@Test
	public void testGetParticleType() {
		assertEquals(ParticleType.DUST, particle.getParticleType());
	}

	@Test
	public void testGetPosition() {
		Vector2 pos = particle.getPosition();
		assertEquals(10, pos.x, 0.001);
		assertEquals(20, pos.y, 0.001);
	}

	@Test
	public void testSetPosition() {
		Vector2 newPos = new Vector2(30, 40);
		particle.setPosition(newPos);
		assertEquals(newPos, particle.getPosition());
		verify(mockPooledEffect).setPosition(newPos.x, newPos.y);
	}

	@Test
	public void testUpdateShown() {
		particle.setShown(true);
		particle.update(0.5f);
		verify(mockPooledEffect).update(0.5f);
	}

	@Test
	public void testUpdateHidden() {
		particle.setShown(false);
		particle.update(0.5f);
		verify(mockPooledEffect, never()).update(anyFloat());
	}

	@Test
    public void testIsComplete() {
        when(mockPooledEffect.isComplete()).thenReturn(true);
        assertTrue(particle.isComplete());
    }

	@Test
	public void testGetParticleEffect() {
		assertEquals(mockPooledEffect, particle.getParticleEffect());
	}

	@Test
	public void testStart() {
		particle.start();
		verify(mockPooledEffect).start();
	}

	@Test
	public void testSetShown() {
		particle.setShown(false);
		assertFalse(particle.isShown());
		particle.setShown(true);
		assertTrue(particle.isShown());
	}

	@Test
	public void testToString() {
		String expectedString = "Particle : DUST at pos : (10.0,20.0)";
		assertEquals(expectedString, particle.toString());
	}
}
