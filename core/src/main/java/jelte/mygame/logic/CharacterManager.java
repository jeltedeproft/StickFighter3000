package jelte.mygame.logic;

import java.util.UUID;

import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.physics.PhysicsComponent;

public class CharacterManager {
	private Character player;
	private Array<Character> enemies;
	private Array<PhysicsComponent> bodies;

	public CharacterManager(Character player) {
		this.player = player;
		enemies = new Array<>();
		bodies = new Array<>();
		bodies.add(player.getPhysicsComponent());
	}

	public void addEnemy(Character enemy) {
		enemies.add(enemy);
		bodies.add(enemy.getPhysicsComponent());
	}

	public void removeEnemy(Character enemy) {
		enemies.removeValue(enemy, false);
		bodies.removeValue(enemy.getPhysicsComponent(), false);
	}

	public Character getCharacterById(UUID id) {
		if (player.getId().equals(id)) {
			return player;
		}
		for (Character enemy : enemies) {
			if (enemy.getId().equals(id)) {
				return enemy;
			}
		}
		return null;
	}

	public Array<Character> getAllCharacters() {
		Array<Character> allCharacters = new Array<>(enemies);
		allCharacters.add(player);
		return allCharacters;
	}

	public Array<Character> getEnemies() {
		return enemies;
	}

	public Character getPlayer() {
		return player;
	}

	public Array<PhysicsComponent> getBodies() {
		return bodies;
	}

	public void update(float delta) {
		getAllCharacters().forEach(character -> character.update(delta));
	}

}
