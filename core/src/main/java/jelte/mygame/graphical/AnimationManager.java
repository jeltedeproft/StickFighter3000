package jelte.mygame.graphical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jelte.myGames.Utility.AssetManagerUtility;
import com.jelte.myGames.shared.entities.WizardFileReader;
import com.jelte.myGames.shared.serverClientMessages.ThingsToDraw.WizardState;
import com.jelte.myGames.shared.spells.SpellFileReader;
import com.jelte.myGames.shared.utility.PonkoVariables;

import jelte.mygame.logic.CharacterFileReader;
import lombok.ToString;

@ToString
public class AnimationManager {
	private static final String TAG = AnimationManager.class.getSimpleName();

	private final Map<UUID, String> previousAnimations;
	private final Map<UUID, Float> frameTimesPerObject;
	private final Map<String, Animation<Sprite>> animationsCache;
	private final Map<String, CharacterAnimationData> animationData;
	private final ConcurrentLinkedQueue<UUID> usedIds;

	public AnimationManager() {
		previousAnimations = new HashMap<>();
		frameTimesPerObject = new HashMap<>();
		animationsCache = new HashMap<>();
		animationData = new HashMap<>();
		usedIds = new ConcurrentLinkedQueue<>();
		initializeWizards();
	}

	public void initializeWizards() {
		for (String wizardName : CharacterFileReader.getAllCharacterNames()) {
			animationData.computeIfAbsent(wizardName, CharacterAnimationData::new);
		}
	}

	public Sprite getTextureForCharacter(Character character) {
		usedIds.add(wizardState.id);
		return stringToTexture(localWizardAnimationData.get(wizardState.name).getAnimationName(wizardState.action, wizardState.direction), wizardState.id);
	}

	public Sprite stringToTexture(String animationName, UUID id) {
		usedIds.add(id);
		final String previous = previousAnimations.get(id);
		// new animation or changed animation, reset frameTime
		if ((previous == null) || !previous.equals(animationName)) {
			previousAnimations.put(id, animationName);
			frameTimesPerObject.put(id, 0f);
		}

		// is animation already loaded?
		Animation<Sprite> animation = animationsCache.get(animationName);
		if (animation == null) {
			animation = AssetManagerUtility.getAnimation(animationName, getAnimationDurationFromString(animationName), getAnimationTypeFromString(animationName));
			if (animation == null) {
				Gdx.app.debug(TAG, "cannot find animation of this type : " + animationName);
				return null;
			}
			animationsCache.put(animationName, animation);
		}

		final float frameTime = frameTimesPerObject.get(id);

		return animation.getKeyFrame(frameTime);
	}

	private float getAnimationDurationFromString(String animationName) {

		for (String name : localWizardAnimationData.keySet()) {
			if (animationName.contains(name)) {
				return getAnimationSpeedForWizard(name, animationName);
			}
		}

		return PonkoVariables.DEFAULT_ANIMATION_SPEED;
	}

	private float getAnimationSpeedForWizard(String name, String animationName) {
		if (animationName.contains("cast")) {
			int id = CharacterFileReader.getIdByName(name);
			return CharacterFileReader.getUnitData().get(id).getCastAnimationSpeed();
		}
		if (animationName.contains("idle")) {
			int id = CharacterFileReader.getIdByName(name);
			return CharacterFileReader.getUnitData().get(id).getIdleAnimationSpeed();
		}
		if (animationName.contains("hurt")) {
			int id = CharacterFileReader.getIdByName(name);
			return CharacterFileReader.getUnitData().get(id).getHurtAnimationSpeed();
		}
		if (animationName.contains("die")) {
			int id = CharacterFileReader.getIdByName(name);
			return CharacterFileReader.getUnitData().get(id).getDieAnimationSpeed();
		}
		if (animationName.contains("shoot")) {
			int id = CharacterFileReader.getIdByName(name);
			return CharacterFileReader.getUnitData().get(id).getShootAnimationSpeed();
		}
		if (animationName.contains("teleport")) {
			int id = CharacterFileReader.getIdByName(name);
			return CharacterFileReader.getUnitData().get(id).getTeleportAnimationSpeed();
		}
		if (animationName.contains("move")) {
			int id = CharacterFileReader.getIdByName(name);
			return CharacterFileReader.getUnitData().get(id).getMoveAnimationSpeed();
		}
		if (animationName.contains("icePickle")) {
			int id = SpellFileReader.getIdByName(name);
			return SpellFileReader.getSpellData().get(id).getAnimationSpeed();
		}
		return PonkoVariables.DEFAULT_ANIMATION_SPEED;
	}

	private Animation.PlayMode getAnimationTypeFromString(String animationName) {
		if (animationName.contains("cast") || animationName.contains("die")) {
			return Animation.PlayMode.NORMAL;
		}
		if (animationName.contains("idle")) {
			return Animation.PlayMode.LOOP;
		}
		if (animationName.contains("teleport")) {
			return Animation.PlayMode.NORMAL;
		}
		if (animationName.contains("move")) {
			return Animation.PlayMode.LOOP;
		}
		if (animationName.contains("shoot")) {
			return Animation.PlayMode.NORMAL;
		}
		if (animationName.contains("shield")) {
			return Animation.PlayMode.LOOP;
		}
		if (animationName.contains("laser")) {
			return Animation.PlayMode.LOOP;
		}
		if (animationName.contains("icePickle")) {
			return Animation.PlayMode.NORMAL;
		}

		return Animation.PlayMode.LOOP;
	}

	public void cleanUpOldAnimations() {
		ArrayList<UUID> idsToCleanUp = new ArrayList<>();
		for (UUID id : previousAnimations.keySet()) {
			if (!usedIds.contains(id)) {
				idsToCleanUp.add(id);
			}
		}
		for (UUID id : frameTimesPerObject.keySet()) {
			if (!usedIds.contains(id)) {
				idsToCleanUp.add(id);
			}
		}

		for (UUID id : idsToCleanUp) {
			previousAnimations.remove(id);
			frameTimesPerObject.remove(id);
		}
		usedIds.clear();
	}

	public void update(final float delta) {
		frameTimesPerObject.replaceAll((k, v) -> v = v + delta);
		cleanUpOldAnimations();
	}

}
