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
	
	public enum ItemType
	{
		SWORD(ItemMotion.SWING),
		BOW(ItemMotion.NULL),
		MISC(ItemMotion.NULL),
		JUNK(ItemMotion.NULL),
		QUEST_ITEM(ItemMotion.NULL),
		STAFF(ItemMotion.SWING),
		GEM(ItemMotion.NULL),
		FOOD(ItemMotion.NULL),
		RING(ItemMotion.NULL),
		BOOT(ItemMotion.NULL),
		SHIELD(ItemMotion.SWING),
		POTION(ItemMotion.NULL),
		POWERUP(ItemMotion.NULL),
		CHESTPLATE(ItemMotion.NULL),
		HELMET(ItemMotion.NULL),
		SKILL(ItemMotion.NULL),
		BOOK(ItemMotion.NULL),
		AXE(ItemMotion.SWING),
		DAGGER(ItemMotion.STAB),
		NECKLACE(ItemMotion.NULL),
		TILE(ItemMotion.NULL),
		AIR(ItemMotion.NULL);
		
		ItemMotion motion;
		
		ItemType(ItemMotion motion)
		{
			this.motion = motion;
		}
	}
	
	public enum ItemMotion
	{
		NULL,
		SWING,
		STAB;
	}
	
	public enum ItemCategory
	{
		ARMOR(""),
		TOOLS(""),
		ACCESSORIES(""),
		WEAPONS(""),
		ITEM(""),
		CRAFTED("Crafted items (By Mobs, NPC or Players)");
		
		String description;
		
		ItemCategory(String description)
		{
			this.description = description;
		}
	}
}
