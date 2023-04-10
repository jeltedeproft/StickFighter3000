package jelte.mygame.graphical;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.Direction;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterFileReader;
import jelte.mygame.logic.character.state.CharacterState;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import lombok.ToString;

@ToString
public class AnimationManager {
	private static final String TAG = AnimationManager.class.getSimpleName();

	private final Map<UUID, String> animationsPrevious;
	private final Map<UUID, Float> animationsTimers;
	private final Map<String, Animation<Sprite>> animationsCache;
	private final Map<String, Array<STATE>> animationsPossible;
	private final Map<STATE, PlayMode> playmodes;
	private final ConcurrentLinkedQueue<UUID> usedIds;

	public AnimationManager() {
		animationsPrevious = new HashMap<>();
		animationsCache = new HashMap<>();
		animationsPossible = new HashMap<>();
		animationsTimers = new HashMap<>();
		playmodes = new EnumMap<>(STATE.class);
		usedIds = new ConcurrentLinkedQueue<>();
		initializeAvailableStates();
		playmodes.put(STATE.APPEARING, PlayMode.NORMAL);
		playmodes.put(STATE.ATTACKING, PlayMode.NORMAL);
		playmodes.put(STATE.DIE, PlayMode.NORMAL);
		playmodes.put(STATE.HURT, PlayMode.NORMAL);
		playmodes.put(STATE.IDLE, PlayMode.LOOP);
		playmodes.put(STATE.JUMPING, PlayMode.NORMAL);
		playmodes.put(STATE.WALKING, PlayMode.LOOP);
		playmodes.put(STATE.RUNNING, PlayMode.LOOP);
		playmodes.put(STATE.SPRINTING, PlayMode.LOOP);
		playmodes.put(STATE.FALLING, PlayMode.LOOP);
		playmodes.put(STATE.CAST, PlayMode.NORMAL);
		playmodes.put(STATE.CROUCHED, PlayMode.NORMAL);
		playmodes.put(STATE.LANDING, PlayMode.NORMAL);
		playmodes.put(STATE.STOPRUNNING, PlayMode.NORMAL);
		playmodes.put(STATE.DASHING, PlayMode.LOOP);
		playmodes.put(STATE.IDLECROUCH, PlayMode.NORMAL);
		playmodes.put(STATE.HOLDING, PlayMode.NORMAL);
		playmodes.put(STATE.BLOCKING, PlayMode.NORMAL);
		playmodes.put(STATE.TELEPORTING, PlayMode.NORMAL);
		playmodes.put(STATE.GRABBING, PlayMode.NORMAL);
		playmodes.put(STATE.ROLLATTACK, PlayMode.NORMAL);
		playmodes.put(STATE.ROLLING, PlayMode.LOOP);
		playmodes.put(STATE.WALLSLIDING, PlayMode.LOOP);
		playmodes.put(STATE.WALLSLIDINGSTOP, PlayMode.LOOP);
		playmodes.put(STATE.FALLATTACKING, PlayMode.LOOP);
	}

	public void initializeAvailableStates() {
		for (String characterName : CharacterFileReader.getAllCharacterNames()) {
			Array<STATE> availableStates = new Array<>();
			final Array<AtlasRegion> regions = AssetManagerUtility.getAllRegionsWhichContainName(characterName);
			for (final AtlasRegion region : regions) {
				final String[] parts = region.name.split("-");
				if (parts.length >= 3 && parts[0].equalsIgnoreCase(characterName)) {
					int length = parts[1].length();
					final String statePart = parts[1].substring(0, length - 1).toUpperCase();
					final String indexPart = parts[1].substring(length - 1, length - 1);

					final STATE state = STATE.valueOf(statePart);
					availableStates.add(state);
				}
			}
			animationsPossible.put(characterName, availableStates);
		}
	}

	// GET
	public Sprite getSprite(jelte.mygame.logic.character.Character character) {
		usedIds.add(character.getId());
		String spriteName = character.getData().getEntitySpriteName();
		Direction direction = character.getCurrentDirection();
		CharacterState characterState = character.getCurrentCharacterState();
		if (animationsPossible.get(spriteName).contains(characterState.getState(), false)) {
			return stringToTexture(spriteName + "-" + characterState.getState().toString() + "1" + "-" + direction.name(), character);
		}
		return stringToTexture(spriteName + "-" + STATE.IDLE.toString() + "1" + "-" + Direction.right.name(), character);// TODO randomize the index for which animation should play
	}

	public Sprite stringToTexture(String animationName, Character character) {
		usedIds.add(character.getId());
		final String previous = animationsPrevious.get(character.getId());
		// new animation or changed animation, reset frameTime
		if (previous == null || !previous.equals(animationName)) {
			animationsPrevious.put(character.getId(), animationName);
			animationsTimers.put(character.getId(), 0f);
		}

		// is animation already loaded?
		Animation<Sprite> animation = animationsCache.get(animationName);
		if (animation == null) {
			animation = AssetManagerUtility.getAnimation(animationName, getFrameDuration(character), playmodes.get(character.getCurrentCharacterState().getState()));
			if (animation == null) {
				Gdx.app.debug(TAG, "cannot find animation of this type : " + animationName);
				return null;
			}
			animationsCache.put(animationName, animation);
		}

		final float frameTime = animationsTimers.get(character.getId());

		return animation.getKeyFrame(frameTime);
	}

	private float getFrameDuration(Character character) {
		switch (character.getCurrentCharacterState().getState()) {
		case APPEARING:
			return character.getData().getAppearFrameDuration();
		case ATTACKING:
			return character.getData().getAttackFrameDuration();
		case DIE:
			return character.getData().getDieFrameDuration();
		case HURT:
			return character.getData().getHurtFrameDuration();
		case IDLE:
			return character.getData().getIdleFrameDuration();
		case JUMPING:
			return character.getData().getJumpFrameDuration();
		case RUNNING:
			return character.getData().getRunningFrameDuration();
		default:
			return Constants.DEFAULT_ANIMATION_SPEED;
		}

	}

	public void cleanUpOldAnimations() {
		ArrayList<UUID> idsToCleanUp = new ArrayList<>();
		for (UUID id : animationsPrevious.keySet()) {
			if (!usedIds.contains(id)) {
				idsToCleanUp.add(id);
			}
		}
		for (UUID id : animationsTimers.keySet()) {
			if (!usedIds.contains(id)) {
				idsToCleanUp.add(id);
			}
		}

		for (UUID id : idsToCleanUp) {
			animationsPrevious.remove(id);
			animationsTimers.remove(id);
		}
		usedIds.clear();
	}

	public void update(final float delta) {
		animationsTimers.replaceAll((k, v) -> v = v + delta);
		cleanUpOldAnimations();
	}

}
