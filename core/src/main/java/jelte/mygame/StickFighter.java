package jelte.mygame;


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
		inputHandler = new InputHandlerImpl(this);
		logicManager = new LogicManagerImpl(this);
		graphicalManager = new GraphicalManagerImpl(this);
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

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getCode()) {
		case TEST:
			break;
		default:
			break;
		}

	}

}
