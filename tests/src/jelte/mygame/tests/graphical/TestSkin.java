package jelte.mygame.tests.graphical;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import jelte.mygame.tests.testUtil.GdxTestRunner;

@Ignore
@RunWith(GdxTestRunner.class)
public class TestSkin {

	private Skin skin;

	@Mock
	private FreeTypeFontGenerator mockFontGenerator;

	@Before
    public void setup() throws Exception {
        // Mock the FreeTypeFontGenerator instance
        when(mockFontGenerator.generateFont(Mockito.any())).thenReturn(new BitmapFont());

        // Stub the static method using PowerMock
        Mockito.mockStatic(FreeTypeFontGenerator.class);

        skin = new Skin();
        skin.add("font", mockFontGenerator);
        skin.load(Gdx.files.internal("skin/dark.json"));
    }

	@Test
	public void testSkinLoading() {
		assertNotNull(skin);
		assertNotNull(skin.get("font", BitmapFont.class));
		assertNotNull(skin.getDrawable("default-rect"));
		assertNotNull(skin.get("default-font", BitmapFont.class));
	}

	@Test
	public void testSkinResources() {
		assertTrue(skin.has("default", TextField.TextFieldStyle.class));
		assertTrue(skin.has("default", Button.ButtonStyle.class));
		assertTrue(skin.has("default", Label.LabelStyle.class));
		// Add more assertions for other resources in your skin
	}
}
