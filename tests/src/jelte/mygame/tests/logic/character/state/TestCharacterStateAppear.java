package jelte.mygame.tests.logic.character.state;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.character.state.CharacterStateAppear;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestCharacterStateAppear {

	private CharacterStateManager characterStateManager;
	private CharacterStateAppear characterState;

	@Before
	public void setup() {
		characterStateManager = mock(CharacterStateManager.class);
		float duration = 1.5f; // Example duration
		characterState = new CharacterStateAppear(characterStateManager, duration);
	}

	@Test
	public void testEntry() {
		characterState.entry();
		// Assert any expected behavior during the entry phase
	}

	@Test
	public void testUpdate() {
		float delta = 0.1f; // Example delta time

		// Call update multiple times until the timer reaches zero
		while (characterState.getTimer() > 0) {
			characterState.update(delta);
			// Assert any expected behavior during the update phase
		}

		// Verify that the character state manager transitions to the expected state
		verify(characterStateManager).transition(CHARACTER_STATE.IDLE);
	}

	@Test
	public void testHandleEvent() {
		EVENT event = EVENT.ATTACK_PRESSED;
		characterState.handleEvent(event);
		// Assert any expected behavior based on the handled event

		// Verify that the character state manager transitions to the expected state
		verify(characterStateManager).transition(CHARACTER_STATE.ATTACKING);
	}

	@Test
	public void testExit() {
		characterState.exit();
		// Assert any expected behavior during the exit phase
	}

	// Add more test cases for other methods as needed

}
