package jelte.mygame.tests.logic.character;

import static org.mockito.Mockito.doNothing;

import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.input.InputBox;
import jelte.mygame.input.InputHandlerImpl.BUTTONS;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.character.state.CharacterStateManager.CHARACTER_STATE;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;

@RunWith(GdxTestRunner.class)
public class TestCharacterStates {
	private PlayerCharacter testPlayer;

	@Mock
	private MusicManagerInterface mockMusicManager;

	@InjectMocks
	private CharacterStateManager characterStateManager;

	@BeforeClass
	public static void beforeAllTests() {
		PlayerFileReader.loadUnitStatsInMemory(Constants.PLAYER_STATS_TEST_FILE_LOCATION);
	}

	@Before
	public void beforeEverytest() {
		testPlayer = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), UUID.randomUUID());
		characterStateManager = new CharacterStateManager(testPlayer);
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
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.ATTACK, true);
		characterStateManager.handleInput(inputBox);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.ATTACKING);
	}

	@Test
	public void testAppearToHurt() {
		characterStateManager.handleEvent(EVENT.DAMAGE_TAKEN);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.HURT);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 10);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testAppearToJumping() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_JUMP);
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.UP, true);
		characterStateManager.handleInput(inputBox);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.JUMPING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == Constants.JUMP_SPEED.y);
	}

	@Test
	public void testAppearToWalkingLeft() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_WALK);
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.LEFT, true);
		characterStateManager.handleInput(inputBox);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.WALKING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == -Constants.WALK_SPEED);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getDirection() == Direction.left);
	}

	@Test
	public void testAppearLeftUnpressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.LEFT, false);
		characterStateManager.handleInput(inputBox);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 0);
	}

	@Test
	public void testAppearToWalkingRight() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_LOOP, AudioEnum.SOUND_WALK);
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.RIGHT, true);
		characterStateManager.handleInput(inputBox);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.WALKING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == Constants.WALK_SPEED);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getDirection() == Direction.right);
	}

	@Test
	public void testAppearRightUnpressed() {
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.RIGHT, false);
		characterStateManager.handleInput(inputBox);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 0);
	}

	@Test
	public void testAppearToTeleportRight() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.right);
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.TELEPORT, true);
		characterStateManager.handleInput(inputBox);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == Constants.TELEPORT_DISTANCE);
	}

	@Test
	public void testAppearToTeleportLeft() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.TELEPORT, true);
		characterStateManager.handleInput(inputBox);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == 0);
	}

	@Test
	public void testAppearToTeleportLeft2() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
		characterStateManager.getCharacter().getPhysicsComponent().setPosition(new Vector2(Constants.TELEPORT_DISTANCE, 0));
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.TELEPORT, true);
		characterStateManager.handleInput(inputBox);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == 0);
	}

	@Test
	public void testAppearToDash() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_DASH);
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.DASH, true);
		characterStateManager.handleInput(inputBox);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.DASHING);
	}

	@Test
	public void testAppearToRoll() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_ROLL);
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.ROLL, true);
		characterStateManager.handleInput(inputBox);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.ROLLING);
	}

	@Test
	public void testAppearToBlock() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_SHIELD);
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.BLOCK, true);
		characterStateManager.handleInput(inputBox);
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
		characterStateManager.pushState(CHARACTER_STATE.ATTACKING);
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
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 10);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testAttackLeftUnpressed() {
		switchToAttackingState();
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.LEFT, false);
		characterStateManager.handleInput(inputBox);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testAttackRightUnpressed() {
		switchToAttackingState();
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.RIGHT, false);
		characterStateManager.handleInput(inputBox);
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
		characterStateManager.pushState(CHARACTER_STATE.BLOCKING);
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
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.LEFT, false);
		characterStateManager.handleInput(inputBox);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testBlockRightUnpressed() {
		switchToBlockingState();
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.RIGHT, false);
		characterStateManager.handleInput(inputBox);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testBlockToTeleportRight() {
		switchToBlockingState();
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.right);
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.TELEPORT, true);
		characterStateManager.handleInput(inputBox);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == Constants.TELEPORT_DISTANCE);
	}

	@Test
	public void testBlockToTeleportLeft() {
		switchToBlockingState();
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.TELEPORT, true);
		characterStateManager.handleInput(inputBox);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == 0);
	}

	@Test
	public void testBlockToTeleportLeft2() {
		switchToBlockingState();
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_TELEPORT);
		characterStateManager.getCharacter().getPhysicsComponent().setDirection(Direction.left);
		characterStateManager.getCharacter().getPhysicsComponent().setPosition(new Vector2(Constants.TELEPORT_DISTANCE, 0));
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.TELEPORT, true);
		characterStateManager.handleInput(inputBox);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.TELEPORTING);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getPosition().x == 0);
	}

	@Test
	public void testBlockToDash() {
		switchToBlockingState();
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_DASH);
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.DASH, true);
		characterStateManager.handleInput(inputBox);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.DASHING);
	}

	@Test
	public void testBlockToRoll() {
		switchToBlockingState();
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_ROLL);
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.ROLL, true);
		characterStateManager.handleInput(inputBox);
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
	// PRECAST //////////////////////////////////////////////
	private void switchToPreCastingState() {
		doNothing().when(mockMusicManager).sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.SOUND_FIREBALL_LOOP);
		characterStateManager.pushState(CHARACTER_STATE.PRECAST);
	}

	@Test
	public void testPreCastUpdate() {
		switchToPreCastingState();
		characterStateManager.update(0.3f);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.CAST);
	}

	@Test
	public void testCastToHurt() {
		switchToPreCastingState();
		characterStateManager.handleEvent(EVENT.DAMAGE_TAKEN);
		Assert.assertEquals(characterStateManager.getCurrentCharacterState().getState(), CHARACTER_STATE.HURT);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().y == 10);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testCastLeftUnpressed() {
		switchToPreCastingState();
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.LEFT, false);
		characterStateManager.handleInput(inputBox);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

	@Test
	public void testCastRightUnpressed() {
		switchToPreCastingState();
		InputBox inputBox = new InputBox();
		inputBox.updateButtonPressed(BUTTONS.RIGHT, false);
		characterStateManager.handleInput(inputBox);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getAcceleration().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().x == 0);
		Assert.assertTrue(characterStateManager.getCharacter().getPhysicsComponent().getVelocity().y == 0);
	}

}
