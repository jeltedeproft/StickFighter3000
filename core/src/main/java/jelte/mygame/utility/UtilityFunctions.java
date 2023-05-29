package jelte.mygame.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class UtilityFunctions {
	private static final Random random = new Random();

	public static void writeArrayToFile(int[] array, String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (int i = 0; i < array.length; i++) {
				writer.write(String.valueOf(array[i]));
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeArrayToFile(Array<Vector2> array, String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (int i = 0; i < array.size; i++) {
				writer.write(String.valueOf(array.get(i)));
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> findImageFiles(String directoryPath) {
		List<String> imageFiles = new ArrayList<>();
		File directory = new File(directoryPath);
		findImageFilesRecursive(directory, imageFiles);
		return imageFiles;
	}

	private static void findImageFilesRecursive(File directory, List<String> imageFiles) {
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					findImageFilesRecursive(file, imageFiles); // Recursively search subdirectories
				} else if (isImageFile(file)) {
					imageFiles.add(file.getAbsolutePath());
				}
			}
		}
	}

	private static boolean isImageFile(File file) {
		String fileName = file.getName();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		return extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg") || extension.equals("gif");
		// Add more image file extensions as needed
	}

	// to not exclusive
	public static int randomNumberFromTo(int from, int to) {
		return random.nextInt(from, to);
	}
}
