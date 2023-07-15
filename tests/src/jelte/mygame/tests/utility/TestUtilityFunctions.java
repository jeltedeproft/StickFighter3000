package jelte.mygame.tests.utility;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;

import javafx.util.Pair;
import jelte.mygame.logic.collisions.CollisionPair;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.UtilityFunctions;

@RunWith(GdxTestRunner.class)
public class UtilityFunctionsTest {

	@Test
	public void testWriteAndReadArrayToFile() throws IOException {
		int[] array = { 1, 2, 3, 4, 5 };
		String filePath = "test-array.txt";

		UtilityFunctions.writeArrayToFile(array, filePath);

		int[] readArray = readIntArrayFromFile(filePath);
		assertArrayEquals(array, readArray);
	}

	@Test
	public void testWriteAndReadSetsToFile() throws IOException {
		Set<CollisionPair>[] array = new HashSet[2];
		array[0] = new HashSet<>();
		array[0].add(new CollisionPair(1, 2));
		array[0].add(new CollisionPair(3, 4));
		array[1] = new HashSet<>();
		array[1].add(new CollisionPair(5, 6));
		array[1].add(new CollisionPair(7, 8));
		String filePath = "test-sets.txt";

		UtilityFunctions.writeSetsToFile(array, filePath);

		Set<CollisionPair>[] readArray = readSetsFromFile(filePath);
		assertSetsArrayEquals(array, readArray);
	}

	@Test
	public void testFindImageFiles() {
		// Create a temporary directory and some dummy image files for testing
		File tempDir = createTemporaryDirectory();
		createTemporaryImageFile(tempDir, "image1.png");
		createTemporaryImageFile(tempDir, "image2.jpg");
		createTemporaryImageFile(tempDir, "image3.jpeg");
		createTemporaryImageFile(tempDir, "file.txt"); // Not an image file

		List<String> imageFiles = UtilityFunctions.findImageFiles(tempDir.getAbsolutePath());

		assertEquals(3, imageFiles.size());
		assertTrue(imageFiles.contains(tempDir.getAbsolutePath() + File.separator + "image1.png"));
		assertTrue(imageFiles.contains(tempDir.getAbsolutePath() + File.separator + "image2.jpg"));
		assertTrue(imageFiles.contains(tempDir.getAbsolutePath() + File.separator + "image3.jpeg"));
	}

	@Test
	public void testRandomNumberFromTo() {
		int from = 1;
		int to = 10;

		for (int i = 0; i < 100; i++) {
			int random = UtilityFunctions.randomNumberFromTo(from, to);
			assertTrue(random >= from && random <= to);
		}
	}

	// Helper methods

	private int[] readIntArrayFromFile(String filePath) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			return reader.lines()
					.mapToInt(Integer::parseInt)
					.toArray();
		}
	}

	private Set<CollisionPair>[] readSetsFromFile(String filePath) throws IOException {
		Set<CollisionPair>[] array = new HashSet[2];
		for (int i = 0; i < array.length; i++) {
			array[i] = new HashSet<>();
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				int first = Integer.parseInt(parts[0]);
				int second = Integer.parseInt(parts[1]);
				array[i].add(new Pair<Integer, Integer>(first, second));
				i++;
			}
		}

		return array;
	}

	private void assertSetsArrayEquals(Set<CollisionPair>[] expected, Set<CollisionPair>[] actual) {
		assertEquals(expected.length, actual.length);
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
	}

	private File createTemporaryDirectory() {
		File tempDir = new File("temp");
		tempDir.mkdir();
		tempDir.deleteOnExit();
		return tempDir;
	}

	private void createTemporaryImageFile(File directory, String fileName) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(directory, fileName)))) {
			writer.write("Dummy image content");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
