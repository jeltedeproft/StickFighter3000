package jelte.mygame.graphical.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.HashMap;
import java.util.Map;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.graphical.HealthBar;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import lombok.Getter;

@Getter
public class HudManager {
	private static final String TAG = HudManager.class.getSimpleName();

	private ScreenViewport uiViewport;
	private Stage uiStage;
	private Skin skin;
	private ProgressBar playerHpBar;
	private ProgressBar playerMpBar;
	private ProgressBar playerStaminaBar;
	private Map<AiCharacter, HealthBar> enemyHealthBars;

	private Table root = new Table();
	private Table topBar = new Table();
	private Table middleBar = new Table();
	private Table bottomBar = new Table();

	public HudManager(MessageListener messageListener, SpriteBatch batch) {
		enemyHealthBars = new HashMap<>();
		skin = AssetManagerUtility.getSkin(Constants.SKIN_FILE_PATH);

		uiViewport = new ScreenViewport();
		uiViewport.getCamera().position.set(uiViewport.getWorldWidth() / 2, uiViewport.getWorldHeight() / 2, 0);
		uiViewport.getCamera().viewportWidth = uiViewport.getWorldWidth();
		uiViewport.getCamera().viewportHeight = uiViewport.getWorldHeight();
		uiViewport.getCamera().update();

		uiStage = new Stage(uiViewport, batch);

		messageListener.receiveMessage(new Message(RECIPIENT.INPUT, ACTION.SEND_STAGE, uiStage));

		createHud();
	}

	private void createHud() {
		Table table = new Table();
		table.setFillParent(true); // Makes the table fill the entire screen
		table.setPosition(0, 0);

		Window statsWindow = new Window("Stats", skin);

		playerHpBar = new ProgressBar(0, Constants.PLAYER_MAX_HP, 1, false, skin, "hp");
		playerMpBar = new ProgressBar(0, Constants.PLAYER_MAX_HP, 1, false, skin, "mp");// TODO change to MP and stamina here
		playerStaminaBar = new ProgressBar(0, Constants.PLAYER_MAX_HP, 1, false, skin, "stamina");
		statsWindow.add(playerHpBar).expand().left().top(); // Positions the button in the center of the table
		statsWindow.row();
		statsWindow.add(playerMpBar).expand().left().top(); // Positions the button in the center of the table
		statsWindow.row();
		statsWindow.add(playerStaminaBar).expand().left().top(); // Positions the button in the center of the table

		table.add(statsWindow).width(Gdx.app.getGraphics().getWidth() * Constants.STATS_WINDOW_WIDTH_PERCENT_SCREEN).height(Gdx.app.getGraphics().getHeight() * Constants.STATS_WINDOW_HEIGHT_PERCENT_SCREEN).expand().left().top();

		uiStage.addActor(table); // Adds the table to the stage

	}

	public void renderUI() {
		uiStage.getViewport().apply();
		uiStage.act();
		uiStage.draw();
	}

	public void renderhealthBar(AiCharacter enemy, SpriteBatch batch, BitmapFont font) {
		enemyHealthBars.putIfAbsent(enemy, new HealthBar(enemy.getPhysicsComponent().getRectangle().x, enemy.getPhysicsComponent().getRectangle().y, enemy.getData().getMaxHP(), font));
		enemyHealthBars.get(enemy).update(enemy.getPhysicsComponent().getRectangle().x, enemy.getPhysicsComponent().getRectangle().y, enemy.getCurrentHp());
		enemyHealthBars.get(enemy).draw(batch);
	}

	public void updatePlayerStats(PlayerCharacter player) {
		playerHpBar.setValue(player.getCurrentHp());
		playerMpBar.setValue(player.getCurrentHp());// TODO replace with mp and stamina
		playerStaminaBar.setValue(player.getCurrentHp());
	}

	public void resize(int width, int height) {
		uiViewport.update(width, height);
		uiViewport.getCamera().position.set(uiViewport.getWorldWidth() / 2, uiViewport.getWorldHeight() / 2, 0);
	}

}
