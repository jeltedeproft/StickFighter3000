package jelte.mygame.tests.graphical.particles;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

import jelte.mygame.graphical.particles.ParticleEffectActor;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestParticleEffectActor {

	@Test
	public void testDraw() {
		ParticleEffect particleEffect = mock(ParticleEffect.class);
		ParticleEffectActor particleEffectActor = new ParticleEffectActor(particleEffect);
		Batch batch = mock(Batch.class);
		float parentAlpha = 0.5f;

		particleEffectActor.draw(batch, parentAlpha);

		verify(particleEffect, times(1)).draw(batch);
	}

	@Test
	public void testAct() {
		ParticleEffect particleEffect = mock(ParticleEffect.class);
		ParticleEffectActor particleEffectActor = new ParticleEffectActor(particleEffect);
		float delta = 0.1f;
		float width = 100f;
		float height = 200f;
		float centerX = width * 0.5f;
		float centerY = height * 0.5f;

		particleEffectActor.setSize(width, height);
		particleEffectActor.act(delta);

		verify(particleEffect, times(1)).setPosition(centerX, centerY);
		verify(particleEffect, times(1)).update(delta);
	}

	@Test
	public void testStart() {
		ParticleEffect particleEffect = mock(ParticleEffect.class);
		ParticleEffectActor particleEffectActor = new ParticleEffectActor(particleEffect);

		particleEffectActor.start();

		verify(particleEffect, times(1)).start();
	}

	@Test
	public void testAllowCompletion() {
		ParticleEffect particleEffect = mock(ParticleEffect.class);
		ParticleEffectActor particleEffectActor = new ParticleEffectActor(particleEffect);

		particleEffectActor.allowCompletion();

		verify(particleEffect, times(1)).allowCompletion();
	}
}
