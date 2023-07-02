package jelte.mygame.utility.logging;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationLogger;

public class MultiFileLogger implements ApplicationLogger {

	ApplicationLogger aiLogger;
	ApplicationLogger physicsLogger;
	ApplicationLogger guiLogger;
	ApplicationLogger assetsLogger;
	ApplicationLogger logicLogger;
	ApplicationLogger restLogger;

	public MultiFileLogger() {
		aiLogger = new FileLogger("ai.log", Application.LOG_ERROR);
		physicsLogger = new FileLogger("physics.log", Application.LOG_INFO);
		guiLogger = new FileLogger("gui.log", Application.LOG_DEBUG);
		assetsLogger = new FileLogger("assets.log", Application.LOG_DEBUG);
		logicLogger = new FileLogger("logic.log", Application.LOG_DEBUG);
		restLogger = new FileLogger("rest.log", Application.LOG_NONE);
	}

	/**
	 * AI LOGGER
	 *
	 */
	@Override
	public void log(String tag, String message) {
		aiLogger.log(tag, message);
	}

	/**
	 * REST LOGGER
	 *
	 */
	@Override
	public void log(String tag, String message, Throwable exception) {
		restLogger.log(tag, message, exception);
	}

	/**
	 * PHYSICS LOGGER
	 *
	 */
	@Override
	public void error(String tag, String message) {
		physicsLogger.log(tag, message);
	}

	/**
	 * ASSETS LOGGER
	 *
	 */
	@Override
	public void error(String tag, String message, Throwable exception) {
		assetsLogger.log(tag, message, exception);
	}

	/**
	 * GUI LOGGER
	 *
	 */
	@Override
	public void debug(String tag, String message) {
		guiLogger.log(tag, message);

	}

	/**
	 * LOGIC LOGGER
	 *
	 */
	@Override
	public void debug(String tag, String message, Throwable exception) {
		logicLogger.log(tag, message, exception);

	}
}
