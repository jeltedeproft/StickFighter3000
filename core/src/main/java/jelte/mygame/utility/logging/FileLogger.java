package jelte.mygame.utility.logging;

import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.ApplicationLogger;

public class FileLogger implements ApplicationLogger {

	private FileWriter writer;
	private int logLevel;

	public FileLogger(String fileName, int logLevel) {
		this.logLevel = logLevel;
		try {
			writer = new FileWriter(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void log(String tag, String message) {
		try {
			writer.write(tag + ": " + message + System.lineSeparator());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void log(String tag, String message, Throwable exception) {
		try {
			writer.write(tag + ": " + message + System.lineSeparator() + exception + System.lineSeparator());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void error(String tag, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(String tag, String message, Throwable exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(String tag, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(String tag, String message, Throwable exception) {
		// TODO Auto-generated method stub

	}

	public int getLogLevel() {
		return logLevel;
	}

	public void dispose() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
