package com.steve6472.sge.gui.components.context;

import com.steve6472.sge.gfx.Render;
import com.steve6472.sge.gfx.SpriteRender;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.MainApp;
import com.steve6472.sge.test.Fex;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 16.03.2019
 * Project: SGE2
 *
 ***********************/
public class ContextMenu extends Component
{
	public List<ContextMenuItem> menuItems;
	public boolean darkenGui = true;
	public boolean spoofCursor = false;
	public boolean canClose = true;
	public int mx, my;

	public ContextMenu()
	{
		menuItems = new ArrayList<>();
	}

	@Override
	public void init(MainApp main)
	{
		setVisible(false);
	}

	@Override
	public void tick()
	{
		if (getMain().getMouseHandler().getButton() != -1 && !isCursorInComponent() && canClose)
		{
			hide();
			return;
		}

		if (isCursorInComponent())
		{
			spoofCursor = true;
			getMain().getEventHandler().addSpecialCaseObject(this);
		} else
		{
			getMain().getEventHandler().removeSpecialCaseObject(this);
			spoofCursor = false;
		}

		int y = 0;
		for (ContextMenuItem menuItem : menuItems)
		{
			menuItem.tick(y);
			y += menuItem.getHeight();
		}
	}

	@Override
	public void render()
	{
		if (darkenGui)
			Render.fillRect(0, 0, getMain().getWidth(), getMain().getHeight(), 0x80101010);

//		UIHelper.renderSingleBorder(x, y, width, height, 0xff000000, 0xff404040);
		SpriteRender.renderSingleBorder(x, y, width, height, 0, 0, 0, 1, Fex.H40, Fex.H40, Fex.H40, Fex.Hff);
		int y = 0;
		for (ContextMenuItem menuItem : menuItems)
		{
			menuItem.render(y);
			y += menuItem.getHeight();
		}
	}

	public void add(ContextMenuItem contextMenuItem)
	{
		contextMenuItem.preInit(this);
		menuItems.add(contextMenuItem);
		height = 0;
		menuItems.forEach(c -> height += c.getHeight());
		height += 4;
	}

	public void addSeparator()
	{
		add(new ContextMenuSeparator());
	}

	public void addSeparator(int height)
	{
		add(new ContextMenuSeparator(height));
	}

	public void setSize(int width)
	{
		this.width = width;
	}

	public void show(int x, int y)
	{
		show(x, y, x, y);
	}

	public void show(int x, int y, int mouseX, int mouseY)
	{
		if (isVisible())
			return;

		setLocation(x, y);

		getMain().getEventHandler().addSpecialCaseObject(this);

		this.mx = mouseX;
		this.my = mouseY;

		setVisible(true);
	}

	public void hide()
	{
		if (!isVisible())
			return;

		getMain().getEventHandler().removeSpecialCaseObject(this);
		spoofCursor = false;

		setVisible(false);
	}

	public boolean isCursorInComponent()
	{
		if (!isVisible())
			return false;
		return (getMouseX() >= getX() && getMouseX() <= getWidth() + getX())
				&& (getMouseY() >= getY() && getMouseY() <= getHeight() + getY());
	}
}
