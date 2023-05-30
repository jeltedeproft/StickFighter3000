package jelte.mygame.logic.character;

import java.util.UUID;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

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

	public Array<PhysicsComponent> getAllCharacterbodies() {
		Array<PhysicsComponent> characterbodies = new Array<>();
		characterbodies.add(player.getPhysicsComponent());
		for (Character enemy : enemies) {
			characterbodies.add(enemy.getPhysicsComponent());
		}
		return characterbodies;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("player");
		sb.append(" --> ");
		sb.append(player.toString());
		sb.append("\n");
		for (Character enemy : enemies) {
			sb.append("character");
			sb.append(" --> ");
			sb.append(enemy.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

}
