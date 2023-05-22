package jelte.mygame.utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class UtilityFunctions {
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
}
