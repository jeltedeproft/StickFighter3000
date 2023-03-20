package jelte.mygame.logic;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.jelte.myGames.shared.entities.ActionManager.Action;
import com.jelte.myGames.shared.spells.Modifier;
import com.jelte.myGames.shared.spells.SpellData;
import com.jelte.myGames.shared.spells.SpellFileReader;
import com.jelte.myGames.shared.spells.SpellsEnum;
import com.jelte.myGames.shared.utility.PonkoPolygon;
import com.jelte.myGames.shared.utility.Position;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Character {
	@EqualsAndHashCode.Exclude
	private float currentHp;
	@EqualsAndHashCode.Exclude
	private Body box2DBody;
	@EqualsAndHashCode.Exclude
	private CharacterData data;
	private UUID id;
	@EqualsAndHashCode.Exclude
	private boolean dead;
	@EqualsAndHashCode.Exclude
	private CharacterStateManager characterStateManager;
	@EqualsAndHashCode.Exclude
	private Direction currentDirection;
//	@EqualsAndHashCode.Exclude
//	private Array<SpellsEnum> spells;
//	@EqualsAndHashCode.Exclude
//	private Array<SpellSlot> spellSlots;

	public Character(CharacterData data, UUID id, Integer[] spellIds, Body characterBox2DBody) {
		this.id = id;
		this.data = data;
		characterStateManager = new CharacterStateManager(this);
		currentDirection = Direction.right;
		currentHp = data.getMaxHP();
		wizardArea = new PonkoPolygon(new float[] { 0, 0, getBoundingBoxWidth(), 0, getBoundingBoxWidth(), getBoundingBoxHeight(), 0, getBoundingBoxHeight() });
		initSpells(spellIds);
	}

	public Character(CharacterData data, UUID randomUUID, ArrayList<Integer> spells) {
		this(data, randomUUID, spells.toArray(new Integer[0]));
	}

	private void initSpells(Integer[] spellIds) {
		spells = new Array<>();
		spellSlots = new Array<>();
		for (int spellId : spellIds) {
			SpellsEnum spell = SpellsEnum.getTypes().get(spellId);
			spells.add(spell);
			SpellData data = SpellFileReader.getSpellData().get(spellId);
			spellSlots.add(new SpellSlot(spell, data.getCoolDown()));
		}
	}

	public void update(float delta) {
		for (SpellSlot slot : spellSlots) {
			slot.update(delta);
		}
		actionManager.update(delta);
	}

	public void resetCooldown(int slot) {
		spellSlots.get(slot).resetCooldown();
	}

	public boolean isSpellReady(int slot) {
		return spellSlots.get(slot).isSpellReady();
	}

	public float getCooldown(int slot) {
		return spellSlots.get(slot).getCurrentCooldown();
	}

	public boolean damage(float amount) {
		if (currentHp <= amount) {
			currentHp = 0;
			dead = true;
			actionManager.setCurrentActionForcefully(Action.die);
			return true;
		} else {
			currentHp -= amount;
			return false;

		}
	}

	public void heal(float amount) {
		if (data.getMaxHP() <= (currentHp + amount)) {
			currentHp = data.getMaxHP();
		} else {
			currentHp += amount;
		}
	}

	public int getBoundingBoxWidth() {
		return data.getBoundingBoxWidth();
	}

	public int getBoundingBoxHeight() {
		return data.getBoundingBoxHeight();
	}

	public String getSpriteName() {
		return data.getEntitySpriteName();
	}

	public String getName() {
		return data.getName();
	}

	public void setCurrentPositionX(float x) {
		if (!dead) {
			currentPosition.setX(x);
			wizardArea.setPosition(x, wizardArea.getY());
		}
	}

	public void setCurrentPositionY(float y) {
		if (!dead) {
			currentPosition.setY(y);
			wizardArea.setPosition(wizardArea.getX(), y);
		}
	}

	public void setCurrentPosition(Position pos) {
		if (!dead) {
			currentPosition = pos;
			wizardArea.setPosition(pos.getX(), pos.getY());
		}
	}

	public void startCooldown(int spellSlot) {
		spellSlots.get(spellSlot).startCooldown();
	}

	public void setCurrentAction(Action action) {
		actionManager.setCurrentAction(action);
	}

	public void setCurrentActionForcefully(Action action) {
		actionManager.setCurrentActionForcefully(action);
	}

	public Action getCurrentAction() {
		return actionManager.getCurrentAction();
	}

	public void addModifier(Modifier modifier) {
		modifiers.add(modifier);
	}

	public void setcurrentmovementspeed(float speed) {
		currentMovementSpeed = speed;
	}

}
