package steve6472.sge.main.game.inventory;

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
	AXE(ItemMotion.CHOP),
	PICKAXE(ItemMotion.MINE),
	DAGGER(ItemMotion.STAB),
	NECKLACE(ItemMotion.NULL),
	TILE(ItemMotion.PLACE),
	AIR(ItemMotion.NULL),
	RESOURCE(ItemMotion.NULL);

	ItemMotion motion;

	ItemType(ItemMotion motion)
	{
		this.motion = motion;
	}

	public ItemMotion getMotion()
	{
		return motion;
	}
}