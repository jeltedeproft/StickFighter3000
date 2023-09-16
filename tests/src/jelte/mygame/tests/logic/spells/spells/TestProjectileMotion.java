package jelte.mygame.tests.logic.spells.spells;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jelte.mygame.graphical.audio.MusicManager;
import jelte.mygame.graphical.audio.MusicManagerInterface;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.character.PlayerFileReader;
import jelte.mygame.logic.character.state.CharacterStateManager;
import jelte.mygame.logic.physics.PlayerPhysicsComponent;
import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.logic.spells.spells.ProjectileSpell;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestProjectileMotion {
	private Character caster;
	private Character target;
	private UUID casterId = UUID.randomUUID();
	private UUID targetId = UUID.randomUUID();
	private Vector2 casterPosition;
	private Vector2 targetPosition;
	private Vector2 mousePosition;
	private ProjectileSpell testProjectile;

	public enum EXCEL_HEADERS {
		START_POSITION_X,
		START_POSITION_Y,
		TARGET_POSITION_X,
		TARGET_POSITION_Y,
		INITIAL_SPEED,
		UPDATE_CYCLES,
		DELTA_PER_CYCLE,
		EXPECTED_POSITION_X,
		EXPECTED_POSITION_Y
	}

	@Mock
	private MusicManagerInterface mockMusicManager;

	@BeforeClass
	public static void setUpBeforeClass() {
		PlayerFileReader.loadUnitStatsInMemory(Constants.PLAYER_STATS_TEST_FILE_LOCATION);
		SpellFileReader.loadSpellsInMemory(Constants.SPELL_STATS_TEST_FILE_LOCATION);
		ApplicationLogger logger = new MultiFileLogger();
		Gdx.app.setApplicationLogger(logger);
	}

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		MusicManager.setInstance(mockMusicManager);

		caster = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), casterId);
		caster.setCharacterStateManager(new CharacterStateManager(caster));
		caster.setPhysicsComponent(new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(0, 0)));
		casterPosition = caster.getPhysicsComponent().getPosition();

		target = new PlayerCharacter(PlayerFileReader.getUnitData().get(0), targetId);
		target.setCharacterStateManager(new CharacterStateManager(target));
		target.setPhysicsComponent(new PlayerPhysicsComponent(UUID.randomUUID(), new Vector2(100, 100)));
		targetPosition = target.getPhysicsComponent().getPosition();

		mousePosition = new Vector2(50, 50);

		testProjectile = new ProjectileSpell(SpellFileReader.getSpellData().get(3), caster, mousePosition);
	}

	@Test
	public void testProjectilePositions() {
		try {
			FileHandle fileHandle = Gdx.files.internal(Constants.TEST_FILE_PROJECTILE_INPUT_EXCEL);
			Workbook workbook = new XSSFWorkbook(fileHandle.read());
			Sheet sheet = workbook.getSheetAt(0);

			// Start reading from the second row (assuming the header is in the first row)
			int rowIndex = 1;

			for (Row row : sheet) {
				if (rowIndex > 1) { // Skip the header
					double startPositionX = 0;
					double startPositionY = 0;
					double targetPositionX = 0;
					double targetPositionY = 0;
					double initialSpeed = 0;
					double updateCycles = 0;
					double deltaPerCycle = 0;
					double expectedPositionX = 0;
					double expectedPositionY = 0;

					for (Cell cell : row) {
						switch (EXCEL_HEADERS.values()[cell.getColumnIndex()]) {
						case START_POSITION_X:
							startPositionX = cell.getNumericCellValue();
							break;
						case START_POSITION_Y:
							startPositionY = cell.getNumericCellValue();
							break;
						case TARGET_POSITION_X:
							targetPositionX = cell.getNumericCellValue();
							break;
						case TARGET_POSITION_Y:
							targetPositionY = cell.getNumericCellValue();
							break;
						case INITIAL_SPEED:
							initialSpeed = cell.getNumericCellValue();
							break;
						case UPDATE_CYCLES:
							updateCycles = cell.getNumericCellValue();
							break;
						case DELTA_PER_CYCLE:
							deltaPerCycle = cell.getNumericCellValue();
							break;
						case EXPECTED_POSITION_X:
							expectedPositionX = cell.getNumericCellValue();
							break;
						case EXPECTED_POSITION_Y:
							expectedPositionY = cell.getNumericCellValue();
							break;
						default:
							break;

						}
					}
					caster.getPhysicsComponent().setPosition(new Vector2((float) startPositionX, (float) startPositionY));
					mousePosition.set((float) targetPositionX, (float) targetPositionY);
					SpellData data = SpellFileReader.getSpellData().get(3);
					data.setSpeed((float) initialSpeed);
					data.setDuration(100);
					ProjectileSpell spell = new ProjectileSpell(data, caster, mousePosition);
					for (int i = 0; i < updateCycles; i++) {
						spell.update((float) deltaPerCycle, caster, mousePosition);
					}
					Assert.assertEquals((float) expectedPositionX, spell.getPhysicsComponent().getPosition().x, 0.01f);
					Assert.assertEquals((float) expectedPositionY, spell.getPhysicsComponent().getPosition().y, 0.01f);

				}
				rowIndex++;
			}

			// Close the input stream and release resources
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testStartingAngles() {
		// Test cases
		double initialSpeed = 100.0; // Initial speed in m/s
		double gravity = 9.81f; // Acceleration due to gravity in m/s^2

		// Test scenarios
		double[][] testInputs = {
				// Horizontal target at different altitudes
				{ initialSpeed, gravity, 100.0, 10.0 }, // Low altitude
				{ initialSpeed, gravity, 100.0, 50.0 }, // Moderate altitude
				{ initialSpeed, gravity, 100.0, 100.0 }, // High altitude

				// Diagonal target at different altitudes
				{ initialSpeed, gravity, 100.0, 100.0 }, // Low altitude
				{ initialSpeed, gravity, 100.0, 150.0 }, // Moderate altitude
				{ initialSpeed, gravity, 100.0, 200.0 }, // High altitude

				// Targets at different horizontal distances and altitudes
				{ initialSpeed, gravity, 50.0, 10.0 }, // Short distance, low altitude
				{ initialSpeed, gravity, 200.0, 50.0 }, // Long distance, moderate altitude
				{ initialSpeed, gravity, 300.0, 200.0 } // Very long distance, high altitude
		};

		for (int i = 0; i < testInputs.length; i++) {
			double[] input = testInputs[i];
			double v = input[0];
			double g = input[1];
			double x = input[2];
			double y = input[3];

			Vector2[] launchVectors = testProjectile.calculateLaunchVectors(v, g, x, y);
			double[] launchAngles = testProjectile.calculateLaunchAngles(v, g, x, y);

			System.out.println("Scenario " + (i + 1));
			System.out.println("Initial Speed: " + v + " m/s");
			System.out.println("Gravity: " + g + " m/s^2");
			System.out.println("Horizontal Distance: " + x + " meters");
			System.out.println("Vertical Altitude: " + y + " meters");

			for (int j = 0; j < launchVectors.length; j++) {
				System.out.println("Launch Angle " + (j + 1) + ": " + Math.toDegrees(launchAngles[j]) + " degrees");
				System.out.println("Launch Vector " + (j + 1) + ": (" + launchVectors[j].x + ", " + launchVectors[j].y + ") m/s");
			}

			System.out.println("----------------------------------");
		}
	}
}
