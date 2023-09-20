package jelte.mygame.graphical.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import lombok.Getter;

@Getter
public class MainMenuHudManager {
	private static final String TAG = MainMenuHudManager.class.getSimpleName();

	private Viewport uiViewport;
	private Stage uiStage;
	private Skin skin;
	private BitmapFont font;

	private Table root = new Table();
	private Table topLeftBar = new Table();
	private Table topMiddleBar = new Table();
	private Table topRightBar = new Table();
	private Table middleLeftBar = new Table();
	private Table middleMiddleBar = new Table();
	private Table middleRightBar = new Table();
	private Table bottomLeftBar = new Table();
	private Table bottomMiddleBar = new Table();
	private Table bottomRightBar = new Table();

	private Label title;
	private ImageButton newGameButton;
	private ImageButton resumeButton;

	private MessageListener messageListener;

	public MainMenuHudManager(MessageListener messageListener, SpriteBatch batch, BitmapFont font) {
		this.messageListener = messageListener;
		skin = AssetManagerUtility.getSkin(Constants.SKIN_FILE_PATH);
		this.font = font;

		uiViewport = new ScreenViewport();
		uiViewport.getCamera().position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		uiViewport.getCamera().update();

		uiStage = new Stage(uiViewport, batch);

		messageListener.receiveMessage(new Message(RECIPIENT.INPUT, ACTION.SEND_STAGE, uiStage));

		createHud();
	}

	private void createHud() {
		float rectangleWidth = Gdx.graphics.getWidth() / 3;
		float rectangleHeight = Gdx.graphics.getHeight() / 3;

		root.setFillParent(true); // Makes the table fill the entire screen
		root.setPosition(0, 0);

		root.add(topLeftBar).width(rectangleWidth).height(rectangleHeight);
		root.add(topMiddleBar).width(rectangleWidth).height(rectangleHeight);
		root.add(topRightBar).width(rectangleWidth).height(rectangleHeight);
		root.row();
		root.add(middleLeftBar).width(rectangleWidth).height(rectangleHeight);
		root.add(middleMiddleBar).width(rectangleWidth).height(rectangleHeight);
		root.add(middleRightBar).width(rectangleWidth).height(rectangleHeight);
		root.row();
		root.add(bottomLeftBar).width(rectangleWidth).height(rectangleHeight);
		root.add(bottomMiddleBar).width(rectangleWidth).height(rectangleHeight);
		root.add(bottomRightBar).width(rectangleWidth).height(rectangleHeight);

		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = font;
		title = new Label("Sorrowscape", labelStyle);

		topMiddleBar.add(title);

		newGameButton = new ImageButton(skin, "newgame");
		resumeButton = new ImageButton(skin, "continue");
		bottomMiddleBar.add(newGameButton);
		bottomMiddleBar.row();
		bottomMiddleBar.add(resumeButton);

		root.pack();

		uiStage.addActor(root); // Adds the table to the stage
	}

	public void renderUI() {
		uiViewport.apply();
		uiStage.act();
		uiStage.draw();
	}

	public void resize(int width, int height) {
		uiViewport.update(width, height);
		uiViewport.getCamera().position.set(uiViewport.getWorldWidth() / 2, uiViewport.getWorldHeight() / 2, 0);
	}

}
