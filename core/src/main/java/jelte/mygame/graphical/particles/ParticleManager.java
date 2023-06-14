package jelte.mygame.graphical.particles;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Array;

public class ParticleManager {
	private final AssetManager assetManager;
	private final Array<PooledEffect> activeEffects;
	private final ParticleEffectPool effectPool;

	public ParticleManager() {
		assetManager = new AssetManager();
		activeEffects = new Array<>();
		effectPool = new ParticleEffectPool(new ParticleEffect(), 1, 10);
	}

	public void loadParticleEffect(String assetPath) {
		assetManager.load(assetPath, ParticleEffect.class);
		assetManager.finishLoading();
	}

	public void addParticle(String assetName, float x, float y) {
		ParticleEffect particleEffect = assetManager.get(assetName, ParticleEffect.class);
		PooledEffect effect = effectPool.obtain();
		effect.setDuration(0);
		effect.setPosition(x, y);
		effect.start();
		activeEffects.add(effect);
	}

	public void update(float deltaTime) {
		for (int i = activeEffects.size - 1; i >= 0; i--) {
			PooledEffect effect = activeEffects.get(i);
			effect.update(deltaTime);
			if (effect.isComplete()) {
				effect.free();
				activeEffects.removeIndex(i);
			}
		}
	}

	public void render(Batch batch) {
		for (PooledEffect effect : activeEffects) {
			effect.draw(batch);
		}
	}

	public void dispose() {
		assetManager.dispose();
		for (PooledEffect effect : activeEffects) {
			effect.free();
		}
		activeEffects.clear();
	}
}
