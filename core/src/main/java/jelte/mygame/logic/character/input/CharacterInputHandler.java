package jelte.mygame.logic.character.input;

import jelte.mygame.Message;
import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.spells.SpellFileReader;

public class CharacterInputHandler {

	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case DOWN_PRESSED:
			characterStateManager.handleEvent(EVENT.DOWN_PRESSED);
			break;
		case DOWN_UNPRESSED:
			characterStateManager.handleEvent(EVENT.DOWN_UNPRESSED);
			break;
		case LEFT_PRESSED:
			characterStateManager.handleEvent(EVENT.LEFT_PRESSED);
			break;
		case LEFT_UNPRESSED:
			characterStateManager.handleEvent(EVENT.LEFT_UNPRESSED);
			break;
		case RIGHT_PRESSED:
			characterStateManager.handleEvent(EVENT.RIGHT_PRESSED);
			break;
		case RIGHT_UNPRESSED:
			characterStateManager.handleEvent(EVENT.RIGHT_UNPRESSED);
			break;
		case UP_PRESSED:
			characterStateManager.handleEvent(EVENT.JUMP_PRESSED);
			break;
		case BLOCK_PRESSED:
			characterStateManager.handleEvent(EVENT.BLOCK_PRESSED);
			break;
		case BLOCK_UNPRESSED:
			characterStateManager.handleEvent(EVENT.BLOCK_UNPRESSED);
			break;
		case DASH_PRESSED:
			characterStateManager.handleEvent(EVENT.DASH_PRESSED);
			break;
		case ROLL_PRESSED:
			characterStateManager.handleEvent(EVENT.ROLL_PRESSED);
			break;
		case SPRINT_PRESSED:
			characterStateManager.handleEvent(EVENT.SPRINT_PRESSED);
			break;
		case SPRINT_UNPRESSED:
			characterStateManager.handleEvent(EVENT.SPRINT_UNPRESSED);
			break;
		case TELEPORT_PRESSED:
			characterStateManager.handleEvent(EVENT.TELEPORT_PRESSED);
			break;
		case ATTACK_PRESSED:
			characterStateManager.handleEvent(EVENT.ATTACK_PRESSED);
			break;
		case CAST_PRESSED:
			spellsPreparedToCast.addLast(SpellFileReader.getSpellData().get((int) message.getValue()));
			characterStateManager.handleEvent(EVENT.CAST_PRESSED);
			break;
		default:
			break;

		}
	}

}
