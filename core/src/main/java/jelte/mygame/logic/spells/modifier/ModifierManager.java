package jelte.mygame.logic.spells.modifier;

import com.badlogic.gdx.utils.Array;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.spells.modifier.Modifier.MODIFIER_TYPE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ModifierManager {

	private Map<Modifier, CharacterDelay> timersToStart;
	private Map<Character, ConcurrentLinkedQueue<Modifier>> charactersWithModifiers;

	public ModifierManager() {
		timersToStart = new ConcurrentHashMap<>();
		charactersWithModifiers = new ConcurrentHashMap<>();
	}

	public void addModifier(ModifiersEnum type, Character character) {
		Modifier modifier = new Modifier(type, UUID.randomUUID());
		addModifier(modifier, character);
	}

	public void addModifier(Modifier modifier, Character character) {
		applyInitialEffect(modifier, character);
		charactersWithModifiers.putIfAbsent(character, new ConcurrentLinkedQueue<>());
		charactersWithModifiers.get(character).add(modifier);
	}

	public void addDelayedModifier(ModifiersEnum type, Character character, float time, float delay) {
		Modifier modifier = new Modifier(type, time, UUID.randomUUID());
		timersToStart.put(modifier, new CharacterDelay(character, delay));
	}

	public void removeModifier(Modifier modifier, Character character) {
		if (charactersWithModifiers.get(character).remove(modifier)) {
			applyEndEffect(modifier, character);
		}
	}

	public void removeModifierOfType(ModifiersEnum modifier, Character character) {
		ConcurrentLinkedQueue<Modifier> currentModifiers = charactersWithModifiers.get(character);
		final Iterator<Modifier> iterator = currentModifiers.iterator();
		while (iterator.hasNext()) {
			final Modifier currentModifier = iterator.next();
			if (modifier.equals(currentModifier.getType())) {
				applyEndEffect(currentModifier, character);
				iterator.remove();
				break;
			}
		}
	}

	public void removeAllModifiersOfType(ModifiersEnum modifier, Character character) {
		ConcurrentLinkedQueue<Modifier> currentModifiers = charactersWithModifiers.get(character);
		final Iterator<Modifier> iterator = currentModifiers.iterator();
		while (iterator.hasNext()) {
			final Modifier currentModifier = iterator.next();
			if (modifier.equals(currentModifier.getType())) {
				applyEndEffect(currentModifier, character);
				iterator.remove();
			}
		}
	}

	public ConcurrentLinkedQueue<Modifier> getModifiersByCharacter(Character character) {
		return charactersWithModifiers.get(character);
	}

	private void applyInitialEffect(Modifier modifier, Character character) {
		switch (modifier.getType()) {
		case FIRE:
			break;
		case FROZEN:
			break;
		case SILENCED:
			break;
		case STUNNED:
			break;
		case SPEED_BUFF:
			break;
		case SPEED_DEBUFF:
			break;
		case TIMED_KILL:
			break;
		case MOVE_ONLY_FORWARD:
			break;
		case MOVE_ONLY_BACKWARD:
			break;
		case SHIELDED:
			break;
		case RUPTURED:
			break;
		default:
			break;
		}

	}

	public void update(float delta, Array<Character> characters) {
		updateDelayedModifiers(delta);
		characters.forEach(this::updateCharacter);
		for (Character character : characters) {
			for (Modifier modifier : charactersWithModifiers.get(character)) {
				modifier.update(delta);

				updateModifierByType(delta, character, modifier);
			}

			// cleanup
			ConcurrentLinkedQueue<Modifier> currentModifiers = charactersWithModifiers.get(character);
			final Iterator<Modifier> iterator = currentModifiers.iterator();
			while (iterator.hasNext()) {
				final Modifier currentModifier = iterator.next();
				if (currentModifier.isCompleted()) {
					applyEndEffect(currentModifier, character);
					iterator.remove();
				}
			}
		}
	}

	private void updateCharacter(Character character) {
		if (!character.getModifiersreadyToApply().isEmpty()) {
			String modifierName = character.getModifiersreadyToApply().removeFirst();
			addModifier(ModifiersEnum.valueOf(modifierName), character);
		}
	}

	private void updateModifierByType(float delta, Character character, Modifier modifier) {
		switch (MODIFIER_TYPE.valueOf(modifier.getModifierType())) {
		case DOT:
			if (modifier.getCurrentTickTime() > 0) {
				modifier.setCurrentTickTime(modifier.getCurrentTickTime() - delta);
			} else {
				character.damage(modifier.getAmount());
			}
			break;
		case BUFF, DAMAGE, DEBUFF, NO_DIRECTION, NO_MOVE, NO_SPELL, STUNNED:
		default:
			break;

		}
	}

	private void updateDelayedModifiers(float delta) {
		timersToStart.forEach((k, v) -> v.setDelay(v.getDelay() - delta));

		final Iterator<Entry<Modifier, CharacterDelay>> iterator = timersToStart.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<Modifier, CharacterDelay> entry = iterator.next();
			if (entry.getValue().getDelay() <= 0) {
				addModifier(entry.getKey(), entry.getValue().getCharacter());
				iterator.remove();
			}
		}
	}

	private void applyEndEffect(Modifier modifier, Character character) {
		switch (modifier.getType()) {
		case FIRE:
			break;
		case FROZEN:
			break;
		case SILENCED:
			break;
		case STUNNED:
			break;
		case SPEED_BUFF:
			break;
		case TIMED_KILL:
			character.damage(10000);
			break;
		case MOVE_ONLY_FORWARD:
			break;
		case MOVE_ONLY_BACKWARD:
			break;
		case SHIELDED:
			break;
		default:
			break;
		}

	}

	@Getter
	@Setter
	@AllArgsConstructor
	private class CharacterDelay {
		private Character character;
		private float delay;
	}
}
