package de.hochschuletrier.gdw.ws1314.entity;

import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author ElFapo
 *
 */
public abstract class ClientEntity 
{
	private Vector2 	position;
	private long 		id;
	
	public static EntityType type;
	
	public ClientEntity(Vector2 position, long id)
	{
		this.position = position;
		this.id = id;
	}
	
	public void setPosition(Vector2 position)
	{
		this.position = position;
	}
	
	public Vector2 getPosition()
	{
		return this.position;
	}
	
	public long getID()
	{
		return id;
	}
	
	public abstract void enable();
	public abstract void disable();
    public abstract void dispose();
    public abstract void update(int delta);
	
	public abstract void render();
	
	public abstract void setEntityState(EntityState state);
}
