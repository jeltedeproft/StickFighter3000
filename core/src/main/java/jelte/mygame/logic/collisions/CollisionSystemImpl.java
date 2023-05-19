package jelte.mygame.logic.collisions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.physics.PhysicsComponent;
import jelte.mygame.logic.collisions.Collidable.COLLIDABLE_TYPE;
import jelte.mygame.logic.spells.Spell;

public class CollisionSystemImpl implements CollisionSystem {
	private SpatialMesh spatialMesh;

	@Override
	public void addToSpatialMesh(Collidable collidable) {// TODO shouldnt know about spatialMesh, just executreCollisions and check inside spatialmesh if new characters needto be added or updated
		spatialMesh.addCollidable(collidable);
	}

	@Override
	public void updateSpatialMesh(Collidable collidable) {
		spatialMesh.updateCollidable(collidable);
	}

	@Override
	public void addToSpatialMesh(Array<Collidable> collidables) {
		spatialMesh.addCollidables(collidables);
	}

	@Override
	public void updateSpatialMesh(Array<Collidable> collidables) {
		spatialMesh.updateCollidables(collidables);
	}

	@Override
	public void executeCollisions() {
		Map<UUID, UUID> doneCollisions = new HashMap<>();
		for (CollisionData collisionData : spatialMesh.getAllPossibleCollisions()) {
			Set<Collidable> dynamicCollidables = collisionData.getDynamicCollidables();
			Set<Collidable> staticCollidables = collisionData.getStaticCollidables();
			if (!dynamicCollidables.isEmpty()) {
				if (dynamicCollidables.size() == 1) {
					if (!staticCollidables.isEmpty()) {
						for (Collidable dynamicCollidable : dynamicCollidables) {
							handleStaticCollision(dynamicCollidable, staticCollidables, doneCollisions);
						}

					}
				} else {
					Array<Collidable> dynamicCollidablesArray = new Array<>(dynamicCollidables.toArray(new Collidable[0]));
					for (int i = 0; i < dynamicCollidablesArray.size - 1; i++) {
						for (int j = i + 1; j < dynamicCollidablesArray.size; j++) {
							Collidable currentDynamicCollidable = dynamicCollidablesArray.get(i);
							Collidable nextDynamicCollidable = dynamicCollidablesArray.get(j);
							if (doneCollisions.containsKey(currentDynamicCollidable.getId()) && doneCollisions.containsValue(nextDynamicCollidable.getId()) || doneCollisions.containsKey(nextDynamicCollidable.getId()) && doneCollisions.containsValue(currentDynamicCollidable.getId())) {
								continue;
							}
							resolveDynamicCollision(currentDynamicCollidable, nextDynamicCollidable);
							handleStaticCollision(currentDynamicCollidable, staticCollidables, doneCollisions);
						}
					}
					handleStaticCollision(dynamicCollidablesArray.get(dynamicCollidablesArray.size - 1), staticCollidables, doneCollisions);// dont forget the last one
				}
			}
		}
	}

	private void handleStaticCollision(Collidable collidable, Set<Collidable> staticColliders, Map<UUID, UUID> doneCollisions) {
		if (collidable.getType().equals(COLLIDABLE_TYPE.CHARACTER)) {
			handleStaticCollisionCharacter((PhysicsComponent) collidable, staticColliders, doneCollisions);
		}

		if (collidable.getType().equals(COLLIDABLE_TYPE.SPELL)) {
			handleStaticCollisionSpell((Spell) collidable, staticColliders, doneCollisions);
		}
	}

	private void handleStaticCollisionSpell(Spell collidable, Set<Collidable> staticColliders, Map<UUID, UUID> doneCollisions) {
		// TODO Auto-generated method stub
	}

	private void handleStaticCollisionCharacter(PhysicsComponent body, Set<Collidable> staticColliders, Map<UUID, UUID> doneCollisions) {
		Array<StaticBlock> overlappingObstacles = getOverlappingObstacles(body.getRectangle(), staticColliders);
		boolean collided = !overlappingObstacles.isEmpty();

		if (collided) {
			body.setCollided(true);
			handleCollision(body, body.getPosition(), overlappingObstacles, doneCollisions);
		}
	}

	private Array<StaticBlock> getOverlappingObstacles(Rectangle playerRect, Set<Collidable> staticColliders) {
		Array<StaticBlock> overlappingObstacles = new Array<>();

		for (Collidable obstacle : staticColliders) {
			StaticBlock staticBlock = (StaticBlock) obstacle;

			boolean isInside = staticBlock.overlaps(playerRect) && staticBlock.contains(playerRect);
			if (isInside) {
				staticBlock.contains();
				overlappingObstacles.add(staticBlock);
			} else if (staticBlock.overlaps(playerRect)) {
				staticBlock.calculateOverlapPlayer(playerRect);
				overlappingObstacles.add(staticBlock);
			}
		}

		return overlappingObstacles;
	}

	private void handleCollision(PhysicsComponent body, Vector2 pos, Array<StaticBlock> overlappingObstacles, Map<UUID, UUID> doneCollisions) {
		for (StaticBlock obstacle : overlappingObstacles) {
			if (!doneCollisions.containsKey(body.getPlayerReference()) || !doneCollisions.containsValue(obstacle.getId())) {
				doneCollisions.put(body.getPlayerReference(), obstacle.getId());
				obstacle.handleCollision(body, pos);
			}
		}
	}

	// can be spell or character
	private void resolveDynamicCollision(Collidable object1, Collidable object2) {
		COLLIDABLE_TYPE type1 = object1.getType();
		COLLIDABLE_TYPE type2 = object2.getType();

		if (COLLIDABLE_TYPE.CHARACTER.equals(type1) && COLLIDABLE_TYPE.CHARACTER.equals(type2)) {
			resolveCollisionCharacterCharacter((Character) object1, (Character) object2);
		}

		if (COLLIDABLE_TYPE.CHARACTER.equals(type1) && COLLIDABLE_TYPE.SPELL.equals(type2)) {
			resolveCollisionCharacterSpell((Character) object1, (Spell) object2);
		}

		if (COLLIDABLE_TYPE.SPELL.equals(type1) && COLLIDABLE_TYPE.CHARACTER.equals(type2)) {
			resolveCollisionCharacterSpell((Character) object2, (Spell) object1);
		}

		if (COLLIDABLE_TYPE.SPELL.equals(type1) && COLLIDABLE_TYPE.SPELL.equals(type2)) {
			resolveCollisionSpellSpell((Spell) object1, (Spell) object2);
		}
	}

	private void resolveCollisionSpellSpell(Spell object1, Spell object2) {
		// TODO Auto-generated method stub

	}

	private void resolveCollisionCharacterSpell(Character object1, Spell object2) {
		// TODO Auto-generated method stub

	}

	private void resolveCollisionCharacterCharacter(Character character1, Character character2) {
		PhysicsComponent mainBody = character1.getPhysicsComponent();
		PhysicsComponent otherBody = character2.getPhysicsComponent();
		if (mainBody.getRectangle().overlaps(otherBody.getRectangle())) {
			character1.damage(0.5f);
			character2.damage(0.5f);
		}
	}

	public void initSpatialMesh(Vector2 mapBoundaries) {
		spatialMesh = new SpatialMesh(mapBoundaries);
	}

	@Override
	public void reset() {
		spatialMesh.removeAllCollidables();
	}

}