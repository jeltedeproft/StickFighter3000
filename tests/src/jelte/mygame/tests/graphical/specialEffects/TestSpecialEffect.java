package jelte.mygame.tests.graphical.specialEffects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import jelte.mygame.graphical.animations.NamedSprite;
import jelte.mygame.graphical.specialEffects.SpecialEffect;
import jelte.mygame.graphical.specialEffects.SpecialEffectsFileReader;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.physics.CharacterPhysicsComponentImpl;
import jelte.mygame.tests.testUtil.GdxTestRunner;
import jelte.mygame.utility.Constants;

@RunWith(GdxTestRunner.class)
public class TestSpecialEffect {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SpecialEffectsFileReader.loadSpecialEffectsInMemory(Constants.SPECIAL_EFFECTS_STATS_TEST_FILE_LOCATION);
	}

	@Test
	public void testSpecialEffect() {
		Animation<NamedSprite> animation = mock(Animation.class);
		NamedSprite keyFrameSprite = mock(NamedSprite.class);
		when(animation.getKeyFrame(0f)).thenReturn(keyFrameSprite);
		when(keyFrameSprite.getWidth()).thenReturn(10f);
		when(keyFrameSprite.getName()).thenReturn("swordmaster-FALLATTACKING1-left-effect");
		Character character = mock(Character.class);
		Vector2 position = new Vector2(10f, 10f);
		when(character.getPhysicsComponent()).thenReturn(mock(CharacterPhysicsComponentImpl.class));
		when(character.getPhysicsComponent().getPosition()).thenReturn(position);

		SpecialEffect specialEffect = new SpecialEffect(animation, character);

		assertEquals(0f, specialEffect.getTimeAlive(), 0.001f);
		assertFalse(specialEffect.isDead());
		assertEquals(position, specialEffect.getPosition());
	}

	@Test
	public void testAdjustPosition_LeftDirection() {
		Animation<NamedSprite> animation = mock(Animation.class);
		NamedSprite keyFrameSprite = mock(NamedSprite.class);
		when(animation.getKeyFrame(0f)).thenReturn(keyFrameSprite);
		when(keyFrameSprite.getWidth()).thenReturn(10f);
		when(keyFrameSprite.getName()).thenReturn("swordmaster-FALLATTACKING1-left-effect");

		Character character = mock(Character.class);
		Vector2 position = new Vector2(10f, 10f);
		when(character.getPhysicsComponent()).thenReturn(mock(CharacterPhysicsComponentImpl.class));
		when(character.getPhysicsComponent().getPosition()).thenReturn(position);
		when(character.getPhysicsComponent().getDirection()).thenReturn(Direction.left);

		SpecialEffect specialEffect = new SpecialEffect(animation, character);

		assertEquals(0f, specialEffect.getTimeAlive(), 0.001f);
		assertFalse(specialEffect.isDead());
		assertEquals(new Vector2(10f, 10f), specialEffect.getPosition());
	}

	@Test
	public void testGetSprite() {
		Animation<NamedSprite> animation = mock(Animation.class);
		NamedSprite keyFrameSprite = mock(NamedSprite.class);
		when(animation.getKeyFrame(0f)).thenReturn(keyFrameSprite);
		when(keyFrameSprite.getName()).thenReturn("swordmaster-FALLATTACKING1-left-effect");
		Character character = mock(Character.class);
		Vector2 position = new Vector2(10f, 10f);
		when(character.getPhysicsComponent()).thenReturn(mock(CharacterPhysicsComponentImpl.class));
		when(character.getPhysicsComponent().getPosition()).thenReturn(position);

		SpecialEffect specialEffect = new SpecialEffect(animation, character);
		NamedSprite sprite = specialEffect.getSprite();

		assertEquals(keyFrameSprite, sprite);
		verify(keyFrameSprite).setPosition(anyFloat(), anyFloat());
	}

	@Test
	public void testUpdate_AnimationNotFinished() {
		Animation<NamedSprite> animation = mock(Animation.class);
		NamedSprite sprite = mock(NamedSprite.class);
		when(animation.isAnimationFinished(1f)).thenReturn(false);
		when(sprite.getName()).thenReturn("swordmaster-FALLATTACKING1-left-effect");
		when(animation.getKeyFrame(0f)).thenReturn(sprite);

		Character character = mock(Character.class);
		Vector2 position = new Vector2(10f, 10f);
		when(character.getPhysicsComponent()).thenReturn(mock(CharacterPhysicsComponentImpl.class));
		when(character.getPhysicsComponent().getPosition()).thenReturn(position);

		SpecialEffect specialEffect = new SpecialEffect(animation, character);
		specialEffect.update(1f);

		assertEquals(1f, specialEffect.getTimeAlive(), 0.001f);
		assertFalse(specialEffect.isDead());
	}

	@Test
	public void testUpdate_AnimationFinished() {
		Animation<NamedSprite> animation = mock(Animation.class);
		NamedSprite sprite = mock(NamedSprite.class);
		when(animation.isAnimationFinished(50f)).thenReturn(true);
		when(sprite.getName()).thenReturn("swordmaster-FALLATTACKING1-left-effect");
		when(animation.getKeyFrame(0f)).thenReturn(sprite);

		Character character = mock(Character.class);
		Vector2 position = new Vector2(10f, 10f);
		when(character.getPhysicsComponent()).thenReturn(mock(CharacterPhysicsComponentImpl.class));
		when(character.getPhysicsComponent().getPosition()).thenReturn(position);

		SpecialEffect specialEffect = new SpecialEffect(animation, character);
		specialEffect.update(50f);

		assertEquals(50f, specialEffect.getTimeAlive(), 0.001f);
		assertTrue(specialEffect.isDead());
	}

	@Test
	public void testDraw() {
		Animation<NamedSprite> animation = mock(Animation.class);
		NamedSprite sprite = mock(NamedSprite.class);
		when(sprite.getName()).thenReturn("swordmaster-FALLATTACKING1-left-effect");
		when(animation.getKeyFrame(0f)).thenReturn(sprite);
		SpriteBatch batch = mock(SpriteBatch.class);

		Character character = mock(Character.class);
		Vector2 position = new Vector2(10f, 10f);
		when(character.getPhysicsComponent()).thenReturn(mock(CharacterPhysicsComponentImpl.class));
		when(character.getPhysicsComponent().getPosition()).thenReturn(position);

		SpecialEffect specialEffect = new SpecialEffect(animation, character);
		specialEffect.draw(batch);

		verify(sprite).draw(batch);
	}

	@Test
	public void testToString() {
		Animation<NamedSprite> animation = mock(Animation.class);
		NamedSprite keyFrameSprite = mock(NamedSprite.class);
		when(keyFrameSprite.getName()).thenReturn("swordmaster-FALLATTACKING1-left-effect");
		when(animation.getKeyFrame(0f)).thenReturn(keyFrameSprite);

		Character character = mock(Character.class);
		Vector2 position = new Vector2(10f, 10f);
		when(character.getPhysicsComponent()).thenReturn(mock(CharacterPhysicsComponentImpl.class));
		when(character.getPhysicsComponent().getPosition()).thenReturn(position);

		SpecialEffect specialEffect = new SpecialEffect(animation, character);
		String toStringResult = specialEffect.toString();

		assertEquals("special effect for : swordmaster-FALLATTACKING1-left-effectalive for 0.0", toStringResult);
	}
}
