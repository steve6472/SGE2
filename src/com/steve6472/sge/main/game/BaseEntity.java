package com.steve6472.sge.main.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.main.MainApplication;

public abstract class BaseEntity extends Killable implements IObject, Cloneable, IInit, Serializable
{
	private static final long serialVersionUID = -1719377764373653659L;
	protected Vec2 loc;
	protected AABB box;
	protected Sprite sprite;
	protected EntityList entityList;
	protected MainApplication mainApp;
	protected List<Task> tasks;
	protected List<Tag> tags;
	protected double speed = 1d;
	protected double angle;
	protected double cos, sin;
	protected double xa, ya;
	private double oldAngle;
	protected int id = 0;
	
	public BaseEntity()
	{
		
	}

	public abstract Sprite setSprite();
	
	/*
	 * Events
	 */
	
	/**
	 * Used for changing variables (Like new location, different sprite ect.)
	 */
	public void cloneEvent() {};

	/*
	 * Methods
	 */

	/**
	 * 
	 * @param fromx1 From X
	 * @param fromy1 From Y
	 * @param fromx2 To X
	 * @param fromy2 To Y
	 * @return angle
	 */
	public double countAngle(double fromx1, double fromy1, double fromx2, double fromy2)
	{
		return -Math.toDegrees(Math.atan2(fromx1 - fromx2, fromy1 - fromy2));
	}
	
	public double countAngle(Vec2 from, Vec2 to)
	{
		return -Math.toDegrees(Math.atan2(from.getX() - to.getX(), from.getY() - to.getY()));
	}
	
	/**
	 * Moves entity in direction (getAngle()) with speed (getSpeed())
	 * Moves loc & box
	 */
	public void move()
	{
//		loc.move2(getAngle(), getSpeed());
		
		loc.move(xa, ya, getSpeed());
		
		if (getSprite() != null)
			setBox(new AABB(loc.getX(), loc.getY(), loc.getX() + getSprite().getWidth(), loc.getY() + getSprite().getHeight()));
	}
	
	/**
	 * Moves entity in direction (angle) with speed (getSpeed)
	 * Moves loc & box
	 */
	public void move(double angle)
	{
		loc.move2(angle, getSpeed());
		
		if (getSprite() != null)
			setBox(new AABB(loc.getX(), loc.getY(), loc.getX() + getSprite().getWidth(), loc.getY() + getSprite().getHeight()));
	}
	
	/**
	 * Can be called to set cos & sin variables. (Should be called only once in tick() for efficiency)
	 * @return true if cos & sin were changed false otherwise
	 */
	public boolean updateAngles()
	{
		// If angle hasn't changed do nothing
		if (oldAngle == angle)
			return false;
		
		oldAngle = angle;

		cos = Math.cos(Math.toRadians(angle));
		sin = Math.sin(Math.toRadians(angle));
		
		xa = ((Math.cos(Math.toRadians(angle - 90))) * 1);
		ya = ((Math.sin(Math.toRadians(angle - 90))) * 1);
		
		return true;
	}
	
	/**
	 * Safetly clones entity
	 */
	public BaseEntity clone()
	{
		try
		{
			return (BaseEntity) super.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public final void tickTasks()
	{
		if (mainApp == null)
			return;
		tasks.forEach((c) -> c.tickTask(this));
	}
	
	/*
	 * Setters
	 */

	public void setLocation(Vec2 newLoc) { this.loc = newLoc; }
	
	public void setId(int id) { this.id = id; }
	
	public void setSize(double width, double height) { this.setBox(new AABB(getLocation(), width, height)); }
	
	public void setMotion(double xa, double ya) { this.xa = xa; this.ya = ya; }

	/**
	 * Creates new Vec2 from given newLoc
	 * @param newLoc new Location
	 */
	public void setNewLocation(Vec2 newLoc) { this.loc = newLoc.clone(); }
	
	public void setBox(AABB newBox) { this.box = newBox; }
	
	public void setSprite(Sprite sprite) { this.sprite = sprite; }
	
	public void setEntityList(EntityList entityList) { this.entityList = entityList; }
	
	public void setGame(MainApplication game) { this.mainApp = game; }

	/**
	 * If loc (Vec2) is null will create new Vec2!
	 * @param x
	 * @param y
	 */
	public void setLocation(double x, double y)
	{
		if (this.loc == null)
			loc = new Vec2(0, 0);
		
		this.loc.setLocation(x, y);
	}
	
	public void setSpeed(double newSpeed) { this.speed = newSpeed; }

	public void setAngle(double angle) { this.angle = angle; }

	/*
	 * Getters
	 */

	public final Vec2 getLocation() { return loc; }

	public final Vec2 getNewLocation() { return loc.clone(); }
	
	public final AABB getBox() { return box; }
	
	public Sprite getSprite() { if (entityList != null) return entityList.getSprites().get(getId()); else return null; }
	
	public String getName() { return this.getClass().getName().split("\\.")[this.getClass().getName().split("\\.").length - 1]; }
	
	public final MainApplication getMainApp() { return mainApp; }
	
	public final double getSpeed() { return speed; }

	public final double getAngle() { return angle; }

	public final double getCos() { return cos; }

	public final double getSin() { return sin; }
	
	public final double getXa() { return xa; }
	
	public final double getYa() { return ya; }
	
	public final int getId() { return id; }

	
	/*
	 * Tags
	 */
	
	public final void initTags() { tags = new ArrayList<Tag>(); }
	
	public final void addTag(Tag tag) { tags.add(tag); }
	
	public final Tag getTag(String name)
	{
		for (Tag tag : tags)
		{
			if (tag.getName().equals(name))
			{
				return tag;
			}
		}
		return null;
	}
	
	public final List<Tag> getTags() { return tags; }
	
	public final boolean hasTag(String name) { return getTag(name) != null; }
	
	/**
	 * If entity doesn't have the tag it will add it
	 * @param tag
	 */
	public final void addTagIfDoesNotHave(Tag tag)
	{
		if (!hasTag(tag.getName()))
			addTag(tag);
	}
	
	/**
	 * 
	 * @param tagName
	 *            Name of tag that should be removed
	 * @return true if tag has been removed. False otherwise
	 */
	public final boolean removeTag(String tagName)
	{
		int tagIndex = 0;
		boolean found = false;
		
		for (Tag tag : tags)
		{
			if (tag.getName().equals(tagName))
			{
				found = true;
				break;
			}
			tagIndex++;
		}
		
		if (found)
			tags.remove(tagIndex);
		
		return found;
	}
	
	public final void changeTag(String name, Object newValue)
	{
		Tag tag = getTag(name);
		if (tag != null)
		{
			tag.value = newValue;
		}
	}
}

interface IInit
{
	/**
	 * Called when creating the entity
	 * @param mainApp
	 */
	public void initEntity(MainApplication mainApp, Object... objects);
	
	default void checkClass(Object[] objects, Class<?>... classes)
	{
		final String errorMessage = "Wrong parameter input!";
		
		if (objects.length != classes.length)
			throw new IllegalArgumentException(errorMessage);
		
		for (int i = 0; i < objects.length; i++)
		{
			if (objects[i] != null)
				if (!classes[i].isInstance(objects[i]))
					throw new IllegalArgumentException(errorMessage + " " + classes[i].getTypeName() + " " + objects[i].getClass().getTypeName());
		}
	}
}
