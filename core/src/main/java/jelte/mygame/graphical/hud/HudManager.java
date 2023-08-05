package jelte.mygame.graphical.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.Map;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.graphical.HealthBar;
import jelte.mygame.graphical.animations.NamedSprite;
import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.PlayerCharacter;
import jelte.mygame.logic.spells.SpellFileReader;
import jelte.mygame.logic.spells.SpellsEnum;
import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import lombok.Getter;

@Getter
public class HudManager {
	private static final String TAG = HudManager.class.getSimpleName();

	private Viewport uiViewport;
	private Stage uiStage;
	private Skin skin;
	private ProgressBar playerHpBar;
	private ProgressBar playerMpBar;
	private ProgressBar playerStaminaBar;
	private Map<AiCharacter, HealthBar> enemyHealthBars;

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

	private Image minimapImage;
	private Array<SpellButton> spellButtons;
	private int freeSpellSlot = 0;

	private MessageListener messageListener;

	public HudManager(MessageListener messageListener, SpriteBatch batch) {
		this.messageListener = messageListener;
		enemyHealthBars = new HashMap<>();
		spellButtons = new Array<>();
		skin = AssetManagerUtility.getSkin(Constants.SKIN_FILE_PATH);

		uiViewport = new ExtendViewport(Constants.VISIBLE_UI_WIDTH, Constants.VISIBLE_UI_HEIGHT);
		uiViewport.getCamera().position.set(uiViewport.getWorldWidth() / 2, uiViewport.getWorldHeight() / 2, 0);
//		uiViewport.getCamera().viewportWidth = uiViewport.getWorldWidth();
//		uiViewport.getCamera().viewportHeight = uiViewport.getWorldHeight();
		uiViewport.getCamera().update();

		uiStage = new Stage(uiViewport, batch);

		messageListener.receiveMessage(new Message(RECIPIENT.INPUT, ACTION.SEND_STAGE, uiStage));

		createHud();
	}

	private void createHud() {
		float rectangleWidth = uiViewport.getWorldWidth() / 3;
		float rectangleHeight = uiViewport.getWorldHeight() / 3;

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

		Window statsWindow = new Window("", skin);
		statsWindow.getTitleTable().padBottom(20);

		Animation<NamedSprite> redCrystalAnimation = AssetManagerUtility.getAnimation("redCrystal", 0.5f, PlayMode.LOOP);
		AnimationActor redCrystal = new AnimationActor(redCrystalAnimation);
		redCrystal.setSize(10, 10);

		Animation<NamedSprite> greenCrystalAnimation = AssetManagerUtility.getAnimation("greenCrystal", 0.5f, PlayMode.LOOP);
		AnimationActor greenCrystal = new AnimationActor(greenCrystalAnimation);
		greenCrystal.setSize(10, 10);

		Animation<NamedSprite> blueCrystalAnimation = AssetManagerUtility.getAnimation("blueCrystal", 0.5f, PlayMode.LOOP);
		AnimationActor blueCrystal = new AnimationActor(blueCrystalAnimation);
		blueCrystal.setSize(10, 10);

		ImageButton hpicon = new ImageButton(skin, "hpicon");
		ImageButton mpicon = new ImageButton(skin, "mpicon");
		ImageButton staminaIcon = new ImageButton(skin, "staminaIcon");

		playerHpBar = new ProgressBar(0, Constants.PLAYER_MAX_HP, 1, false, skin, "hp");
		playerMpBar = new ProgressBar(0, Constants.PLAYER_MAX_HP, 1, false, skin, "mp");// TODO change to MP and stamina here
		playerStaminaBar = new ProgressBar(0, Constants.PLAYER_MAX_HP, 1, false, skin, "stamina");
		statsWindow.add(hpicon).size(20).expand().left().top().padLeft(20).padTop(10);
		statsWindow.add(playerHpBar).width(Constants.VISIBLE_UI_WIDTH * Constants.STATS_BAR_WIDTH_PERCENT_SCREEN).height(Constants.VISIBLE_UI_HEIGHT * Constants.STATS_BAR_HEIGHT_PERCENT_SCREEN)
				.expand().left().top().padTop(10); // Positions the button in the center of the table
		statsWindow.row();
		statsWindow.add(mpicon).size(20).expand().left().top().padLeft(20);
		statsWindow.add(playerMpBar).width(Constants.VISIBLE_UI_WIDTH * Constants.STATS_BAR_WIDTH_PERCENT_SCREEN).height(Constants.VISIBLE_UI_HEIGHT * Constants.STATS_BAR_HEIGHT_PERCENT_SCREEN)
				.expand().left().top(); // Positions the button in the center of the table
		statsWindow.row();
		statsWindow.add(staminaIcon).size(20).expand().left().top().padLeft(20);
		statsWindow.add(playerStaminaBar).width(Constants.VISIBLE_UI_WIDTH * Constants.STATS_BAR_WIDTH_PERCENT_SCREEN).height(Constants.VISIBLE_UI_HEIGHT * Constants.STATS_BAR_HEIGHT_PERCENT_SCREEN)
				.expand().left().top(); // Positions the button in the center of the table

		topLeftBar.add(statsWindow).width(Constants.VISIBLE_UI_WIDTH * Constants.STATS_WINDOW_WIDTH_PERCENT_SCREEN).height(Constants.VISIBLE_UI_HEIGHT * Constants.STATS_WINDOW_HEIGHT_PERCENT_SCREEN).expand().left().top().padLeft(30).padTop(20);

		minimapImage = new Image();

		Table topBar = new Table();
		Table bottomBar = new Table();
		for (int i = 1; i < Constants.MAX_SPELL_SLOTS; i++) {
			SpellButton button = new SpellButton(skin);
			bottomBar.add(button.getStack()).align(Align.bottomLeft);
			spellButtons.add(button);
		}

		bottomMiddleBar.add(topBar).width(rectangleWidth).height(rectangleHeight / 2);
		bottomMiddleBar.row();
		bottomMiddleBar.add(bottomBar).width(rectangleWidth).height(rectangleHeight / 2).align(Align.bottomLeft);

		root.pack();

		uiStage.addActor(root); // Adds the table to the stage
	}

	public void activateNextSpell(SpellsEnum spellsEnum) {
		spellButtons.get(freeSpellSlot).activateSpellSlot(SpellFileReader.getSpellData().get(spellsEnum.ordinal()));
		freeSpellSlot++;
	}

	public void setMinimap(Texture minimapTexture) {
//		minimapImage.setSize(minimapTexture.getWidth(), minimapTexture.getHeight());
//		TextureRegion textureRegion = new TextureRegion(minimapTexture);
//		textureRegion.flip(false, true);
//		minimapImage.setDrawable(new TextureRegionDrawable(textureRegion));
//		minimapImage.getDrawable().setMinWidth(minimapTexture.getWidth());
//		minimapImage.getDrawable().setMinHeight(minimapTexture.getHeight());
//		Table subTable = new Table();
//		subTable.add(minimapImage).width(minimapTexture.getWidth()).height(minimapTexture.getHeight()).expand().right().top().padRight(20);
//
//		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
//		pixmap.setColor(Color.WHITE);
//		pixmap.fill();
//		Texture texture = new Texture(pixmap);
//		pixmap.dispose();
//		TextureRegionDrawable whitePixelDrawable = new TextureRegionDrawable(texture);
//		subTable.setBackground(whitePixelDrawable);
//		topRightBar.add(subTable).width(minimapTexture.getWidth()).height(minimapTexture.getHeight()).expand().right().top().padRight(20);
	}

	public void renderMinimapDot(Vector2 relativePlayerPositionMinimap) {
		Vector2 minimapDotPosition = new Vector2(relativePlayerPositionMinimap.x * minimapImage.getWidth(), relativePlayerPositionMinimap.y * minimapImage.getHeight());

		// Draw the dot at the player's position
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(uiViewport.getCamera().combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Constants.MINIMAP_DOT_COLOR);
		shapeRenderer.circle(minimapImage.getX() + minimapDotPosition.x, minimapImage.getY() + minimapDotPosition.y, Constants.MINIMAP_DOT_SIZE);
		// shapeRenderer.circle(390, 436, Constants.MINIMAP_DOT_SIZE);// TODO why these numbers?
		shapeRenderer.end();
	}

	public void renderUI() {
		uiViewport.apply();
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
