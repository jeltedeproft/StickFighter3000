package jelte.mygame.graphical;

import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpellAnimation {
	private String spellName;
	private CHARACTER_STATE state;
	private int animationIndex;
	private Direction direction;
	private Float timeRunning = 0f;
	private String fullName;

	public SpellAnimation(String characterName, CHARACTER_STATE state, int animationIndex, Direction direction) {
		this.characterName = characterName;
		this.state = state;
		this.animationIndex = animationIndex;
		this.direction = direction;
		rebuildName();
	}

	public void changeCharacter(String newCharacterName) {
		characterName = newCharacterName;
		rebuildName();
		resetTimer();
	}

	public void changeState(CHARACTER_STATE newState) {
		state = newState;
		rebuildName();
		resetTimer();
	}

	public void changeAnimationIndex(int newAnimationIndex) {
		animationIndex = newAnimationIndex;
		rebuildName();
		resetTimer();
	}

	public void changeDirection(Direction newDirection) {
		direction = newDirection;
		rebuildName();
		resetTimer();
	}

	public void rebuildName() {
		StringBuilder sb = new StringBuilder();
		sb.append(characterName);
		sb.append("-");
		sb.append(state.toString());
		sb.append(animationIndex);
		sb.append("-");
		sb.append(direction);
		fullName = sb.toString();
	}

	public String getFullName() {
		return fullName;
	}

	public void resetTimer() {
		timeRunning = 0f;
	}

	public void update(float delta) {
		timeRunning += delta;
	}

	public boolean updateData(String characterName, int animationIndex, Direction direction, CHARACTER_STATE state) {
		boolean changed = false;
		if (!characterName.equals(this.characterName)) {
			changed = true;
			this.characterName = characterName;
		}
		if (animationIndex != this.animationIndex) {
			changed = true;
			this.animationIndex = animationIndex;
		}
		if (!direction.equals(this.direction)) {
			changed = true;
			this.direction = direction;
		}
		if (!state.equals(this.state)) {
			changed = true;
			this.state = state;
		}
		if (changed) {
			resetTimer();
			rebuildName();
		}
		return changed;
	}

	@Override
	public String toString() {
		return fullName;
	}

}
