package jelte.mygame.graphical;

import jelte.mygame.Message;
import jelte.mygame.MessageListener;

public class GraphicalManagerDispatcher implements GraphicalManager {
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
		activeGraphicalManager.dispose();
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

	@Override
	public void update(float delta) {
		activeGraphicalManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		activeGraphicalManager.resize(width, height);
	}

	@Override
	public void dispose() {
		activeGraphicalManager.dispose();
	}

	@Override
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
