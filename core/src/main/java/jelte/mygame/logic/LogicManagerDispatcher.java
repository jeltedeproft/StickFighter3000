package jelte.mygame.logic;

import jelte.mygame.Message;
import jelte.mygame.MessageListener;

public class LogicManagerDispatcher implements LogicManager {
	private MessageListener messageListener;
	private LogicManager activeLogicManager;
	private LogicManager mainMenuLogicManager;
	private LogicManager battleLogicManagerImpl;

	public enum SCREENS {
		MAIN_MENU,
		BATTLE
	}

	public LogicManagerDispatcher(MessageListener messageListener) {
		this.messageListener = messageListener;
		battleLogicManagerImpl = new BattleLogicManagerImpl(messageListener);
		mainMenuLogicManager = new MainMenuLogicManagerImpl(messageListener);
		activeLogicManager = mainMenuLogicManager;
	}

	public void switchScreen(SCREENS screen) {
		activeLogicManager.dispose();
		switch (screen) {
		case BATTLE:
			activeLogicManager = battleLogicManagerImpl;
			break;
		case MAIN_MENU:
			activeLogicManager = mainMenuLogicManager;
			break;
		default:
			break;

		}
	}

	@Override
	public void update(float delta) {
		activeLogicManager.update(delta);
	}

	@Override
	public void dispose() {
		activeLogicManager.dispose();
	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case SWITCH_SCREEN:
			switchScreen((SCREENS) message.getValue());
			break;
		default:
			activeLogicManager.receiveMessage(message);
			break;

		}
	}

}
