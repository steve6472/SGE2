/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 17. 12. 2017
* Project: SGG
*
***********************/

package com.steve6472.sge.main.game.inventory;

import java.lang.reflect.InvocationTargetException;

import com.steve6472.sge.main.SGArray;
import com.steve6472.sge.main.game.BaseEntity;

public class Inventory
{
	/**
	 * The entity this inventory belongs to
	 */
	protected BaseEntity entity;
	
	/*
	 * This should allow me to add and remove slots on the run!
	 */
	SGArray<ItemSlot> slots;
	
	public Inventory(BaseEntity entity, Class<? extends ItemSlot> clazz, String... slotNames)
	{
		this.entity = entity;
		
		slots = new SGArray<ItemSlot>(slotNames.length, true, false);
		
		for (int i = 0; i < slotNames.length; i++)
		{
			try
			{
				slots.setObject(i, clazz.getConstructor(String.class, int.class).newInstance(slotNames[i], i));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public Inventory(BaseEntity entity, Class<? extends ItemSlot> clazz, int initialSlotCount)
	{
		this.entity = entity;
		
		slots = new SGArray<ItemSlot>(initialSlotCount, true, false);
		
		for (int i = 0; i < initialSlotCount; i++)
		{
			try
			{
				slots.setObject(i, clazz.getConstructor(String.class, int.class).newInstance("" + i, i));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Setting items
	 */
	
	public final void setItem(String slotName, Item item)
	{
		slots.getObject(getSlotId(slotName)).setItem(item);
	}
	
	public final void setItem(int slotId, Item item)
	{
		slots.getObject(slotId).setItem(item);
	}
	
	/*
	 * Getting items from inventory
	 */
	
	public final Item getItem(String slotName)
	{
		int slotId = getSlotId(slotName);
		
		if (slotId == -1)
				throw new NullPointerException(slotName + " doesn't exits!");
		
		return slots.getObject(slotId).getItem();
//		
	}

	public final Item getItem(int slotId)
	{
		if (slotId < 0)
			return Item.AIR;
		if (slotId > slots.getSize())
			return Item.AIR;

		return slots.getObject(slotId).getItem();
	}
	
	public final Item[] getItems()
	{
		Item[] items = new Item[slots.getSize()];
		
		for (int i = 0; i < slots.getSize(); i++)
		{
			items[i] = slots.getObject(i).getItem();
		}
		return items;
	}
	
	public final SGArray<ItemSlot> getSlots()
	{
		return slots;
	}
	
	/**
	 * 
	 * @param slotName
	 * @return -1 if {@code slotName} doesn't exist
	 */
	public final int getSlotId(String slotName)
	{
		for (ItemSlot is : slots)
			if (is.getName().equals(slotName))
				return is.getId();
		
		return -1;
	}
	
	public final ItemSlot getSlot(int id)
	{
		return slots.getObject(id);
	}
}