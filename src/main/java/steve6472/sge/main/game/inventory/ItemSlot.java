/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 17. 12. 2017
* Project: SGG
*
***********************/

package steve6472.sge.main.game.inventory;

import java.io.Serializable;

public class ItemSlot implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name;
	private int id;
	private int count;
	private Item item = Item.AIR;
	
	public ItemSlot(String name, int id)
	{
		this.id = id;
		this.name = name;
		this.count = 1;
	}

	public ItemSlot(String name, int id, Item item, int count)
	{
		this.id = id;
		this.item = item;
		this.name = name;
		this.count = count;
	}

	public ItemSlot(String name, int id, Item item)
	{
		this(name, id, item, 1);
	}

	/*
	 * Setting item into slot
	 */
	
	public void setItem(Item item, int count)
	{
		if (item == null)
		{
			this.count = count;
			this.item = Item.AIR;
			return;
		}
		this.count = count;
		this.item = item;
	}

	public void setItem(Item item)
	{
		setItem(item, 1);
	}
	
	/*
	 * Getting item from the slot
	 */
	
	public final Item getItem()
	{
		return item;
	}
	
	/*
	 * Getting information about slot
	 */
	
	public final int getId()
	{
		return id;
	}
	
	public final String getName()
	{
		return name;
	}

	public final int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}
}
