package com.steve6472.sge.gui.components.context;

import com.steve6472.sge.gfx.Render;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 16.03.2019
 * Project: SJP
 *
 ***********************/
public class ContextMenuSeparator extends ContextMenuItem
{
	int height;

	public ContextMenuSeparator()
	{
		height = 11;
	}

	public ContextMenuSeparator(int height)
	{
		this.height = height;
	}

	@Override
	public void tick(int itemY)
	{
	}

	@Override
	public void render(int itemY)
	{
		Render.line_(contextMenu.getX() + 13, contextMenu.getY() + itemY + height / 2 + 1, contextMenu.getX() + contextMenu.getWidth() - 13, contextMenu.getY() + itemY + height / 2 + 1, 0xff909090);
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	@Override
	public int getHeight()
	{
		return height;
	}
}
