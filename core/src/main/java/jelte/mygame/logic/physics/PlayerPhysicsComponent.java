package jelte.mygame.logic.physics;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerPhysicsComponent extends CharacterPhysicsComponentImpl {
	private static final String TAG = PlayerPhysicsComponent.class.getSimpleName();

	public PlayerPhysicsComponent(UUID playerReference, Vector2 startPosition) {
		super(playerReference, startPosition);
	}

	@Override
	public COLLIDABLE_TYPE getType() {
		return COLLIDABLE_TYPE.PLAYER;
	}

}
