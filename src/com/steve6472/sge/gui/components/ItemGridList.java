package com.steve6472.sge.gui.components;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.RenderHelper;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.Util;

public class ItemGridList extends Component
{
	
	private static final long serialVersionUID = 3003404793344194226L;
	List<Item> items = new ArrayList<Item>();
	private List<ChangeEvent> changeEvents = new ArrayList<ChangeEvent>();
	int fontSize = 1;
	int visibleItemsX = 4;
	int visibleItemsY = 4;
	int scroll = 0;
	int hovered = 0;
	int selected = 0;
	
	private boolean upEnabled = true, downEnabled = true, upHovered = false, downHovered = false;
	
	/*
	 * If this component is removed the call will stay... fix it please... someday... someone
	 */
	@Override
	public void init(MainApplication game)
	{

		getMouseHandler().addMouseButtonCallback((x, y, button, action, mods) ->
		{
			if (action == GLFW_PRESS && upEnabled && upHovered)
			{
				scroll -= visibleItemsX;
				for (ChangeEvent ce : changeEvents)
				{
					ce.change();
				}
			}
		});

		getMouseHandler().addMouseButtonCallback((x, y, button, action, mods) ->
		{
			if (action == GLFW_PRESS && downEnabled && downHovered)
			{
				scroll += visibleItemsX;
				getMouseHandler().setTrigger(true);
				for (ChangeEvent ce : changeEvents)
				{
					ce.change();
				}
			}
		});
	}
	
	@Override
	public void render(Screen screen)
	{
		for (int i = 0; i < visibleItemsX; i++)
		{
			for (int j = 0; j < visibleItemsY; j++)
			{
				int y = i + j * visibleItemsX;
				
				if (!((y + scroll) > (items.size() - 1)))
				{
					renderItem(i, j, y, screen);
				}
			}
		}
		
		int width = getWidth() * getVisibleItemsX();
		// Right "slider"
		RenderHelper.renderSingleBorder(screen, getX() + width, getY() + 14, 22, getHeight() * visibleItemsX - 14 * 2, 0xff7f7f7f, 0xff000000);

		RenderHelper.renderButton(screen, getX() + width, getY(), 22, 14, upEnabled, upHovered);
		
		RenderHelper.renderButton(screen, getX() + width, getY() + getHeight() * getVisibleItemsY() - 14, 22, 14, downEnabled, downHovered);
	}
	
	public void renderItem(int i, int j, int y, Screen screen)
	{
//		getGame().panelList.getPanelById(0).render(getX() + i * getWidth(), getY() + j * getHeight(), getWidth(), getHeight());
		RenderHelper.renderSingleBorder(screen, getX() + i * getHeight(), getY() + j * getHeight(), getWidth(), getHeight(), 0xff3f3f3f, 0xffbfbfbf);

		
		Item item = items.get(i + scroll);
		
		Sprite sprite = item.sprite;

		if (sprite != null)
			screen.drawSprite(getX() + 2 + i * getWidth(),
					getY() + j * getHeight() + (getHeight() / 2 - items.get(y + scroll).sprite.getHeight() / 2), items.get((y + scroll)).sprite,
					Screen.getColor(item.getRed(), item.getGreen(), item.getBlue(), item.getAlpha()));

		if (hovered == y)
			screen.fillRect(getX() + i * getWidth(), getY() + j * getHeight(), getWidth(), getHeight(), Util.HOVERED_OVERLAY);

		if (selected == y + scroll)
			screen.fillRect(getX() + i * getWidth(), getY() + j * getHeight(), getWidth(), getHeight(), Util.SELECTED_OVERLAY);
	}
	
	private int oldHover = -1;

	@Override
	public void tick()
	{
		upHovered = isCursorInComponent(getX() + getWidth() * getVisibleItemsX(), getY(), 22, 14);
		
		downHovered = isCursorInComponent(getX() + getWidth() * getVisibleItemsX(), getY() + getHeight() * getVisibleItemsY() - 14, 22, 14);
		
		if (downHovered)
		{
			downHovered = true;
		} else if (!downHovered)
		{
			downHovered = false;
		} else if (!downHovered)
		{
			downHovered = false;
		}

		upEnabled = scroll > 0;
		downEnabled = scroll < items.size() - visibleItemsY * visibleItemsX;

		hovered = -1;
		for (int i = 0; i < visibleItemsX; i++)
		{
			for (int j = 0; j < visibleItemsY; j++)
			{
				int y = i + j * visibleItemsX;

				if (!((y + scroll) > (items.size() - 1)))
				{
					boolean b = isCursorInComponent(getX() + i * getWidth(), getY() + j * getHeight(), getWidth(), getHeight());
					if (b)
					{
						onMousePressed(b, (c) ->
						{
							if (selected != y + scroll)
							{
								selected = y + scroll;
								for (ChangeEvent ce : changeEvents)
								{
									ce.change();
								}
							}
						});
						hovered = y;
						break;
					}
				}
			}
		}
		if (oldHover != hovered)
		{
			oldHover = hovered;
		}
	}
	
	public void setSize(int width, int height)
	{
		super.setSize(width / getVisibleItemsX(), height / getVisibleItemsY());
	}
	
	public Sprite getSprite(int index)
	{
		return items.get(index).sprite;
	}
	
	public Item getItem(int index)
	{
		return items.get(index);
	}
	
	public Item getSelectedItem()
	{
		return items.get(selected);
	}
	
	public void setSelected(int selected)
	{
		this.selected = selected;
	}
	
	public void setScroll(int scroll)
	{
		this.scroll = scroll;
	}
	
	public int getScroll()
	{
		return scroll;
	}

	/*
	 * text will be used as tooltip (can be set if you want or don't want the tooltip to show)
	 */
	public void addItem(String text, Sprite sprite, int xOffset, int yOffset, float red, float green, float blue, float alpha)
	{
		items.add(new Item(text, sprite, xOffset, yOffset, red, green, blue, alpha));
	}
	
	public void addItem(String text, Sprite sprite, int color)
	{
		float[] cs = Screen.getColors(color);
		addItem(text, sprite, 2, 0, cs[0], cs[1], cs[2], cs[3]);
	}
	
	public void addItem(String text, int color)
	{
		float[] cs = Screen.getColors(color);
		addItem(text, null, 2, 0, cs[0], cs[1], cs[2], cs[3]);
	}
	
	public void addItem(String text, Sprite sprite, int xOffset, int yOffset, int color)
	{
		float[] cs = Screen.getColors(color);
		addItem(text, sprite, xOffset, yOffset, cs[0], cs[1], cs[2], cs[3]);
	}
	
	public void addItem(String text, int xOffset, int yOffset, int color)
	{
		float[] cs = Screen.getColors(color);
		addItem(text, null, xOffset, yOffset, cs[0], cs[1], cs[2], cs[3]);
	}
	
	public void addItem(String text, Sprite sprite, float red, float green, float blue, float alpha)
	{
		addItem(text, sprite, 2, 0, red, green, blue, alpha);
	}
	
	public void addItem(String text, float red, float green, float blue, float alpha)
	{
		addItem(text, null, 2, 0, red, green, blue, alpha);
	}

	public void addItem(String text, Sprite sprite, int xOffset, int yOffset)
	{
		addItem(text, sprite, xOffset, yOffset, 1f, 1f, 1f, 1f);
	}

	public void addItem(String text, int xOffset, int yOffset)
	{
		addItem(text, null, xOffset, yOffset, 1f, 1f, 1f, 1f);
	}
	
	public void addItem(String text, Sprite sprite)
	{
		addItem(text, sprite, 2, 0);
	}

	public void addItem(String text)
	{
		addItem(text, null);
	}
	
	public void removeItem(int index)
	{
		if (index < items.size())
			items.remove(index);
	}
	
	public void removeAllItems()
	{
		items.clear();
	}
	
	public void setFontSize(int size)
	{
		this.fontSize = size;
	}
	
	public void setVisibleItems(int x, int y)
	{
		this.visibleItemsX = x;
		this.visibleItemsY = y;
	}
	
	public int getVisibleItemsX()
	{
		return visibleItemsX;
	}
	
	public int getVisibleItemsY()
	{
		return visibleItemsY;
	}
	
	public int getSelectedIndex()
	{
		return selected;
	}
	
	public void addChangeEvent(ChangeEvent ce)
	{
		changeEvents.add(ce);
	}
	
	public List<Item> getItems()
	{
		return items;
	}
}
