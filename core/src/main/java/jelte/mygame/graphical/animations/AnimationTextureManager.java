package jelte.mygame.graphical.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.spells.spells.AbstractSpell;
import jelte.mygame.logic.spells.state.SpellStateManager.SPELL_STATE;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;

public class AnimationTextureManager {
	private static final String TAG = AnimationTextureManager.class.getSimpleName();
	private final Map<String, Animation<NamedSprite>> cache;// TODO make class and optimize, remove all animations of character that is dead for example
	private final Map<UUID, String> previous;
	private final Map<UUID, Float> timers;
	private final Map<CHARACTER_STATE, PlayMode> characterPlayModes;
	private final Map<SPELL_STATE, PlayMode> spellPlayModes;
	private final ConcurrentLinkedQueue<UUID> usedIds;

	public AnimationTextureManager() {
		cache = new HashMap<>();
		previous = new HashMap<>();
		timers = new HashMap<>();
		characterPlayModes = new EnumMap<>(CHARACTER_STATE.class);
		spellPlayModes = new EnumMap<>(SPELL_STATE.class);
		usedIds = new ConcurrentLinkedQueue<>();
		initPlayModes();
	}

	private void initPlayModes() {
		characterPlayModes.put(CHARACTER_STATE.APPEARING, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.ATTACKING, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.DIE, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.HURT, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.IDLE, PlayMode.LOOP);
		characterPlayModes.put(CHARACTER_STATE.JUMPING, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.WALKING, PlayMode.LOOP);
		characterPlayModes.put(CHARACTER_STATE.RUNNING, PlayMode.LOOP);
		characterPlayModes.put(CHARACTER_STATE.STOPRUNNING, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.CLIMBING, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.SPRINTING, PlayMode.LOOP);
		characterPlayModes.put(CHARACTER_STATE.FALLING, PlayMode.LOOP);
		characterPlayModes.put(CHARACTER_STATE.PRECAST, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.CAST, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.CROUCHED, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.LANDING, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.DASHING, PlayMode.LOOP);
		characterPlayModes.put(CHARACTER_STATE.IDLECROUCH, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.HOLDING, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.BLOCKING, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.TELEPORTING, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.GRABBING, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.ROLLATTACK, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.ROLLING, PlayMode.LOOP);
		characterPlayModes.put(CHARACTER_STATE.WALLSLIDING, PlayMode.LOOP);
		characterPlayModes.put(CHARACTER_STATE.WALLSLIDINGSTOP, PlayMode.LOOP);
		characterPlayModes.put(CHARACTER_STATE.FALLATTACKING, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.LANDATTACKING, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.HOLDINGTOSLIDING, PlayMode.NORMAL);
		characterPlayModes.put(CHARACTER_STATE.JUMPTOFALL, PlayMode.NORMAL);
		spellPlayModes.put(SPELL_STATE.DEAD, PlayMode.NORMAL);
		spellPlayModes.put(SPELL_STATE.LOOP, PlayMode.LOOP);
		spellPlayModes.put(SPELL_STATE.WINDUP, PlayMode.NORMAL);
		spellPlayModes.put(SPELL_STATE.END, PlayMode.NORMAL);
	}

	public void addUsedId(UUID id) {
		usedIds.add(id);
	}

	public Animation<NamedSprite> checkCache(String animationName) {
		return cache.get(animationName);
	}

	public PlayMode getPlayMode(CHARACTER_STATE state) {
		return characterPlayModes.getOrDefault(state, PlayMode.NORMAL);
	}

	public PlayMode getPlayMode(SPELL_STATE state) {
		return spellPlayModes.getOrDefault(state, PlayMode.NORMAL);
	}

	public void cache(String animationName, Animation<NamedSprite> animation) {
		cache.put(animationName, animation);
	}

	public void update(final float delta) {
		timers.replaceAll((k, v) -> v + delta);
		// clean();//TODO check if this is ok
	}

	public void clean() {
		timers.keySet().removeIf(id -> !usedIds.contains(id));
		usedIds.clear();
	}

	public boolean exists(String animationName) {
		return AssetManagerUtility.animationExists(animationName);
	}

	public Animation<NamedSprite> getAnimationNoCache(String animationName, Character character) {
		Animation<NamedSprite> animation = AssetManagerUtility.getAnimation(animationName, getFrameDuration(character), getPlayMode(character.getCurrentCharacterState().getState()));// TODO get state from name here because state of character might be different
		if (animation == null) {
			return null;
		}
		return animation;
	}

	public NamedSprite getSprite(String animationName, Character character) {
		addUsedId(character.getId());

		final String previousAnimationName = previous.get(character.getId());
		// new animation or changed animation, reset frameTime
		if (previousAnimationName == null || !previousAnimationName.equals(animationName)) {
			previous.put(character.getId(), animationName);
			timers.put(character.getId(), 0f);
		}

		// is animation already loaded?
		Animation<NamedSprite> animation = checkCache(animationName);
		if (animation == null) {
			animation = AssetManagerUtility.getAnimation(animationName, getFrameDuration(character), getPlayMode(character.getCurrentCharacterState().getState()));// TODO get state from name here because state of character might be different
			if (animation == null) {
				Gdx.app.debug(TAG, String.format("Cannot find animation of this type: %s", animationName));
				return null;
			}
			cache(animationName, animation);

		}

		final float frameTime = timers.get(character.getId());

		return animation.getKeyFrame(frameTime);
	}

	public NamedSprite getSprite(String animationName, AbstractSpell spell) {
		addUsedId(spell.getId());

		final String previousAnimationName = previous.get(spell.getId());
		// new animation or changed animation, reset frameTime
		if (previousAnimationName == null || !previousAnimationName.equals(animationName)) {
			previous.put(spell.getId(), animationName);
			timers.put(spell.getId(), 0f);
		}

		// is animation already loaded?
		Animation<NamedSprite> animation = checkCache(animationName);
		if (animation == null) {
			animation = AssetManagerUtility.getAnimation(animationName, getFrameDuration(spell), getPlayMode(spell.getSpellStateManager().getCurrentSpellState().getState()));// TODO get state from name here because state of character might be different
			if (animation == null) {
				Gdx.app.debug(TAG, String.format("Cannot find animation of this type: %s", animationName));
				return null;
			}
			cache(animationName, animation);

		}

		final float frameTime = timers.get(spell.getId());

		return animation.getKeyFrame(frameTime);
	}

	private float getFrameDuration(Character character) {
		return switch (character.getCurrentCharacterState().getState()) {
		case APPEARING -> character.getData().getAppearFrameDuration();
		case ATTACKING -> character.getData().getAttackFrameDuration();
		case DIE -> character.getData().getDieFrameDuration();
		case HURT -> character.getData().getHurtFrameDuration();
		case IDLE -> character.getData().getIdleFrameDuration();
		case JUMPING -> character.getData().getJumpFrameDuration();
		case RUNNING -> character.getData().getRunningFrameDuration();
		case WALKING -> character.getData().getRunningFrameDuration();
		case SPRINTING -> character.getData().getRunningFrameDuration();
		case FALLATTACKING -> character.getData().getFallAttackingFrameDuration();
		default -> Constants.DEFAULT_ANIMATION_SPEED;
		};
	}

	private float getFrameDuration(AbstractSpell spell) {
		return switch (spell.getSpellStateManager().getCurrentSpellState().getState()) {
		case END -> spell.getData().getEndFrameDuration();
		case LOOP -> spell.getData().getLoopFrameDuration();
		case WINDUP -> spell.getData().getWindupFrameDuration();
		case DEAD -> Constants.DEFAULT_ANIMATION_SPEED;
		default -> Constants.DEFAULT_ANIMATION_SPEED;
		};
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<UUID, String> anim : previous.entrySet()) {
			sb.append(anim.getKey());
			sb.append(" --> ");
			sb.append(anim.getValue());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

}