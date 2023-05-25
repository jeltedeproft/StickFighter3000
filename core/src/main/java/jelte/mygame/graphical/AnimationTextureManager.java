package jelte.mygame.graphical;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.SpriteUtils;
import jelte.mygame.utility.UtilityFunctions;

public class AnimationTextureManager {
	private static final String TAG = AnimationTextureManager.class.getSimpleName();
	private final Map<String, Animation<CharacterSprite>> cache;// TODO make class and optimize, remove all animations of character that is dead for example
	private final Map<String, Float> animationNameToOffset;
	private final Map<UUID, String> previous;
	private final Map<UUID, Float> timers;
	private final Map<STATE, PlayMode> playmodes;
	private final ConcurrentLinkedQueue<UUID> usedIds;

	public AnimationTextureManager() {
		animationNameToOffset = new HashMap<>();
		cache = new HashMap<>();
		previous = new HashMap<>();
		timers = new HashMap<>();
		playmodes = new EnumMap<>(STATE.class);
		usedIds = new ConcurrentLinkedQueue<>();
		initPlayModes();
		calculateOffsets();
	}

	private void calculateOffsets() {
		List<String> spriteNames = UtilityFunctions.findImageFiles(Constants.IMAGE_FILES_PATH);
		for (String absolutePath : spriteNames) {
			String imageName = SpriteUtils.imageNameFromAbsolutePath(absolutePath);
			animationNameToOffset.put(imageName, SpriteUtils.calculateAttackOffset(SpriteUtils.convertToTextureRegion(absolutePath), imageName.contains("left")));
		}
	}

	private void initPlayModes() {
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
		playmodes.put(STATE.JUMPTOFALL, PlayMode.NORMAL);
	}

	public void addUsedId(UUID id) {
		usedIds.add(id);
	}

	public Animation<CharacterSprite> checkCache(String animationName) {
		return cache.get(animationName);
	}

	public PlayMode getPlayMode(STATE state) {
		return playmodes.get(state);
	}

	public void cache(String animationName, Animation<CharacterSprite> animation) {
		cache.put(animationName, animation);
	}

	public void update(final float delta) {
		timers.replaceAll((k, v) -> v = v + delta);
		clean();
	}

	public void clean() {
		ArrayList<UUID> idsToCleanUp = new ArrayList<>();
		for (UUID id : timers.keySet()) {
			if (!usedIds.contains(id)) {
				idsToCleanUp.add(id);
			}
		}
		for (UUID id : idsToCleanUp) {
			timers.remove(id);
		}
		usedIds.clear();
	}

	public CharacterSprite getSprite(String animationName, Character character) {
		addUsedId(character.getId());

		final String previousAnimationName = previous.get(character.getId());
		// new animation or changed animation, reset frameTime
		if (previousAnimationName == null || !previousAnimationName.equals(animationName)) {
			previous.put(character.getId(), animationName);
			timers.put(character.getId(), 0f);
		}

		// is animation already loaded?
		Animation<CharacterSprite> animation = checkCache(animationName);
		if (animation == null) {
			Animation<Sprite> spriteAnimation = AssetManagerUtility.getAnimation(animationName, getFrameDuration(character), getPlayMode(character.getCurrentCharacterState().getState()));// TODO get state from name here because state of character might be different
			if (spriteAnimation == null) {
				Gdx.app.debug(TAG, "cannot find animation of this type : " + animationName);
				return null;
			}
			Array<CharacterSprite> characterSprites = new Array<>();
			for (Sprite sprite : spriteAnimation.getKeyFrames()) {
				characterSprites.add(new CharacterSprite(sprite, animationName, getSpriteOffset(animationName, spriteAnimation.getKeyFrameIndex(timers.get(character.getId())))));
			}
			animation = new Animation<>(getFrameDuration(character), characterSprites, getPlayMode(character.getCurrentCharacterState().getState()));
			cache(animationName, animation);

		}

		final float frameTime = timers.get(character.getId());

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
		case WALKING:
			return character.getData().getRunningFrameDuration();
		case SPRINTING:
			return character.getData().getRunningFrameDuration();
		default:
			return Constants.DEFAULT_ANIMATION_SPEED;
		}
	}

	public float getSpriteOffset(String spriteName, int index) {
		return animationNameToOffset.get(spriteName + "_" + (index + 1));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<UUID, String> anim : previous.entrySet()) {
			sb.append(anim.getKey());
			sb.append(" --> ");
			sb.append(anim.getValue());
			sb.append("\n");
		}
		return sb.toString();
	}

}
