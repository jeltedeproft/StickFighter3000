package jelte.mygame.logic.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.graphical.audio.AudioCommand;
import jelte.mygame.graphical.audio.AudioEnum;
import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.character.NpcCharacter;
import jelte.mygame.logic.character.NpcCharacter.AI_STATE;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.utility.Constants;

public abstract class AbstractAiStrategy implements AiStrategy {
	private static final String TAG = AbstractAiStrategy.class.getSimpleName();
	protected NpcCharacter self;
	protected int currentPatrolPointIndex = 0;
	protected float timeSinceLastAttack = 0f;

	protected abstract void updatePatrolState(float delta, PlayerCharacter player);

	protected abstract void updateIdleState(float delta, PlayerCharacter player);

	protected abstract void updateChaseState(float delta, PlayerCharacter player);

	protected abstract void updateCastState(float delta, PlayerCharacter player);

	protected abstract void updateAttackState(float delta, PlayerCharacter player);

	protected AbstractAiStrategy(NpcCharacter self) {
		this.self = self;
	}

	@Override
	public void update(float delta, PlayerCharacter player, AI_STATE state) {
		Gdx.app.log(TAG, "UPDATING " + state);
		self.getPhysicsComponent().getCollidedWith().clear();
		timeSinceLastAttack += delta;
		switch (state) {
		case ATTACK:
			updateAttackState(delta, player);
			break;
		case CAST:
			updateCastState(delta, player);
			break;
		case CHASE:
			updateChaseState(delta, player);
			break;
		case IDLE:
			updateIdleState(delta, player);
			break;
		case PATROL:
			updatePatrolState(delta, player);
			break;
		default:
			break;

		}

	}

	protected boolean isPointReached(NpcCharacter self, Vector2 goal) {
		Gdx.app.log(TAG, "distance to goal : " + self.getPhysicsComponent().getPosition().dst(goal));
		return self.getPhysicsComponent().getPosition().dst(goal) <= Constants.CONTROL_POINT_REACHED_BUFFER_DISTANCE;
	}

	protected void shiftPatrolPoint() {
		currentPatrolPointIndex++;
		if (currentPatrolPointIndex >= self.getPatrolPositions().size) {
			currentPatrolPointIndex = 0;
		}
	}

	protected void moveTowardsGoal(NpcCharacter self, Vector2 goal) {
		Vector2 direction = goal.cpy().sub(self.getPhysicsComponent().getPosition()).nor();
		Direction currentDirection = self.getPhysicsComponent().getDirection();
		if (direction.x < 0 && currentDirection == Direction.right) {
			Gdx.app.log(TAG, "RIGHT UNPRESSED");
			Gdx.app.log(TAG, "LEFT PRESSED");
			sendMessage(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_UNPRESSED));
			sendMessage(new Message(RECIPIENT.LOGIC, ACTION.LEFT_PRESSED));
		} else if (direction.x > 0 && currentDirection == Direction.left) {
			Gdx.app.log(TAG, "LEFT UNPRESSED");
			Gdx.app.log(TAG, "RIGHT PRESSED");
			sendMessage(new Message(RECIPIENT.LOGIC, ACTION.LEFT_UNPRESSED));
			sendMessage(new Message(RECIPIENT.LOGIC, ACTION.RIGHT_PRESSED));
		}
	}

	protected void changeState(AI_STATE state) {
		switch (state) {
		case ATTACK:
			if (timeSinceLastAttack >= self.getData().getAttackCooldown()) {
				MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.forName(String.format("SOUND_ATTACK_STATE_%s", self.getName().toUpperCase())));
				self.getSpellsreadyToCast().addLast(SpellFileReader.getSpellData().get(0));
				timeSinceLastAttack = 0f;
				self.setState(AI_STATE.ATTACK);
			} else {
				self.setState(AI_STATE.PATROL);
			}
			break;
		case CAST:
			MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.forName(String.format("SOUND_CAST_STATE_%s", self.getName().toUpperCase())));
			self.setState(AI_STATE.CAST);
			break;
		case CHASE:
			MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.forName(String.format("SOUND_CHASE_STATE_%s", self.getName().toUpperCase())));
			self.setState(AI_STATE.CHASE);
			break;
		case IDLE:
			self.setState(AI_STATE.IDLE);
			break;
		case PATROL:
			self.setState(AI_STATE.PATROL);
			break;
		default:
			break;
		}
	}

	@Override
	public void sendMessage(Message message) {
		self.receiveMessage(message);
	}

}
