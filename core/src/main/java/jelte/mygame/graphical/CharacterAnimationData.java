package jelte.mygame.graphical;

import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.CharacterStateManager.Action;
import jelte.mygame.logic.Direction;
import jelte.mygame.utility.AssetManagerUtility;
import lombok.ToString;

@ToString
public class CharacterAnimationData{
	private final String wizardName;
	private final Map<Action, Array<Direction>> animations;
	


	// INIT
	public CharacterAnimationData(String wizardName) {
		this.wizardName = wizardName;
		animations = new EnumMap<>(Action.class);
		for (final Action action : Action.values()) {
			animations.put(action, new Array<>());
		}

		initAvailableActionsDirections(wizardName);
	}

	private void initAvailableActionsDirections(String wizardName) {
		final Array<AtlasRegion> regions = AssetManagerUtility.getAllRegionsWhichContainName(wizardName);
		for (final AtlasRegion region : regions) {
			final String[] parts = region.name.split("-");
			if ((parts.length >= 3) && (parts[0].equalsIgnoreCase(wizardName))) {
				final Action action = Action.valueOf(parts[1]);
				final Direction direction = Direction.valueOf(parts[2]);
				animations.get(action).add(direction);
			}
		}
	}

	// GET
	public String getAnimationName(Action action, Direction direction) {
		if (hasAnimation(action, direction)) {
			return wizardName + "-" + action.name() + "-" + direction.name();
		}
		return getStandardAnimationAsString();
	}


	public boolean hasAnimation(Action action, Direction direction) {
		if (animations.containsKey(action)) {
			return animations.get(action).contains(direction, false);
		}
		return false;
	}


	public String getStandardAnimationAsString() {
		return wizardName + "-" + Action.idle.name() + "-" + Direction.right.name();
	}

}
