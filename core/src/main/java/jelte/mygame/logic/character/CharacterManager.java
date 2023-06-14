package jelte.mygame.logic.character;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

import jelte.mygame.logic.character.state.CharacterStateManager.EVENT;
import jelte.mygame.logic.collisions.collidable.Collidable;
import jelte.mygame.logic.physics.PhysicsComponent;

public class CharacterManager {
	private PlayerCharacter player;
	private Array<NpcCharacter> enemies;
	private Array<PhysicsComponent> bodies;

	public CharacterManager(PlayerCharacter player) {
		this.player = player;
		enemies = new Array<>();
		bodies = new Array<>();
		bodies.add(player.getPhysicsComponent());
	}

	public void addEnemy(NpcCharacter enemy) {
		enemies.add(enemy);
		bodies.add(enemy.getPhysicsComponent());
	}

	public void removeEnemy(NpcCharacter enemy) {
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

	public Set<Collidable> getAllCharacterbodies() {
		Set<Collidable> characterbodies = new HashSet<>();
		characterbodies.add(player.getPhysicsComponent());
		for (Character enemy : enemies) {
			characterbodies.add(enemy.getPhysicsComponent());
		}
		return characterbodies;
	}

	public Array<NpcCharacter> getEnemies() {
		return enemies;
	}

	public Character getPlayer() {
		return player;
	}

	public Array<PhysicsComponent> getBodies() {
		return bodies;
	}

	public void update(float delta) {
		player.update(delta);
		enemies.forEach(enemy -> enemy.update(delta, player));
		bodies.forEach(this::checkCollision);
	}

	private void checkCollision(PhysicsComponent body) {
		if (body.isCollided()) {
			body.setCollided(false);
		} else {
			getCharacterById(body.getOwnerReference()).getCurrentCharacterState().handleEvent(EVENT.NO_COLLISION);
		}
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
