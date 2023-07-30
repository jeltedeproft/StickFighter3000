package jelte.mygame.graphical.hud;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.utility.AssetManagerUtility;

public class SpellButton {
	private Stack stack;
	private SpellData spellData;
	private Table hotKeyTable = new Table();
	private ImageButton spellIconButton;
	private Image spellNumber;
	private Table spellIconTable = new Table();
	private TextButton cooldownButton;
	private Table cooldownTable = new Table();
	private float currentCooldown = 0;
	private boolean isReady = true;
	private Skin skin;

	public SpellButton(Skin skin) {
		this.skin = skin;
		spellIconButton = new ImageButton(skin, "spellButton");
		spellIconButton.setVisible(true);
		spellIconButton.setSize(75, 75);
		cooldownButton = new TextButton("", skin, "counter");
		cooldownButton.setVisible(false);
		cooldownButton.setSize(75, 75);

		spellIconTable.add(spellIconButton).size(75);
		cooldownTable.add(cooldownButton).size(75);
		stack = new Stack();
		stack.add(spellIconTable);
		stack.add(cooldownTable);
		hotKeyTable.setSize(100, 100);
		spellNumber = new Image(AssetManagerUtility.getSprite("Number0"));
		hotKeyTable.add(spellNumber).size(25).padTop(75);
		stack.add(hotKeyTable);
	}

	public SpellButton(Skin skin, SpellData data) {
		this(skin);
		this.spellData = data;
		initializeImage();
	}

	public void activateSpellSlot(SpellData data) {
		this.spellData = data;
		initializeImage();
	}

	public void addTooltip(Skin skin, TooltipManager tooltipManager) {
		spellIconButton.addListener(new TextTooltip(spellData.getInfoText(), tooltipManager, skin));
	}

	private void initializeImage() {
		Sprite image = AssetManagerUtility.getSprite(spellData.getIconSpriteName());
		if (image != null) {
			final TextureRegion tr = new TextureRegion(image);
			final TextureRegionDrawable buttonImage = new TextureRegionDrawable(tr);
			buttonImage.setMinHeight(55);
			buttonImage.setMinWidth(55);
			ImageButton button = new ImageButton(skin, "spellButton");
			ImageButtonStyle standardStyle = button.getStyle();
			ImageButtonStyle buttonStyle = new ImageButtonStyle();
			buttonStyle.imageUp = buttonImage;
			buttonStyle.up = standardStyle.up;
			buttonStyle.up.setMinHeight(95);
			buttonStyle.up.setMinWidth(95);
			buttonStyle.down = standardStyle.down;
			buttonStyle.down.setMinHeight(95);
			buttonStyle.down.setMinWidth(95);
			buttonStyle.over = standardStyle.over;
			buttonStyle.over.setMinHeight(95);
			buttonStyle.over.setMinWidth(95);
			spellIconButton.setStyle(buttonStyle);
		}
	}

	private void showCooldown() {
		spellIconButton.setVisible(false);
		cooldownButton.setVisible(true);
	}

	private void showSpellIcon() {
		spellIconButton.setVisible(true);
		cooldownButton.setVisible(false);
	}

	public void startCooldown() {
		if (isReady) {
			showCooldown();
			resetCooldown();
			isReady = false;
		}
	}

	public void update(float delta) {
		if (currentCooldown > 0) {
			currentCooldown -= delta;
			int cd = (int) currentCooldown;
			String cooldown = Integer.toString(cd);
			cooldownButton.setText(cooldown);
		} else if (!isReady) {
			isReady = true;
			showSpellIcon();
			cooldownButton.setText("");
		}
	}

	public void resetCooldown() {
		currentCooldown = spellData.getCoolDown();
	}

	public boolean isSpellReady() {
		return currentCooldown <= 0;
	}

	public float getCurrentCooldown() {
		if (currentCooldown <= 0) {
			return 0f;
		}
		return currentCooldown;
	}

	public ImageButton getSpellIconButton() {
		return spellIconButton;
	}

	public TextButton getCooldownButton() {
		return cooldownButton;
	}

	public Stack getStack() {
		return stack;
	}
}
