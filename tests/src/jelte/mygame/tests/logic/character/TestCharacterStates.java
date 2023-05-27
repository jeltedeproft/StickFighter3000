package jelte.mygame.tests.logic.character;

import static org.mockito.Mockito.doNothing;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.math.Vector2;

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManager.AudioCommand;
import jelte.mygame.graphical.audio.MusicManager.AudioEnum;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterFileReader;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.character.state.CharacterStateManager.STATE;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;

@RunWith(GdxTestRunner.class)
public class TestCharacterStates {
	private Character testCharacter;

	@Mock
	private MusicManagerInterface mockMusicManager;

	@InjectMocks
	private CharacterStateManager characterStateManager;

	@BeforeClass
	public static void beforeAllTests() {
		CharacterFileReader.loadUnitStatsInMemory();
	}

	@Before
	public void beforeEverytest() {
		testCharacter = new Character(CharacterFileReader.getUnitData().get(4), UUID.randomUUID());
		characterStateManager = new CharacterStateManager(testCharacter);
		MockitoAnnotations.openMocks(this);
		MusicManager.setInstance(mockMusicManager);
	}

	// APPEAR

	@Test
	public void testStartInAppear() {
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.APPEARING);
	}

	@Test
	public void testAppearUpdate() {
		characterStateManager.update(1000);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.IDLE);
	}

	@Test
	public void testAppearToAttack() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_APPEAR1);
		characterStateManager.handleEvent(EVENT.ATTACK_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.ATTACKING);
	}

	@Test
	public void testAppearToHurt() {
		characterStateManager.handleEvent(EVENT.DAMAGE_TAKEN);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.HURT);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testAppearToJumping() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_JUMP1);
		characterStateManager.handleEvent(EVENT.JUMP_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.JUMPING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == Constants.JUMP_SPEED.y);
	}

	@Test
	public void testAppearToWalkingLeft() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_WALK1);
		characterStateManager.handleEvent(EVENT.LEFT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.WALKING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == -Constants.MOVEMENT_SPEED);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getDirection() == Direction.left);
	}

	@Test
	public void testAppearLeftUnpressed() {
		characterStateManager.handleEvent(EVENT.LEFT_UNPRESSED);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 0);
	}

	@Test
	public void testAppearToWalkingRight() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_WALK1);
		characterStateManager.handleEvent(EVENT.RIGHT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.WALKING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == Constants.MOVEMENT_SPEED);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getDirection() == Direction.right);
	}

	@Test
	public void testAppearRightUnpressed() {
		characterStateManager.handleEvent(EVENT.RIGHT_UNPRESSED);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 0);
	}

	@Test
	public void testAppearToTeleportRight() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT1);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.right);
		characterStateManager.handleEvent(EVENT.TELEPORT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == Constants.TELEPORT_DISTANCE);
	}

	@Test
	public void testAppearToTeleportLeft() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT1);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
		characterStateManager.handleEvent(EVENT.TELEPORT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == 0);
	}

	@Test
	public void testAppearToTeleportLeft2() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT1);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
		characterStateManager.getCharacter().getPhysicsComponent().setPosition(new Vector2(Constants.TELEPORT_DISTANCE, 0));
		characterStateManager.handleEvent(EVENT.TELEPORT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == 0);
	}

	@Test
	public void testAppearToDash() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_DASH1);
		characterStateManager.handleEvent(EVENT.DASH_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.DASHING);
	}

	@Test
	public void testAppearToRoll() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_ROLL1);
		characterStateManager.handleEvent(EVENT.ROLL_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.ROLLING);
	}

	@Test
	public void testAppearToBlock() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_SHIELD1);
		characterStateManager.handleEvent(EVENT.BLOCK_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.BLOCKING);
	}

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	// ATTACK //////////////////////////////////////////////
	private void switchToAttackingState() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_ATTACK1);
		characterStateManager.transition(STATE.ATTACKING);
	}

	@Test
	public void testAttackUpdate() {
		switchToAttackingState();
		characterStateManager.update(1000);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.IDLE);
	}

	@Test
	public void testAttackToHurt() {
		switchToAttackingState();
		characterStateManager.handleEvent(EVENT.DAMAGE_TAKEN);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.HURT);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testAttackLeftUnpressed() {
		switchToAttackingState();
		characterStateManager.handleEvent(EVENT.LEFT_UNPRESSED);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testAttackRightUnpressed() {
		switchToAttackingState();
		characterStateManager.handleEvent(EVENT.RIGHT_UNPRESSED);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	// BLOCKING //////////////////////////////////////////////
	private void switchToBlockingState() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_SHIELD1);
		characterStateManager.transition(STATE.BLOCKING);
	}

	@Test
	public void testBlockToIdle() {
		switchToBlockingState();
		characterStateManager.update(1000f);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.IDLE);
	}

	@Test
	public void testBlockLeftUnpressed() {
		switchToBlockingState();
		characterStateManager.handleEvent(EVENT.LEFT_UNPRESSED);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testBlockRightUnpressed() {
		switchToBlockingState();
		characterStateManager.handleEvent(EVENT.RIGHT_UNPRESSED);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testBlockToTeleportRight() {
		switchToBlockingState();
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT1);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.right);
		characterStateManager.handleEvent(EVENT.TELEPORT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == Constants.TELEPORT_DISTANCE);
	}

	@Test
	public void testBlockToTeleportLeft() {
		switchToBlockingState();
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT1);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
		characterStateManager.handleEvent(EVENT.TELEPORT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == 0);
	}

	@Test
	public void testBlockToTeleportLeft2() {
		switchToBlockingState();
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT1);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
		characterStateManager.getCharacter().getPhysicsComponent().setPosition(new Vector2(Constants.TELEPORT_DISTANCE, 0));
		characterStateManager.handleEvent(EVENT.TELEPORT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == 0);
	}

	@Test
	public void testBlockToDash() {
		switchToBlockingState();
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_DASH1);
		characterStateManager.handleEvent(EVENT.DASH_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.DASHING);
	}

	@Test
	public void testBlockToRoll() {
		switchToBlockingState();
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_ROLL1);
		characterStateManager.handleEvent(EVENT.ROLL_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), STATE.ROLLING);
	}

}
