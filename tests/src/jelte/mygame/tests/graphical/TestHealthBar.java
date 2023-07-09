package jelte.mygame.tests.graphical;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;

import jelte.mygame.graphical.HealthBar;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import jelte.mygame.utility.logging.MultiFileLogger;

@RunWith(GdxTestRunner.class)
public class TestHealthBar {

	@Mock
	private Batch batch;

	@Mock
	private BitmapFont font;

	private NinePatch healthBarNinePatch;
	private NinePatch border;

	private HealthBar healthBar;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationLogger logger = new MultiFileLogger();
		Gdx.app.setApplicationLogger(logger);
		AssetManagerUtility.loadTextureAtlas(Constants.SPRITES_ATLAS_PATH);
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		healthBarNinePatch = mock(NinePatch.class);
		border = mock(NinePatch.class);

		healthBar = new HealthBar(0, 0, 100, font);
	}

	@Test
	public void testUpdate() {
		float x = 10;
		float y = 20;
		float hp = 50;

		healthBar.update(x, y, hp);

		// Assert the updated values
		assertEquals(x, healthBar.getX(), 0.001f);
		assertEquals(y, healthBar.getY(), 0.001f);
		assertEquals(hp, healthBar.getHp(), 0.001f);
	}

}
