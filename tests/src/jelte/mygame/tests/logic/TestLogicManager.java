package jelte.mygame.tests.logic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.graphical.map.EnemySpawnData;
import jelte.mygame.logic.LogicManagerImpl;
import jelte.mygame.logic.collisions.collidable.StaticBlock;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestLogicManager {

	private LogicManagerImpl logicManager;
	private MessageListener listener;

	@Before
	public void setup() {
		listener = mock(MessageListener.class);
		logicManager = new LogicManagerImpl(listener);
	}

	@Test
	public void testUpdate() {
		// Add test cases to test the update() method
		// For example, you can mock dependencies like CharacterManager, AiManager, and SpellManager
		// and verify their interactions with the LogicManagerImpl during the update()
	}

	@Test
	public void testReceiveMessage_PlayerAction() {
		// Test receiveMessage() with a player action message
		Message playerActionMessage = new Message(RECIPIENT.LOGIC, ACTION.SEND_BUTTONS_MAP);
		logicManager.receiveMessage(playerActionMessage);

		// Verify that the message is forwarded to the PlayerCharacter
		verify(logicManager).receiveMessage(playerActionMessage);

		// TODO mock characterManager and check effects
	}

	@Test
	public void testReceiveMessage_SendMouseCoordinates() {
		// Test receiveMessage() with a message to send mouse coordinates
		float mouseX = 50f;
		float mouseY = 70f;
		Message mouseCoordinateMessage = new Message(RECIPIENT.LOGIC, ACTION.SEND_MOUSE_COORDINATES, new Vector3(mouseX, mouseY, 0f));
		logicManager.receiveMessage(mouseCoordinateMessage);

		// Verify that the mouse coordinates are correctly updated in the LogicManagerImpl
		assertEquals(mouseX, logicManager.getMousePosition().x, 0.001f);
		assertEquals(mouseY, logicManager.getMousePosition().y, 0.001f);
	}

	@Test
	public void testReceiveMessage_SpawnEnemies() {
		// Test receiveMessage() with a message to spawn enemies
		EnemySpawnData spawnData = new EnemySpawnData();
		spawnData.setType("2");
		spawnData.setSpawnPoint(new Vector2(10, 10));
		Collection<EnemySpawnData> enemySpawnData = Collections.singletonList(new EnemySpawnData());
		Message spawnEnemiesMessage = new Message(RECIPIENT.LOGIC, ACTION.SPAWN_ENEMIES, enemySpawnData);
		logicManager.receiveMessage(spawnEnemiesMessage);

		// Verify that the enemies are correctly spawned in the CharacterManager
		// and the AiManager is updated with the new enemies
		// (You may need to use mock objects for CharacterManager and AiManager to verify these interactions)

	}

	@Test
	public void testReceiveMessage_SendMapDimensions() {
		// Test receiveMessage() with a message to send map dimensions
		float mapWidth = 800f;
		float mapHeight = 600f;
		Vector2 mapDimensions = new Vector2(mapWidth, mapHeight);
		Set<StaticBlock> blockingObjects = new HashSet<>();
		Message mapDimensionsMessage = new Message(RECIPIENT.LOGIC, ACTION.SEND_MAP_DIMENSIONS, mapDimensions);
		logicManager.receiveMessage(mapDimensionsMessage);

		// Verify that the CollisionDetectionSystem is initialized with the map dimensions
		// and the blocking objects are set in the CollisionDetectionSystem
		// (You may need to use mock objects for the CollisionDetectionSystem to verify these interactions)
	}

	// Add more test methods to cover other behaviors of LogicManagerImpl

	// Helper methods or additional setup can be added as needed
}
