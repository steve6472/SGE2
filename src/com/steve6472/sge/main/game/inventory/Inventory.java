/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 17. 12. 2017
* Project: SGG
*
***********************/

package com.steve6472.sge.main.game.inventory;

import com.steve6472.sge.main.game.BaseEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Inventory<T extends ItemSlot>
{
	/**
	 * The entity this inventory belongs to
	 */
	protected BaseEntity entity;
	
	/*
	 * This should allow me to add and remove slots on the run!
	 */
	List<T> slots;
	
	private Item nullItem = Item.AIR;
	
	@SuppressWarnings("unchecked")
	public Inventory(BaseEntity entity, Class<? extends ItemSlot> clazz, Item nullItem, String... slotNames)
	{
		this.entity = entity;
		
		this.nullItem = nullItem;
		
		slots = new ArrayList<>(slotNames.length);
		
		for (int i = 0; i < slotNames.length; i++)
		{
			try
			{
				slots.set(i, (T) clazz.getConstructor(String.class, int.class).newInstance(slotNames[i], i));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public Inventory(BaseEntity entity, Class<? extends ItemSlot> clazz, String... slotNames)
	{
		this(entity, clazz, Item.AIR, slotNames);
	}
	
	@SuppressWarnings("unchecked")
	public Inventory(BaseEntity entity, Class<? extends ItemSlot> clazz, Item nullItem, int initialSlotCount)
	{
		this.entity = entity;
		
		this.nullItem = nullItem;
		
		slots = new ArrayList<T>(initialSlotCount);
		
		for (int i = 0; i < initialSlotCount; i++)
		{
			try
			{
				slots.set(i, (T) clazz.getConstructor(String.class, int.class).newInstance("" + i, i));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public Inventory(BaseEntity entity, Class<? extends ItemSlot> clazz, int initialSlotCount)
	{
		this(entity, clazz, Item.AIR, initialSlotCount);
	}

	public void swapItems(int slotIdFrom, int slotIdTo)
	{
		Item item = getItem(slotIdTo);
		setItem(slotIdTo, getItem(slotIdFrom));
		setItem(slotIdFrom, item);

		int count = getCount(slotIdTo);
		getSlot(slotIdTo).setCount(getCount(slotIdFrom));
		getSlot(slotIdFrom).setCount(count);
	}

	public void addItem(Item item, int count)
	{
		int firstEmpty = -1;
		int itemSlot = -1;
		for (int k = 0; k < 20; k++)
		{
			if (getItem(k) == item && firstEmpty == -1)
				firstEmpty = k;
			if (getItem(k) == item && itemSlot == -1)
				itemSlot = k;
		}
		if (itemSlot != -1)
		{
			getSlot(itemSlot).setCount(getSlot(itemSlot).getCount() + count);
		} else
		{
			getSlot(firstEmpty).setCount(getSlot(firstEmpty).getCount() + count);
			getSlot(firstEmpty).setItem(item);
		}
	}
	
	/*
	 * Setting items
	 */
	
	public final void setItem(String slotName, Item item)
	{
		slots.get(getSlotId(slotName)).setItem(item);
	}
	
	public final void setItem(int slotId, Item item)
	{
		slots.get(slotId).setItem(item);
	}
	
	/*
	 * Getting items from inventory
	 */
	
	public final Item getItem(String slotName)
	{
		int slotId = getSlotId(slotName);
		
		if (slotId == -1)
				throw new NullPointerException(slotName + " doesn't exits!");
		
		return slots.get(slotId).getItem();
//		
	}

	public final Item getItem(int slotId)
	{
		if (slotId < 0)
			return Item.AIR;
		if (slotId > slots.size())
			return Item.AIR;

		return slots.get(slotId).getItem();
	}

	public final int getCount(int slotId)
	{
		return getSlot(slotId).getCount();
	}
	
	public final Item[] getItems()
	{
		Item[] items = new Item[slots.size()];
		
		for (int i = 0; i < slots.size(); i++)
		{
			items[i] = slots.get(i).getItem();
		}
		return items;
	}

	public final List<T> getSlots()
	{
		return slots;
	}
	
	/**
	 * 
	 * @param slotName slot name
	 * @return -1 if {@code slotName} doesn't exist
	 */
	public final int getSlotId(String slotName)
	{
		for (ItemSlot is : slots)
			if (is.getName().equals(slotName))
				return is.getId();
		
		return -1;
	}
	
	public final T getSlot(int id)
	{
		return slots.get(id);
	}
}
