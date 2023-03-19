package jelte.mygame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

import jelte.mygame.graphical.GraphicalManager;
import jelte.mygame.graphical.GraphicalManagerImpl;
import jelte.mygame.input.InputHandler;
import jelte.mygame.input.InputHandlerImpl;
import jelte.mygame.logic.LogicManager;
import jelte.mygame.logic.LogicManagerImpl;

public class StickFighter implements ApplicationListener, MessageListener {
	private InputHandler inputHandler;
	private LogicManager logicManager;
	private GraphicalManager graphicalManager;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		inputHandler = new InputHandlerImpl(this);
		logicManager = new LogicManagerImpl(this);
		graphicalManager = new GraphicalManagerImpl();
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		inputHandler.update(delta);
		logicManager.update(delta);
		graphicalManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		graphicalManager.resize(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		graphicalManager.dispose();
		logicManager.dispose();
		inputHandler.dispose();
	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getRecipient()) {
		case LOGIC:
			logicManager.receiveMessage(message);
			break;
		case GRAPHIC:
			graphicalManager.receiveMessage(message);
			break;
		case INPUT:
			inputHandler.receiveMessage(message);
			break;
		default:
			break;

		}

	}

}
