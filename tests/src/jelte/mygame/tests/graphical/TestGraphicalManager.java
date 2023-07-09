package jelte.mygame.tests.graphical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.graphical.GraphicalManagerImpl;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.spells.spells.AbstractSpell;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.logging.MultiFileLogger;

@Ignore
@RunWith(GdxTestRunner.class)
public class TestGraphicalManager {

	private GraphicalManagerImpl graphicalManager;
	private MessageListener mockMessageListener;

	@BeforeClass
	public static void beforeAllTests() {
		ApplicationLogger logger = new MultiFileLogger();
		Gdx.app.setApplicationLogger(logger);
	}

	@Before
	public void setup() {
		// Create a mock MessageListener
		mockMessageListener = mock(MessageListener.class);

		// Create an instance of GraphicalManagerImpl
		graphicalManager = new GraphicalManagerImpl(mockMessageListener);

		// Set up LibGDX graphics
		Gdx.graphics = new MockGraphics();
	}

	@Test
	public void testUpdate() {
		// Call the update() method and assert any expected behavior or state changes
		graphicalManager.update(0.1f);

		// Add assertions based on the expected behavior or state changes after calling update()
		// For example, you can assert that certain methods were called on the mockMessageListener
		// or assert the expected rendering results if applicable
		verify(mockMessageListener).receiveMessage(any(Message.class));
	}

	@Test
	public void testReceiveMessage_CameraZoom() {
		// Set up test data
		float zoomValue = 2.0f;
		Message message = new Message(RECIPIENT.GRAPHIC, ACTION.CAMERA_ZOOM, zoomValue);

		// Call the receiveMessage() method
		graphicalManager.receiveMessage(message);

		// Assert the expected behavior or state changes
		// For example, you can assert that the cameraManager's zoomCamera() method was called with the correct value
		assertEquals(zoomValue, graphicalManager.getCameraManager().getCamera().zoom, 0.001f);
	}

	@Test
	public void testReceiveMessage_RenderPlayer() {
		// Set up test data
		PlayerCharacter player = mock(PlayerCharacter.class);
		float currentHp = 100;
		when(player.getCurrentHp()).thenReturn(currentHp);
		Message message = new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_PLAYER, player);

		// Call the receiveMessage() method
		graphicalManager.receiveMessage(message);

		// Assert the expected behavior or state changes
		// For example, you can assert that the playerHpBar's value was updated with the correct HP value
		assertEquals(currentHp, graphicalManager.getPlayerHpBar().getValue(), 0.001f);
	}

	@Test
	public void testReceiveMessage_RenderEnemy() {
		// Set up test data
		AiCharacter enemy = mock(AiCharacter.class);
		Message message = new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_ENEMY, enemy);

		// Call the receiveMessage() method
		graphicalManager.receiveMessage(message);

		// Assert the expected behavior or state changes
		// For example, you can assert that the enemyHealthBars map contains the enemy as a key
		assertTrue(graphicalManager.getEnemyHealthBars().containsKey(enemy));
	}

	@Test
	public void testReceiveMessage_RenderSpells() {
		// Set up test data
		AbstractSpell spell1 = mock(AbstractSpell.class);
		AbstractSpell spell2 = mock(AbstractSpell.class);
		AbstractSpell spell3 = mock(AbstractSpell.class);
		Array<AbstractSpell> spellsToRender = new Array<>();
		spellsToRender.add(spell1);
		spellsToRender.add(spell2);
		spellsToRender.add(spell3);
		Message message = new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_SPELLS, spellsToRender);

		// Call the receiveMessage() method
		graphicalManager.receiveMessage(message);

		// Assert the expected behavior or state changes
		// For example, you can assert that the spellsToRender array contains the expected spells
		assertEquals(spellsToRender, graphicalManager.getSpellsToRender());
	}

	@Test
	public void testReceiveMessage_ExitGame() {
		// Set up test data
		Message message = new Message(RECIPIENT.GRAPHIC, ACTION.EXIT_GAME);

		// Call the receiveMessage() method
		graphicalManager.receiveMessage(message);

		// Assert the expected behavior or state changes
		// For example, you can assert that the Gdx app is exited
		// assertTrue(Gdx.app.exitCalled());
	}

	@Test
	public void testUpdate2() {
		// Create a mock MessageListener
		MessageListener mockMessageListener = new MessageListener() {
			@Override
			public void receiveMessage(Message message) {
				// Mock implementation
			}
		};

		// Create an instance of GraphicalManagerImpl
		GraphicalManagerImpl graphicalManager = new GraphicalManagerImpl(mockMessageListener);

		// Call the update() method and assert any expected behavior or state changes
		graphicalManager.update(0.1f);

		// Add assertions based on the expected behavior or state changes after calling update()
		// For example, you can assert that certain methods were called on the mockMessageListener
		// or assert the expected rendering results if applicable
	}

	// Add more test cases for other methods as needed

}
