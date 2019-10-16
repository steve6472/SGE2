package com.steve6472.sge.gui.components.context;

import com.steve6472.sge.gfx.font.CustomChar;
import com.steve6472.sge.gfx.font.Font;
import com.steve6472.sge.gfx.Render;

import java.util.Objects;
import java.util.function.Consumer;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 16.03.2019
 * Project: SJP
 *
 ***********************/
public abstract class ContextMenuItem
{
	public ContextMenu contextMenu;
	public String name;
	public Object image;
	private boolean isEnabled = true;
	private boolean clickFlag;

	final void preInit(ContextMenu contextMenu)
	{
		this.contextMenu = contextMenu;
	}

	protected void renderHighlight(int itemY)
	{
		if (!isEnabled)
			return;

		int x = contextMenu.getX();
		int y = contextMenu.getY();

		Render.fillRect(x + 2, y + itemY + 2, contextMenu.getWidth() - 4, 20, 0xff909090);
		Render.startLines();
		Render.color(0xff707070);
		Render.line(x + 2, y + itemY + 2, x + 2 + contextMenu.getWidth() - 4, y + itemY + 3);
		Render.line(x + 2, y + itemY + 22, x + 2 + contextMenu.getWidth() - 4, y + itemY + 23);
		Render.line(x + 21, y + itemY + 3, x + 21, y + itemY + 22);
		Render.endLines();
	}

	public boolean isCursorInItem(int itemY)
	{
		if (!isEnabled)
			return false;

		int x = contextMenu.getX() + 2;
		int y = contextMenu.getY() + itemY + 2;
		int w = contextMenu.getWidth() - 4;
		int h = 20;
		int mx = contextMenu.getMouseHandler().getMouseX();
		int my = contextMenu.getMouseHandler().getMouseY();
		return (mx >= x && mx <= w + x) && (my >= y && my <= h + y);
	}

	protected final void onMouseClicked(int itemY, int button, final Consumer<ContextMenuItem> action)
	{
		Objects.requireNonNull(action);
		if (isCursorInItem(itemY))
		{
			if (!clickFlag)
			{
				if (contextMenu.getMain().getMouseHandler().getButton() == button)
				{
					clickFlag = true;
				}
			} else
			{
				if (contextMenu.getMain().getMouseHandler().getButton() != button)
				{
					clickFlag = false;
					action.accept(this);
				}
			}
		} else
		{
			clickFlag = false;
		}
	}

	protected void renderName(int itemY)
	{
		Font.render(name, contextMenu.getX() + 24, contextMenu.getY() + 8 + itemY);
	}

	protected void renderImage(int itemY)
	{
		if (image instanceof CustomChar)
			Font.renderCustom(contextMenu.getX() + 7, contextMenu.getY() + 8 + itemY, 1, image);
		else
			Font.render("" + (image == null ? "" : image), contextMenu.getX() + 7, contextMenu.getY() + 8 + itemY);
	}

	public abstract void tick(int index);

	public abstract void render(int index);

	public abstract int getHeight();

	public void setImage(char image)
	{
		this.image = image;
	}

	public void setImage(CustomChar image)
	{
		this.image = image;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public boolean isEnabled()
	{
		return isEnabled;
	}

	public void setEnabled(boolean enabled)
	{
		isEnabled = enabled;
	}
}
