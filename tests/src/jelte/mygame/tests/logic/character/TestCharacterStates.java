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

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterFileReader;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
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
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.APPEARING);
	}

	@Test
	public void testAppearUpdate() {
		characterStateManager.update(1000);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.IDLE);
	}

	@Test
	public void testAppearToAttack() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_APPEAR);
		characterStateManager.handleEvent(EVENT.ATTACK_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.ATTACKING);
	}

	@Test
	public void testAppearToHurt() {
		characterStateManager.handleEvent(EVENT.DAMAGE_TAKEN);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.HURT);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testAppearToJumping() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_JUMP);
		characterStateManager.handleEvent(EVENT.JUMP_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.JUMPING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == Constants.JUMP_SPEED.y);
	}

	@Test
	public void testAppearToWalkingLeft() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_WALK);
		characterStateManager.handleEvent(EVENT.LEFT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.WALKING);
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
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_WALK);
		characterStateManager.handleEvent(EVENT.RIGHT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.WALKING);
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
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.right);
		characterStateManager.handleEvent(EVENT.TELEPORT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == Constants.TELEPORT_DISTANCE);
	}

	@Test
	public void testAppearToTeleportLeft() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
		characterStateManager.handleEvent(EVENT.TELEPORT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == 0);
	}

	@Test
	public void testAppearToTeleportLeft2() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
		characterStateManager.getCharacter().getPhysicsComponent().setPosition(new Vector2(Constants.TELEPORT_DISTANCE, 0));
		characterStateManager.handleEvent(EVENT.TELEPORT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == 0);
	}

	@Test
	public void testAppearToDash() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_DASH);
		characterStateManager.handleEvent(EVENT.DASH_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.DASHING);
	}

	@Test
	public void testAppearToRoll() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_ROLL);
		characterStateManager.handleEvent(EVENT.ROLL_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.ROLLING);
	}

	@Test
	public void testAppearToBlock() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_SHIELD);
		characterStateManager.handleEvent(EVENT.BLOCK_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.BLOCKING);
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
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_ATTACK);
		characterStateManager.transition(CHARACTER_STATE.ATTACKING);
	}

	@Test
	public void testAttackUpdate() {
		switchToAttackingState();
		characterStateManager.update(1000);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.IDLE);
	}

	@Test
	public void testAttackToHurt() {
		switchToAttackingState();
		characterStateManager.handleEvent(EVENT.DAMAGE_TAKEN);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.HURT);
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
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_SHIELD);
		characterStateManager.transition(CHARACTER_STATE.BLOCKING);
	}

	@Test
	public void testBlockToIdle() {
		switchToBlockingState();
		characterStateManager.update(1000f);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.IDLE);
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
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.right);
		characterStateManager.handleEvent(EVENT.TELEPORT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == Constants.TELEPORT_DISTANCE);
	}

	@Test
	public void testBlockToTeleportLeft() {
		switchToBlockingState();
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
		characterStateManager.handleEvent(EVENT.TELEPORT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == 0);
	}

	@Test
	public void testBlockToTeleportLeft2() {
		switchToBlockingState();
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
		characterStateManager.getCharacter().getPhysicsComponent().setPosition(new Vector2(Constants.TELEPORT_DISTANCE, 0));
		characterStateManager.handleEvent(EVENT.TELEPORT_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == 0);
	}

	@Test
	public void testBlockToDash() {
		switchToBlockingState();
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_DASH);
		characterStateManager.handleEvent(EVENT.DASH_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.DASHING);
	}

	@Test
	public void testBlockToRoll() {
		switchToBlockingState();
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_ROLL);
		characterStateManager.handleEvent(EVENT.ROLL_PRESSED);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.ROLLING);
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
	// CAST //////////////////////////////////////////////
	private void switchToCastingState() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_FIREBALL_LOOP);
		characterStateManager.transition(CHARACTER_STATE.CAST);
	}

	@Test
	public void testCastUpdate() {
		switchToCastingState();
		characterStateManager.update(1000);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.IDLE);
	}

	@Test
	public void testCastToHurt() {
		switchToCastingState();
		characterStateManager.handleEvent(EVENT.DAMAGE_TAKEN);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.HURT);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testCastLeftUnpressed() {
		switchToCastingState();
		characterStateManager.handleEvent(EVENT.LEFT_UNPRESSED);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testCastRightUnpressed() {
		switchToCastingState();
		characterStateManager.handleEvent(EVENT.RIGHT_UNPRESSED);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

}
