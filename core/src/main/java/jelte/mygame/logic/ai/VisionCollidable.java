package jelte.mygame.logic.ai;

import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jelte.mygame.logic.character.AiCharacter;
import jelte.mygame.logic.character.Direction;
import jelte.mygame.logic.collisions.collidable.Collidable;
import lombok.Getter;

@Getter
public class VisionCollidable implements Collidable {
	private UUID id;
	private Rectangle oldRectangle;
	private Rectangle rectangle;
	private boolean hasMoved = false;
	private float visionWidth;
	private float visionHeight;
	private boolean playerSeen = false;

	public VisionCollidable(final AiCharacter aiCharacter) {
		this.id = UUID.randomUUID();
		this.rectangle = createVisionRectangleForCharacter(aiCharacter);
		oldRectangle = new Rectangle(rectangle);
	}

	private Rectangle createVisionRectangleForCharacter(AiCharacter aiCharacter) {
		visionWidth = aiCharacter.getData().getVisionShapeWidth();
		visionHeight = aiCharacter.getData().getVisionShapeHeight();
		Vector2 pos = aiCharacter.getPhysicsComponent().getPosition();
		if (aiCharacter.getPhysicsComponent().getDirection() == Direction.left) {
			float posX = pos.x - visionWidth < 0 ? 0 : pos.x - visionWidth;
			return new Rectangle(posX, pos.y, visionWidth, visionHeight);
		}
		return new Rectangle(pos.x, pos.y, visionWidth, visionHeight);
	}

	public void update(AiCharacter aiCharacter) {
		hasMoved = false;
		if (rectangle.x != aiCharacter.getPhysicsComponent().getPosition().x || rectangle.y != aiCharacter.getPhysicsComponent().getPosition().y) {
			hasMoved = true;
			if (aiCharacter.getPhysicsComponent().getDirection() == Direction.left) {
				rectangle.x = aiCharacter.getPhysicsComponent().getPosition().x - visionWidth < 0 ? 0 : aiCharacter.getPhysicsComponent().getPosition().x - visionWidth;
			} else {
				rectangle.x = aiCharacter.getPhysicsComponent().getPosition().x;
			}
			rectangle.y = aiCharacter.getPhysicsComponent().getPosition().y;
		}
	}

	public void playerSeen() {
		playerSeen = true;
	}

	public void setPlayerSeen(boolean playerSeen) {
		this.playerSeen = playerSeen;
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public Rectangle getRectangle() {
		return rectangle;
	}

	@Override
	public Rectangle getOldRectangle() {
		return oldRectangle;
	}

	@Override
	public boolean hasMoved() {
		return hasMoved;
	}

	@Override
	public boolean isStatic() {
		return false;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean goesTroughObjects() {
		return false;
	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.VISION;
	}

}
