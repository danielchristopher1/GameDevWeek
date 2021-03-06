package de.hochschuletrier.gdw.ws1314.entity.levelObjects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

import de.hochschuletrier.gdw.commons.gdx.physix.PhysixBody;
import de.hochschuletrier.gdw.commons.gdx.physix.PhysixBodyDef;
import de.hochschuletrier.gdw.commons.gdx.physix.PhysixFixtureDef;
import de.hochschuletrier.gdw.commons.gdx.physix.PhysixManager;
import de.hochschuletrier.gdw.ws1314.entity.EntityStates;
import de.hochschuletrier.gdw.ws1314.entity.EntityType;
import de.hochschuletrier.gdw.ws1314.entity.ServerEntity;
import de.hochschuletrier.gdw.ws1314.entity.player.ServerPlayer;

/**
 * 
 * @author yannick
 * 
 */
public class ServerContactMine extends ServerLevelObject {
	private final float DURATION_TILL_EXPLOSION_MAX = 3.0f;
	private final float DURATION_TILL_EXPLOSION_MIN = 1.0f;
	private final float DAMAGE = 80.0f;
	private PhysixManager manager;
	private float originRadius = 2.0f;
	private final float EXPLOSION_RADIUS = 60.0f;
	private final float DURATION_EXPLOSION = 1.0f;
	private boolean isActive = false;
	private float timer;
	private long sourceID;
	private boolean gotDamage = false;

	private Fixture fixture1;
	private Fixture fixture2;

	public ServerContactMine() {
		super();
		sourceID = -1;
	}

	public long getSourceID() {
		return sourceID;
	}

	@Override
	public void initialize() {
		super.initialize();
		timer = MathUtils.random(DURATION_TILL_EXPLOSION_MIN,
				DURATION_TILL_EXPLOSION_MAX) + DURATION_EXPLOSION;
		;
	}

	@Override
	public void beginContact(Contact contact) {

		ServerEntity otherEntity = this.identifyContactFixtures(contact);
		Fixture f = this.getCollidingFixture(contact);

		if (f == fixture2) {
			switch (otherEntity.getEntityType()) {
			case Hunter:
			case Knight:
			case Tank:
			case Noob:
				if (!fixture2.isSensor()) {
					ServerPlayer player = (ServerPlayer) otherEntity;
					if(!gotDamage){
						player.applyDamage(DAMAGE);
						gotDamage = true;
					}
					this.physicsBody.getBody().getFixtureList().get(1).getShape().setRadius(originRadius);
				}
			case HayBale:
				break;
			default:
					break;
			}
		} else if (f == fixture1) {
			switch (otherEntity.getEntityType()) {
			case Hunter:
			case Knight:
			case Tank:
			case Noob:
			case HayBale:
				this.isActive = true;
				this.setEntityState(EntityStates.ATTACK);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		
		ServerEntity otherEntity = this.identifyContactFixtures(contact);
		Fixture f = this.getCollidingFixture(contact);

		if (f == fixture2) {
			switch (otherEntity.getEntityType()) {
			case Hunter:
			case Knight:
			case Tank:
			case Noob:
			case HayBale:
				if(this.getEntityState() == EntityStates.EXPLODING){
					this.setEntityState(EntityStates.NONE);
					this.physicsBody.getBody().getFixtureList().get(1).getShape().setRadius(originRadius);
				}
				break;
			default:
				break;
			}
		} else if (f == fixture1) {
			switch (otherEntity.getEntityType()) {
			case Hunter:
			case Knight:
			case Tank:
			case Noob:
			case HayBale:
				break;
			default:
				break;
			}
		}

	}

	public void update(float deltaTime) {
		CircleShape shape = (CircleShape) this.physicsBody.getBody()
				.getFixtureList().get(1).getShape();
		if(this.getEntityState() == EntityStates.NONE ){
			originRadius = manager.toBox2D(2.0f);
			shape.setRadius(originRadius);
			gotDamage = false;
			this.physicsBody.getBody().getFixtureList().get(1).setSensor(true);
		}else if(this.getEntityState() == EntityStates.ATTACK || this.getEntityState() == EntityStates.EXPLODING){
			if(this.getEntityState() == EntityStates.ATTACK){
				gotDamage = false;
				this.physicsBody.getBody().getFixtureList().get(1).setSensor(true);
			}
			timer -= deltaTime;
			if(timer <= 1){
				this.setEntityState(EntityStates.EXPLODING);
				if (originRadius <= 1.5f) {
					originRadius += manager.toBox2D(2.0f);
					this.physicsBody.getBody().getFixtureList().get(1).setSensor(false);
					shape.setRadius(originRadius);
				}
				if(timer <= 0){
				
					this.setEntityState(EntityStates.NONE);
					timer += timer = MathUtils.random(DURATION_TILL_EXPLOSION_MIN,
							DURATION_TILL_EXPLOSION_MAX) + DURATION_EXPLOSION;
					this.physicsBody.getBody().getFixtureList().get(1).setSensor(true);
				}
			}
		}
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.ContactMine;
	}

	@Override
	public void initPhysics(PhysixManager manager) {
		this.manager = manager;
		PhysixBody body = new PhysixBodyDef(BodyDef.BodyType.KinematicBody,
				manager)
				.position(
						new Vector2(properties.getFloat("x"), properties
								.getFloat("y"))).fixedRotation(false).create();
		body.createFixture(new PhysixFixtureDef(manager).density(0.5f)
				.friction(0.0f).sensor(true).restitution(0.0f).shapeBox(10, 10));
		body.createFixture(new PhysixFixtureDef(manager).density(0.5f)
				.friction(0.0f).sensor(true).restitution(0.0f)
				.shapeCircle(originRadius));
		body.setGravityScale(0);
		body.addContactListener(this);
		setPhysicsBody(body);

		fixture1 = this.physicsBody.getBody().getFixtureList().get(0);
		fixture2 = this.physicsBody.getBody().getFixtureList().get(1);

	}

}
