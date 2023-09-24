package jelte.mygame.utility.logging;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationLogger;

import de.pottgames.tuningfork.logger.TuningForkLogger;

public class MultiFileLogger implements ApplicationLogger, TuningForkLogger {
	ApplicationLogger aiLogger;
	ApplicationLogger physicsLogger;
	ApplicationLogger guiLogger;
	ApplicationLogger assetsLogger;
	ApplicationLogger logicLogger;
	ApplicationLogger restLogger;

	public MultiFileLogger() {
		String desktopPath = System.getProperty("user.home") + "/desktop";
		aiLogger = new FileLogger(desktopPath + "/ai.log", Application.LOG_ERROR);
		physicsLogger = new FileLogger(desktopPath + "/physics.log", Application.LOG_INFO);
		guiLogger = new FileLogger(desktopPath + "/gui.log", Application.LOG_DEBUG);
		assetsLogger = new FileLogger(desktopPath + "/assets.log", Application.LOG_DEBUG);
		logicLogger = new FileLogger(desktopPath + "/logic.log", Application.LOG_DEBUG);
		restLogger = new FileLogger(desktopPath + "/rest.log", Application.LOG_NONE);
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
		restLogger.log(tag, message);
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
		assetsLogger.log(tag, message);
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

	@Override
	public void error(Class<?> clazz, String message) {
		assetsLogger.log("TUNINGFORK", message);

	}

	@Override
	public void warn(Class<?> clazz, String message) {
		assetsLogger.log("TUNINGFORK", message);

	}

	@Override
	public void info(Class<?> clazz, String message) {
		assetsLogger.log("TUNINGFORK", message);

	}

	@Override
	public void debug(Class<?> clazz, String message) {
		assetsLogger.log("TUNINGFORK", message);

	}

	@Override
	public void trace(Class<?> clazz, String message) {
		assetsLogger.log("TUNINGFORK", message);

	}
}
