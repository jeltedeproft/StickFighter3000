package jelte.mygame.tests.graphical.animations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.graphical.animations.CharacterAnimation;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestCharacterAnimation {

	@Test
	public void testCharacterAnimation() {
		String characterName = "Player";
		CHARACTER_STATE state = CHARACTER_STATE.IDLE;
		String animationIndex = "1";
		Direction direction = Direction.right;

		CharacterAnimation characterAnimation = new CharacterAnimation(characterName, state, animationIndex, direction);

		assertEquals(characterName + "-" + state.toString() + animationIndex + "-" + direction, characterAnimation.getFullName());
		assertEquals(0f, characterAnimation.getTimeRunning(), 0.001f);
	}

	@Test
	public void testChangeCharacter() {
		String characterName = "Player";
		String newCharacterName = "Enemy";
		CHARACTER_STATE state = CHARACTER_STATE.IDLE;
		String animationIndex = "1";
		Direction direction = Direction.right;

		CharacterAnimation characterAnimation = new CharacterAnimation(characterName, state, animationIndex, direction);
		characterAnimation.changeCharacter(newCharacterName);

		assertEquals(newCharacterName + "-" + state.toString() + animationIndex + "-" + direction, characterAnimation.getFullName());
		assertEquals(0f, characterAnimation.getTimeRunning(), 0.001f);
	}

	@Test
	public void testChangeState() {
		String characterName = "Player";
		CHARACTER_STATE initialState = CHARACTER_STATE.IDLE;
		CHARACTER_STATE newState = CHARACTER_STATE.RUNNING;
		String animationIndex = "1";
		Direction direction = Direction.right;

		CharacterAnimation characterAnimation = new CharacterAnimation(characterName, initialState, animationIndex, direction);
		characterAnimation.changeState(newState);

		assertEquals(characterName + "-" + newState.toString() + animationIndex + "-" + direction, characterAnimation.getFullName());
		assertEquals(0f, characterAnimation.getTimeRunning(), 0.001f);
	}

	@Test
	public void testChangeAnimationIndex() {
		String characterName = "Player";
		CHARACTER_STATE state = CHARACTER_STATE.IDLE;
		String initialAnimationIndex = "1";
		String newAnimationIndex = "2";
		Direction direction = Direction.right;

		CharacterAnimation characterAnimation = new CharacterAnimation(characterName, state, initialAnimationIndex, direction);
		characterAnimation.changeAnimationIndex(newAnimationIndex);

		assertEquals(characterName + "-" + state.toString() + newAnimationIndex + "-" + direction, characterAnimation.getFullName());
		assertEquals(0f, characterAnimation.getTimeRunning(), 0.001f);
	}

	@Test
	public void testChangeDirection() {
		String characterName = "Player";
		CHARACTER_STATE state = CHARACTER_STATE.IDLE;
		String animationIndex = "1";
		Direction initialDirection = Direction.right;
		Direction newDirection = Direction.left;

		CharacterAnimation characterAnimation = new CharacterAnimation(characterName, state, animationIndex, initialDirection);
		characterAnimation.changeDirection(newDirection);

		assertEquals(characterName + "-" + state.toString() + animationIndex + "-" + newDirection, characterAnimation.getFullName());
		assertEquals(0f, characterAnimation.getTimeRunning(), 0.001f);
	}

	@Test
	public void testUpdate() {
		String characterName = "Player";
		CHARACTER_STATE state = CHARACTER_STATE.IDLE;
		String animationIndex = "1";
		Direction direction = Direction.right;

		CharacterAnimation characterAnimation = new CharacterAnimation(characterName, state, animationIndex, direction);
		characterAnimation.update(0.5f);

		assertEquals(0.5f, characterAnimation.getTimeRunning(), 0.001f);
	}

	@Test
	public void testUpdateData() {
		String characterName = "Player";
		String newCharacterName = "Enemy";
		String animationIndex = "1";
		Direction direction = Direction.right;
		CHARACTER_STATE initialState = CHARACTER_STATE.IDLE;
		CHARACTER_STATE newState = CHARACTER_STATE.RUNNING;

		CharacterAnimation characterAnimation = new CharacterAnimation(characterName, initialState, animationIndex, direction);
		boolean changed = characterAnimation.updateData(newCharacterName, animationIndex, direction, newState);

		assertTrue(changed);
		assertEquals(newCharacterName + "-" + newState.toString() + animationIndex + "-" + direction, characterAnimation.getFullName());
		assertEquals(0f, characterAnimation.getTimeRunning(), 0.001f);
	}

}
