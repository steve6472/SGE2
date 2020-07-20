package steve6472.sge.main.game.inventory;

public enum ItemCategory
{
	ARMOR(""),
	TOOLS(""),
	ACCESSORIES(""),
	WEAPONS(""),
	ITEM(""),
	RESOURCES(""),
	CRAFTED("Crafted items (By Mobs, NPC or Players)");

	String description;

	ItemCategory(String description)
	{
		this.description = description;
	}
}