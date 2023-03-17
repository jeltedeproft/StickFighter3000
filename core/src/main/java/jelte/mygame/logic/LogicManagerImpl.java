package jelte.mygame.logic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import jelte.mygame.Message;
import jelte.mygame.MessageListener;
import jelte.mygame.Message.ACTION;
import jelte.mygame.Message.RECIPIENT;
import jelte.mygame.utility.Constants;

public class LogicManagerImpl implements LogicManager {
	private MessageListener listener;
	private float accumulator = 0;
	private World world;

	public LogicManagerImpl(MessageListener listener) {
		this.listener = listener;
		Box2D.init();
		world = new World(Constants.GRAVITY, true);
		populateWorld();
	}

	private void populateWorld() {
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(50, 50);

		// Create our body in the world using our body definition
		Body body = world.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		CircleShape circle = new CircleShape();
		circle.setRadius(6f);

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit

		// Create our fixture and attach it to the body
		Fixture fixture = body.createFixture(fixtureDef);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		circle.dispose();
		
		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();  
		// Set its world position
		groundBodyDef.position.set(new Vector2(0, 10));  

		// Create a body from the definition and add it to the world
		Body groundBody = world.createBody(groundBodyDef);  

		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();  
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		groundBox.setAsBox(Constants.VISIBLE_WIDTH, 20.0f);
		// Create a fixture from our polygon shape and add it to our ground body  
		groundBody.createFixture(groundBox, 0.0f);
		// Clean up after ourselves
		groundBox.dispose();
	}

	@Override
	public void update(float delta) {
		listener.receiveMessage(new Message(RECIPIENT.GRAPHIC, ACTION.RENDER_WORLD,world));
		doPhysicsStep(delta);
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
			break;
		case DOWN_UNPRESSED:
			break;
		case LEFT_PRESSED:
			break;
		case LEFT_UNPRESSED:
			break;
		case RIGHT_PRESSED:
			break;
		case RIGHT_UNPRESSED:
			break;
		case UP_PRESSED:
			break;
		default:
			break;

		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
