package jelte.mygame.tests.graphical.animations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.graphical.animations.SpellAnimation;
import jelte.mygame.logic.spells.state.SpellStateManager.SPELL_STATE;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestSpellAnimation {

	@Test
	public void testSpellAnimation() {
		String spellName = "Fireball";
		SPELL_STATE state = SPELL_STATE.WINDUP;

		SpellAnimation spellAnimation = new SpellAnimation(spellName, state);

		assertEquals(spellName + "-" + state.toString(), spellAnimation.getFullName());
		assertEquals(0f, spellAnimation.getTimeRunning(), 0.001f);
	}

	@Test
	public void testChangeSpell() {
		String spellName = "Fireball";
		String newSpellName = "Ice Shard";
		SPELL_STATE state = SPELL_STATE.WINDUP;

		SpellAnimation spellAnimation = new SpellAnimation(spellName, state);
		spellAnimation.changeSpell(newSpellName);

		assertEquals(newSpellName + "-" + state.toString(), spellAnimation.getFullName());
		assertEquals(0f, spellAnimation.getTimeRunning(), 0.001f);
	}

	@Test
	public void testChangeState() {
		String spellName = "Fireball";
		SPELL_STATE initialState = SPELL_STATE.WINDUP;
		SPELL_STATE newState = SPELL_STATE.LOOP;

		SpellAnimation spellAnimation = new SpellAnimation(spellName, initialState);
		spellAnimation.changeState(newState);

		assertEquals(spellName + "-" + newState.toString(), spellAnimation.getFullName());
		assertEquals(0f, spellAnimation.getTimeRunning(), 0.001f);
	}

	@Test
	public void testUpdate() {
		String spellName = "Fireball";
		SPELL_STATE state = SPELL_STATE.WINDUP;

		SpellAnimation spellAnimation = new SpellAnimation(spellName, state);
		spellAnimation.update(0.5f);

		assertEquals(0.5f, spellAnimation.getTimeRunning(), 0.001f);
	}

	@Test
	public void testUpdateData() {
		String spellName = "Fireball";
		String newSpellName = "Ice Shard";
		SPELL_STATE initialState = SPELL_STATE.WINDUP;
		SPELL_STATE newState = SPELL_STATE.LOOP;

		SpellAnimation spellAnimation = new SpellAnimation(spellName, initialState);
		boolean changed = spellAnimation.updateData(newSpellName, newState);

		assertTrue(changed);
		assertEquals(newSpellName + "-" + newState.toString(), spellAnimation.getFullName());
		assertEquals(0f, spellAnimation.getTimeRunning(), 0.001f);
	}
}
