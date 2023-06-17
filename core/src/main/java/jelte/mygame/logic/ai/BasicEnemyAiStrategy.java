package jelte.mygame.logic.ai;

import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.Message;
import jelte.mygame.logic.ai.AiStateManager.AI_STATE;
import jelte.mygame.logic.character.NpcCharacter;
import jelte.mygame.logic.character.PlayerCharacter;

public class BasicEnemyAiStrategy implements AiStrategy {
	private NpcCharacter self;
	private float timeSinceLastCommand = 0f;

	public BasicEnemyAiStrategy(NpcCharacter self) {
		this.self = self;
	}

	@Override
	public void update(float delta, PlayerCharacter player, AiState state) {
		switch (state.getState()) {
		case ATTACK:
			break;
		case CAST:
			break;
		case CHASE:
			break;
		case IDLE:
			if (self.checkPlayerVisibility(player)) {
				self.getAiStateManager().transition(AI_STATE.CHASE);
			}
			break;
		case PATROL:
			break;
		default:
			break;

		}
	}

	@Override
	public void sendMessage(Message message) {
		self.receiveMessage(message);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("basic enemy ai strategy");
		return sb.toString();
	}

}
