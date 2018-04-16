/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 17. 12. 2017
* Project: SGG
*
***********************/

package com.steve6472.sge.main.game.inventory;

import java.io.Serializable;

public class ItemSlot implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name;
	private int id;
	private Item item = Item.AIR;
	
	public ItemSlot(String name, int id)
	{
		this.id = id;
		this.name = name;
	}

	public ItemSlot(String name, int id, Item item)
	{
		this.id = id;
		this.item = item;
		this.name = name;
	}

	/*
	 * Setting item into slot
	 */
	
	public void setItem(Item item)
	{
		if (item == null)
		{
			this.item = Item.AIR;
			return;
		}

		this.item = item;
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

}
