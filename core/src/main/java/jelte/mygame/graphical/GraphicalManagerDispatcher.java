package jelte.mygame.graphical;

import jelte.mygame.Message;
import jelte.mygame.MessageListener;

public class GraphicalManagerDispatcher {
	private MessageListener messageListener;
	private GraphicalManager activeGraphicalManager;
	private GraphicalManager mainMenuGraphicalManager;
	private GraphicalManager battleGraphicalManagerImpl;

	public enum SCREENS {
		MAIN_MENU,
		BATTLE
	}

	public GraphicalManagerDispatcher(MessageListener messageListener) {
		this.messageListener = messageListener;
		battleGraphicalManagerImpl = new BattleGraphicalManagerImpl(messageListener);
		mainMenuGraphicalManager = new MainMenuGraphicalManager(messageListener);
		activeGraphicalManager = mainMenuGraphicalManager;
	}

	public void switchScreen(SCREENS screen) {
		switch (screen) {
		case BATTLE:
			activeGraphicalManager = battleGraphicalManagerImpl;
			break;
		case MAIN_MENU:
			activeGraphicalManager = mainMenuGraphicalManager;
			break;
		default:
			break;

		}
	}

	public void update(float delta) {
		activeGraphicalManager.update(delta);
	}

	public void resize(int width, int height) {
		activeGraphicalManager.resize(width, height);
	}

	public void dispose() {
		activeGraphicalManager.dispose();
	}

	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case SWITCH_SCREEN:
			switchScreen((SCREENS) message.getValue());
			break;
		default:
			activeGraphicalManager.receiveMessage(message);
			break;

		}
	}

}
