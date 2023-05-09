package jelte.mygame.graphical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterFileReader;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.utility.AssetManagerUtility;

public class AnimationNameManager {

	private final Map<UUID, CharacterAnimation> animationNames;
	private final Map<String, Array<STATE>> possibleAnimationsCharacter;
	private final ConcurrentLinkedQueue<UUID> usedIds;

	public AnimationNameManager() {
		animationNames = new HashMap<>();
		possibleAnimationsCharacter = new HashMap<>();
		usedIds = new ConcurrentLinkedQueue<>();
		initializeAvailableStates();
	}

	public boolean animationExists(String spriteName, STATE state) {
		return possibleAnimationsCharacter.get(spriteName).contains(state, false);
	}

	public String getFirstName() {
		return (String) possibleAnimationsCharacter.keySet().toArray()[0];
	}

	public void update() {
		clean();
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
		CharacterAnimation characterAnimation = getCharacterAnimation(character);
		if (character.getCurrentCharacterState().getState().equals(STATE.STOPRUNNING)) {
			int j = 5;
		}
		STATE state = animationExists(character.getData().getEntitySpriteName(), character.getCurrentCharacterState().getState()) ? character.getCurrentCharacterState().getState() : STATE.IDLE;
		Gdx.app.debug("nameManager", "state = " + state);
		characterAnimation.updateData(character.getData().getEntitySpriteName(), 1, character.getPhysicsComponent().getDirection(), state);// TODO randomize the index for which animation should play
		System.out.println("returning fullname = " + characterAnimation.getFullName());
		return characterAnimation.getFullName();
	}

	public CharacterAnimation getCharacterAnimation(Character character) {
		CharacterAnimation animation = animationNames.get(character.getId());
		if (animation == null) {
			animationNames.put(character.getId(), new CharacterAnimation(character.getName(), character.getCurrentCharacterState().getState(), 1, character.getPhysicsComponent().getDirection()));
			return animationNames.get(character.getId());
		}
		return animation;
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
			possibleAnimationsCharacter.put(characterName, availableStates);

		}
	}

}
