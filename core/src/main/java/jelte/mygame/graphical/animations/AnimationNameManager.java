package jelte.mygame.graphical.animations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterFileReader;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.spells.AbstractSpell;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.UtilityFunctions;

public class AnimationNameManager {

	private final Map<UUID, AnimationName> animationNames;
	private final Map<String, Map<CHARACTER_STATE, Set<String>>> possibleAnimationsCharacter;
	private final ConcurrentLinkedQueue<UUID> usedIds;

	public AnimationNameManager() {
		animationNames = new HashMap<>();
		possibleAnimationsCharacter = new HashMap<>();
		usedIds = new ConcurrentLinkedQueue<>();
		initializeAvailableStates();
	}

	public boolean animationsExists(String spriteName, CHARACTER_STATE state) {
		return possibleAnimationsCharacter.get(spriteName).containsKey(state);
	}

	public void update() {
		// clean();//TODO check if this is ok
	}

	public void clean() {
		ArrayList<UUID> idsToCleanUp = new ArrayList<>();
		for (UUID id : animationNames.keySet()) {
			if (!usedIds.contains(id)) {
				idsToCleanUp.add(id);
			}
		}
		for (UUID id : idsToCleanUp) {
			animationNames.remove(id);
		}
		usedIds.clear();
	}

	public String getAnimationName(Character character) {
		CHARACTER_STATE state = animationsExists(character.getData().getEntitySpriteName(), character.getCurrentCharacterState().getState()) ? character.getCurrentCharacterState().getState() : CHARACTER_STATE.IDLE;
		CharacterAnimation characterAnimation = (CharacterAnimation) getCharacterAnimation(character, state);
		String animationIndex = state.equals(characterAnimation.getState()) ? characterAnimation.getAnimationIndex() : getRandomAnimationIndex(character.getData().getEntitySpriteName(), state);
		characterAnimation.updateData(character.getData().getEntitySpriteName(), animationIndex, character.getPhysicsComponent().getDirection(), state);
		return characterAnimation.getFullName();
	}

	private String getRandomAnimationIndex(String spriteName, CHARACTER_STATE state) {
		return UtilityFunctions.getRandomValueFromSet(possibleAnimationsCharacter.get(spriteName).get(state));
	}

	public AnimationName getCharacterAnimation(Character character, CHARACTER_STATE state) {
		AnimationName animation = animationNames.get(character.getId());
		if (animation == null) {
			animationNames.put(character.getId(), new CharacterAnimation(character.getName(), character.getCurrentCharacterState().getState(), getRandomAnimationIndex(character.getData().getEntitySpriteName(), state), character.getPhysicsComponent().getDirection()));
			return animationNames.get(character.getId());
		}
		return animation;
	}

	public String getAnimationName(AbstractSpell spell) {
		SpellAnimation spellAnimation = (SpellAnimation) getSpellAnimation(spell);
		spellAnimation.updateData(spell.getData().getSpriteName(), spell.getSpellStateManager().getCurrentSpellState().getState());
		return spellAnimation.getFullName();
	}

	public AnimationName getSpellAnimation(AbstractSpell spell) {
		AnimationName animation = animationNames.get(spell.getId());
		if (animation == null) {
			animationNames.put(spell.getId(), new SpellAnimation(spell.getName(), spell.getSpellStateManager().getCurrentSpellState().getState()));
			return animationNames.get(spell.getId());
		}
		return animation;
	}

	public void initializeAvailableStates() {
		for (String characterName : CharacterFileReader.getAllCharacterNames()) {
			Map<CHARACTER_STATE, Set<String>> availableStates = new HashMap<>();
			final Array<AtlasRegion> regions = AssetManagerUtility.getAllRegionsWhichContainName(characterName);
			for (final AtlasRegion region : regions) {
				final String[] parts = region.name.split("-");
				if (parts.length >= 3 && parts[0].equalsIgnoreCase(characterName)) {
					int length = parts[1].length();
					final String statePart = parts[1].substring(0, length - 1).toUpperCase();
					final String indexPart = parts[1].substring(length - 1);

					final CHARACTER_STATE state = CHARACTER_STATE.valueOf(statePart);
					availableStates.computeIfAbsent(state, key -> new HashSet<>());
					availableStates.get(state).add(indexPart);
				}
			}
			possibleAnimationsCharacter.put(characterName, availableStates);

		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (AnimationName anim : animationNames.values()) {
			sb.append(anim);
			sb.append("\n");
		}
		return sb.toString();
	}

}
