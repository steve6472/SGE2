package com.steve6472.sge.gui.components;


import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.MainApplication;

public class Image extends Component
{
	private static final long serialVersionUID = 2057497336678192459L;
	private Sprite sprite;
	private boolean repeat = false;
	
	public Image(Sprite sprite)
	{
		this.sprite = sprite;
	}
	
	@Override
	public void init(MainApplication game)
	{
	}

	@Override
	public void render(Screen screen)
	{
		if (sprite != null)
		{
			if (getWidth() == 0 && getHeight() == 0)
				Screen.drawSprite(x, y, sprite);
			else if (repeat)
				Screen.drawSpriteRepeat(x, y, sprite, getWidth(), getHeight());
			else
				Screen.drawSprite(x, y, sprite, getWidth(), getHeight());
		}
	}

	/*
	 * Operators
	 */
	
	/*
	 * Setters
	 */

//	public void setLimitedRender(int maxx, int maxy, int minx, int miny)
//	{
//		this.maxx = maxx;
//		this.maxy = maxy;
//		this.minx = minx;
//		this.miny = miny;
//		setLimitedRender = true;
//	}
	
	/**
	 * If is set to repeat mode ->
	 * Width acts like X count
	 * Height acts like Y count
	 */
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
	}

	public Image setImage(Sprite sprite)
	{
		this.sprite = sprite;
		return this;
	}
	
	public void setRepeat(boolean repeat)
	{
		this.repeat = repeat;
	}
	
	@Override
	public void tick()
	{
	}
	
	/*
	 * Getters
	 */
	
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public boolean isRepeat()
	{
		return repeat;
	}
	
}
