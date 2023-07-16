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

import jelte.mygame.logic.collisions.CollisionPair;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.collisions.collidable.StaticBlockBot;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.UtilityFunctions;

@RunWith(GdxTestRunner.class)
public class TestUtilityFunctions {

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
		Collidable first = new StaticBlockBot(5, 5, 5, 5);
		Collidable second = new StaticBlockBot(5, 5, 5, 5);
		array[0].add(new CollisionPair(first, second));
		array[0].add(new CollisionPair(first, second));
		array[1] = new HashSet<>();
		array[1].add(new CollisionPair(first, second));
		array[1].add(new CollisionPair(first, second));
		String filePath = "test-sets.txt";

		UtilityFunctions.writeSetsToFile(array, filePath);

		String[] actualArray = readSetsFromFile(filePath);
		String[] expectedArray = new String[6];
		expectedArray[0] = "collidable 1 : STATIC_BOT";
		expectedArray[1] = "collidable 2 : STATIC_BOT";
		expectedArray[2] = "";
		expectedArray[3] = "collidable 1 : STATIC_BOT";
		expectedArray[4] = "collidable 2 : STATIC_BOT";
		expectedArray[5] = "";

		assertArrayEquals(expectedArray, actualArray);
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

	private String[] readSetsFromFile(String filePath) throws IOException {
		String[] array = new String[6];
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				for (String part : parts) {
					array[i] = part;
				}
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
