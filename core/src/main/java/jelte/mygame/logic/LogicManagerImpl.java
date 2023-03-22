package jelte.mygame.logic;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import jelte.mygame.Message;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.MessageListener;
import jelte.mygame.logic.character.Character;
import jelte.mygame.logic.character.CharacterFileReader;
import jelte.mygame.utility.Constants;

public class LogicManagerImpl implements LogicManager {
	private static final String TAG = LogicManagerImpl.class.getSimpleName();
	private MessageListener listener;
	private float accumulator = 0;
	private World world;
	private Character player;

	public LogicManagerImpl(MessageListener listener) {
		this.listener = listener;
		Box2D.init();
		world = new World(Constants.GRAVITY, true);
		populateWorld();
	}

	private void populateWorld() {
		createPlayerBody();
	}

	private void createPlayerBody() {// TODO use box2d-editor to create a custom shape for the player
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(Constants.PLAYER_START);
		player = new Character(CharacterFileReader.getUnitData().get(2), UUID.randomUUID(), world.createBody(bodyDef));
		player.getBox2DBody().setUserData(player);
		PolygonShape square = new PolygonShape();
		square.setAsBox(10, 10);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = square;
		fixtureDef.density = 0.7f;
		fixtureDef.friction = 0f;
		fixtureDef.restitution = 0.1f; // Make it bounce a little bit
		player.getBox2DBody().createFixture(fixtureDef);
		square.dispose();
		player.getBox2DBody().setFixedRotation(true);
	}

	@Override
	public void update(float delta) {
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_WORLD, world));
		player.update(delta);
		doPhysicsStep(delta);
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.UPDATE_CAMERA_POS, player.getBox2DBody().getPosition()));
	}

	private void doPhysicsStep(float deltaTime) {
		float frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;
		while (accumulator >= Constants.TIME_STEP) {
			world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
			accumulator -= Constants.TIME_STEP;
		}
	}

	@Override
	public void receiveMessage(Message message) {
		switch (message.getAction()) {
		case DOWN_PRESSED:
		case DOWN_UNPRESSED:
		case LEFT_PRESSED:
		case LEFT_UNPRESSED:
		case RIGHT_PRESSED:
		case RIGHT_UNPRESSED:
		case UP_PRESSED:
			player.receiveMessage(message);
			break;
		case SEND_MAP_DIMENSIONS:
			Vector2 bounds = (Vector2) message.getValue();
			createWorldBounds(bounds.x, bounds.y);
			break;
		default:
			break;

		}
	}

	/**
	 * Creates Box2D bounds.
	 *
	 * @param height
	 * @param width
	 */
	private void createWorldBounds(float mapWidth, float mapHeight) {
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(new Vector2(Constants.VISIBLE_WIDTH / 2, Constants.VISIBLE_HEIGHT / 2));

		final ChainShape shape = new ChainShape();
		shape.createLoop(new float[] { -Constants.VISIBLE_WIDTH / 2f, -Constants.VISIBLE_HEIGHT / 2f, -Constants.VISIBLE_WIDTH / 2f, mapHeight - Constants.VISIBLE_HEIGHT / 2f, mapWidth - Constants.VISIBLE_WIDTH / 2f, mapHeight - Constants.VISIBLE_HEIGHT / 2f, mapWidth - Constants.VISIBLE_WIDTH / 2f, -Constants.VISIBLE_HEIGHT / 2f });

		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 0f;
		fixtureDef.density = 0.1f;

		world.createBody(bodyDef).createFixture(fixtureDef);
		shape.dispose();
	}

	@Override
	public void dispose() {

	}

}
