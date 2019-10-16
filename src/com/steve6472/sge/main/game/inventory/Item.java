/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 10. 2017
* Project: SGG
*
***********************/

package com.steve6472.sge.main.game.inventory;

import java.io.Serializable;

public class Item implements Serializable
{
	private static final long serialVersionUID = 2335588895934903724L;

	public static final Item AIR = new Item();
	
	public ItemType itemType;
	public ItemCategory itemCategory;
}
