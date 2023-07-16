package jelte.mygame.graphical;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;

import jelte.mygame.utility.AssetManagerUtility;
import jelte.mygame.utility.Constants;
import lombok.Getter;

@Getter
public class HealthBar {
	private NinePatch healthBar;
	private NinePatch border;
	private BitmapFont font;
	private float x;
	private float y;
	private float maxHp;
	private float hp;
	private boolean visible = true;

	public HealthBar(float x, float y, float maxHp, BitmapFont font) {
		healthBar = new NinePatch(AssetManagerUtility.getAtlasRegion(Constants.RED_HEALTHBAR_SPRITE_NAME), 0, 0, 0, 0);
		border = new NinePatch(AssetManagerUtility.getAtlasRegion(Constants.HEALTHBAR_BORDER_SPRITE_NAME), 0, 0, 0, 0);
		this.x = x;
		this.y = y;
		this.maxHp = maxHp;
		this.font = font;
	}

	public void update(float x, float y, float hp) {
		this.x = x;
		this.y = y;
		this.hp = hp;
	}

	public void draw(Batch batch) {
		if (visible) {
			float width = calculateHealthBarWidth();
			drawBorder(batch, width);
			drawHealthBar(batch, width);
			drawHealthText(batch);
		}
	}

	private float calculateHealthBarWidth() {
		return Constants.MAX_WIDTH_HP_BAR * (hp / maxHp);
	}

	private void drawBorder(Batch batch, float width) {
		border.draw(batch, x - (Constants.MAX_WIDTH_HP_BAR / 2 + Constants.BORDER_WIDTH_HP_BAR),
				y - Constants.BORDER_WIDTH_HP_BAR + Constants.OFFSET_Y_HP_BAR, width + 5,
				Constants.MAX_HEIGHT_HP_BAR + 5);
	}

	private void drawHealthBar(Batch batch, float width) {
		healthBar.draw(batch, x - Constants.MAX_WIDTH_HP_BAR / 2, y + Constants.OFFSET_Y_HP_BAR, width,
				Constants.MAX_HEIGHT_HP_BAR);
	}

	private void drawHealthText(Batch batch) {
		font.draw(batch, Float.toString(hp), x - Constants.MAX_WIDTH_HP_BAR / 4,
				y + Constants.OFFSET_Y_HP_BAR * 1.3f);
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
