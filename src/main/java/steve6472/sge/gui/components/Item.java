package steve6472.sge.gui.components;

import steve6472.sge.gfx.Sprite;

public class Item
{
	protected String text = "";
	protected Sprite sprite;
	int xOffset = 2;
	int yOffset = 0;
	float red, green, blue, alpha;
	
	public Item(String text, Sprite sprite, int xOffset, int yOffset, float red, float green, float blue, float alpha)
	{
		this.text = text;
		this.sprite = sprite;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}

	public String getText()
	{
		return text;
	}
	
	public float getRed()
	{
		return red;
	}
	
	public float getGreen()
	{
		return green;
	}
	
	public float getBlue()
	{
		return blue;
	}
	
	public float getAlpha()
	{
		return alpha;
	}
	
	public int getxOffset()
	{
		return xOffset;
	}
	
	public int getyOffset()
	{
		return yOffset;
	}
}