package jelte.mygame.tests.logic.spells.factories;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.logic.spells.SpellData;
import jelte.mygame.logic.spells.factories.AoeSpellFactory;
import jelte.mygame.logic.spells.factories.BuffSpellFactory;
import jelte.mygame.logic.spells.factories.DefaultSpellFactory;
import jelte.mygame.logic.spells.factories.ProjectileSpellFactory;
import jelte.mygame.logic.spells.factories.SpellFactory;
import jelte.mygame.logic.spells.factories.SpellFactoryRegistry;
import jelte.mygame.logic.spells.factories.SummonSpellFactory;
import jelte.mygame.logic.spells.spells.Spell.SPELL_TYPE;
import jelte.mygame.tests.testUtil.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TestSpellFactoryRegistry {

	private SpellFactoryRegistry registry;

	@Before
	public void setUp() {
		registry = new SpellFactoryRegistry();
	}

	@Test
	public void testGetFactory_AoeSpellData() {
		SpellData spellData = mock(SpellData.class);
		when(spellData.getType()).thenReturn(SPELL_TYPE.AOE.name());

		SpellFactory factory = registry.getFactory(spellData);

		assertTrue(factory instanceof AoeSpellFactory);
	}

	@Test
	public void testGetFactory_BuffSpellData() {
		SpellData spellData = mock(SpellData.class);
		when(spellData.getType()).thenReturn(SPELL_TYPE.BUFF.name());

		SpellFactory factory = registry.getFactory(spellData);

		assertTrue(factory instanceof BuffSpellFactory);
	}

	@Test
	public void testGetFactory_ProjectileSpellData() {
		SpellData spellData = mock(SpellData.class);
		when(spellData.getType()).thenReturn(SPELL_TYPE.PROJECTILE.name());

		SpellFactory factory = registry.getFactory(spellData);

		assertTrue(factory instanceof ProjectileSpellFactory);
	}

	@Test
	public void testGetFactory_SummonSpellData() {
		SpellData spellData = mock(SpellData.class);
		when(spellData.getType()).thenReturn(SPELL_TYPE.SUMMON.name());

		SpellFactory factory = registry.getFactory(spellData);

		assertTrue(factory instanceof SummonSpellFactory);
	}

	@Test
	public void testGetFactory_DefaultSpellData() {
		SpellData spellData = mock(SpellData.class);
		when(spellData.getType()).thenReturn("unknown-type");

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			SpellFactory factory = registry.getFactory(spellData);
			assertTrue(factory instanceof DefaultSpellFactory);
		});

		String expectedMessage = "No enum constant";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}
}
