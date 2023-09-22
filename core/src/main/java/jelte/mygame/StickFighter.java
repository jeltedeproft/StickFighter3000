package jelte.mygame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;

import jelte.mygame.graphical.GraphicalManagerDispatcher;
import jelte.mygame.graphical.audio.AudioFileReader;
import jelte.mygame.graphical.specialEffects.SpecialEffectsFileReader;
import jelte.mygame.input.InputHandler;
import jelte.mygame.input.InputHandlerImpl;
import jelte.mygame.logic.LogicManager;
import jelte.mygame.logic.BattleLogicManagerImpl;
import jelte.mygame.logic.character.EnemyFileReader;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.logic.spells.modifier.ModifierFileReader;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

public class StickFighter implements ApplicationListener, MessageListener {
	private InputHandler inputHandler;
	private LogicManager logicManager;
	private GraphicalManagerDispatcher graphicalDispatcher;

	@Override
	public void create() {
		ApplicationLogger logger = new MultiFileLogger();
		Gdx.app.setApplicationLogger(logger);
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		PlayerFileReader.loadUnitStatsInMemory(Constants.PLAYER_STATS_FILE_LOCATION);
		EnemyFileReader.loadUnitStatsInMemory(Constants.ENEMY_STATS_FILE_LOCATION);
		AudioFileReader.loadAudioInMemory(Constants.AUDIO_FILE_LOCATION);
		SpellFileReader.loadSpellsInMemory(Constants.SPELL_STATS_FILE_LOCATION);
		ModifierFileReader.loadModifiersInMemory(Constants.MODIFIER_STATS_FILE_LOCATION);
		SpecialEffectsFileReader.loadSpecialEffectsInMemory(Constants.SPECIAL_EFFECTS_STATS_FILE_LOCATION);
		inputHandler = new InputHandlerImpl(this);
		logicManager = new BattleLogicManagerImpl(this);
		graphicalDispatcher = new GraphicalManagerDispatcher(this);
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		inputHandler.update(delta);
		logicManager.update(delta);
		graphicalDispatcher.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		graphicalDispatcher.resize(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		graphicalDispatcher.dispose();
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
			graphicalDispatcher.receiveMessage(message);
			break;
		case INPUT:
			inputHandler.receiveMessage(message);
			break;
		default:
			break;

		}

	}

}
