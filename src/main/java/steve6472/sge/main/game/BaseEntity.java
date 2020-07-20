package steve6472.sge.main.game;

import steve6472.sge.main.game.inventory.Inventory;
import steve6472.sge.main.game.inventory.ItemSlot;
import steve6472.sge.main.game.mixable.IMotion2f;
import steve6472.sge.main.game.mixable.IPosition2f;
import steve6472.sge.main.game.mixable.ITag;
import steve6472.sge.main.game.mixable.Killable;
import org.joml.Vector2f;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseEntity implements Serializable, ITag, IPosition2f, IMotion2f, Killable
{
	private static final long serialVersionUID = -1719377764373653659L;
	protected Vector2f pos;
	protected Vector2f motion;
	protected Inventory inventory;
	protected List<Task> tasks;
	protected List<Tag> tags;
	private boolean isDead;

	public abstract void tick();

	public abstract void render();

	public void initEntity()
	{
		pos = new Vector2f();
		motion = new Vector2f();
		tasks = new ArrayList<>();
		tags = new ArrayList<>();
	}
	
	/*
	 * Methods
	 */

	public void move()
	{
		getPosition().add(getMotion());
	}

	public final void tickTasks()
	{
		tasks.forEach((c) -> c.tickTask(this));
	}

	/*
	 * Setters
	 */

	public <T extends ItemSlot> void setInventory(Inventory<T> inventory) { this.inventory = inventory; }
	
	/*
	 * Getters
	 */

	public final Inventory getInventory() { return inventory; };
	
	public String getName() { return this.getClass().getName().split("\\.")[this.getClass().getName().split("\\.").length - 1]; }
	
	@Override
	public Vector2f getMotion()
	{
		return motion;
	}

	@Override
	public Vector2f getPosition()
	{
		return pos;
	}

	@Override
	public boolean isDead()
	{
		return isDead;
	}

	@Override
	public void setDead()
	{
		isDead = true;
	}

	/*
	 * Tags
	 */
	
	public final List<Tag> getTags() { return tags; }

	/**
	 * If entity doesn't have the tag it will add it
	 * @param tag tag
	 */
	public final void addTagIfDoesNotHave(Tag tag)
	{
		if (!hasTag(tag.getName()))
			addTag(tag);
	}
}