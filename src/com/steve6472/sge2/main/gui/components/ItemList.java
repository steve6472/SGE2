package com.steve6472.sge2.main.gui.components;

import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge2.main.MainApplication;
import com.steve6472.sge2.main.gfx.Font;
import com.steve6472.sge2.main.gfx.Screen;
import com.steve6472.sge2.main.gfx.Sprite;
import com.steve6472.sge2.main.gui.Component;

public class ItemList extends Component
{

	private static final long serialVersionUID = 4821451061681776222L;
	protected List<Item> items = new ArrayList<Item>();
	private List<ChangeEvent> changeEvents = new ArrayList<ChangeEvent>();
	protected int fontSize = 1;
	protected int visibleItems = 4;
	protected int scroll = 0;
	protected int hovered = 0;
	protected int selected = 0;

	private boolean upEnabled = true, downEnabled = true, upHovered = false, downHovered = false;

	@Override
	public void init(MainApplication game)
	{
		
	}

	@Override
	public void render(Screen screen)
	{
		for (int i = 0; i < visibleItems; i++)
		{
			if (!((i + scroll) > (items.size() - 1)))
			{
				renderItem(i, screen);
			}
		}
		// Right "slider"
		RenderHelper.renderSingleBorder(screen, getX() + getWidth() - 22, getY() + 14, 22, getHeight() * visibleItems - 14 * 2, 0xff7f7f7f, 0xff000000);

		// Up button
		if (upEnabled && upHovered)
		{
			RenderHelper.renderDoubleBorder(screen, getX() + getWidth() - 22, getY(), 22, 14, RenderHelper.BUTTON_HOVERED_COLORS[0],
					RenderHelper.BUTTON_HOVERED_COLORS[1], RenderHelper.BUTTON_HOVERED_COLORS[2]);
		} else if (upEnabled && !upHovered)
		{
			RenderHelper.renderDoubleBorder(screen, getX() + getWidth() - 22, getY(), 22, 14, RenderHelper.BUTTON_ENABLED_COLORS[0],
					RenderHelper.BUTTON_ENABLED_COLORS[1], RenderHelper.BUTTON_ENABLED_COLORS[2]);
		} else if (!upEnabled)
		{
			RenderHelper.renderDoubleBorder(screen, getX() + getWidth() - 22, getY(), 22, 14, RenderHelper.BUTTON_DISABLED_COLORS[0],
					RenderHelper.BUTTON_DISABLED_COLORS[1], RenderHelper.BUTTON_DISABLED_COLORS[2]);
		}

		// Down button
		if (downEnabled && downHovered)
		{
			RenderHelper.renderDoubleBorder(screen, getX() + getWidth() - 22, getY() + getHeight() * getVisibleItems() - 14, 22, 14,
					RenderHelper.BUTTON_HOVERED_COLORS[0], RenderHelper.BUTTON_HOVERED_COLORS[1], RenderHelper.BUTTON_HOVERED_COLORS[2]);
		} else if (downEnabled && !downHovered)
		{
			RenderHelper.renderDoubleBorder(screen, getX() + getWidth() - 22, getY() + getHeight() * getVisibleItems() - 14, 22, 14,
					RenderHelper.BUTTON_ENABLED_COLORS[0], RenderHelper.BUTTON_ENABLED_COLORS[1], RenderHelper.BUTTON_ENABLED_COLORS[2]);
		} else if (!downEnabled)
		{
			RenderHelper.renderDoubleBorder(screen, getX() + getWidth() - 22, getY() + getHeight() * getVisibleItems() - 14, 22, 14,
					RenderHelper.BUTTON_DISABLED_COLORS[0], RenderHelper.BUTTON_DISABLED_COLORS[1], RenderHelper.BUTTON_DISABLED_COLORS[2]);
		}
	}
	
	public void renderItem(int i, Screen screen)
	{
		RenderHelper.renderSingleBorder(screen, getX(), getY() + i * getHeight(), getWidth() - 22, getHeight(), 0xff3f3f3f, 0xffbfbfbf);

		if (hovered == i)
			screen.fillRect(getX(), getY() + i * getHeight(), getWidth() - 22, getHeight(), 0x80777777);

		if (selected == i + scroll)
			screen.fillRect(getX(), getY() + i * getHeight(), getWidth() - 22, getHeight(), 0x80555555);

		renderText(screen, i);
		
		Item item = items.get(i + scroll);
		
		Sprite sprite = item.sprite;
		
		if (sprite == null)
			return;

		screen.drawSprite(getX() + item.xOffset, getY() + i * getHeight() + (getHeight() / 2 - sprite.getHeight() / 2 + item.yOffset),
				sprite);
	}

	private int oldHover = -1;

	@Override
	public void tick()
	{
		upHovered = isCursorInComponent(getX() + getWidth() - 22, getY(), 22, 14);

		downHovered = isCursorInComponent(getX() + getWidth() - 22, getY() + getHeight() * getVisibleItems() - 14, 22, 14);
		
		onMouseClicked(upEnabled && upHovered, (c) ->
		{
			scroll--;
			getMouseHandler().setTrigger(true);
		});

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
		
		onMouseClicked(downHovered && downEnabled, (c) ->
		{
			scroll++;
			getMouseHandler().setTrigger(true);
		});

		upEnabled = scroll > 0;
		downEnabled = scroll < items.size() - visibleItems;

		hovered = -1;
		for (int i = 0; i < visibleItems; i++)
		{
			if (!((i + scroll) > (items.size() - 1)))
			{
				int awbud = i;
				boolean b = isCursorInComponent(getX(), getY() + i * getHeight(), getWidth() - 22, getHeight());
				if (b)
				{
					onMousePressed(b, (c) -> 
					{
						if (selected != awbud + scroll)
						{
							selected = awbud + scroll;
							for (ChangeEvent ce : changeEvents)
							{
								ce.change();
							}
						}
					});
					hovered = i;
					break;
				}
			}
		}
		if (oldHover != hovered)
		{
			oldHover = hovered;
		}
	}

	protected void renderText(Screen screen, int index)
	{
		Item item = items.get(index + scroll);
		if (item.sprite != null)
			getFont().render(Font.trim(items.get((index + scroll)).text, width - 34, fontSize),
					items.get(index + scroll).sprite.getWidth() + getX() + 4 + item.xOffset, getY() + getHeight() / 2 - 8 / 2 + index * getHeight(),
					fontSize, item.red, item.green, item.blue);
		else
			getFont().render(Font.trim(items.get((index + scroll)).text, width - 34, fontSize), getX() + 4,
					getY() + getHeight() / 2 - 8 / 2 + index * getHeight(), fontSize, item.red, item.green, item.blue);
	}

	public void setSize(int width, int height)
	{
		super.setSize(width, height / visibleItems);
	}

	public Sprite getSprite(int index)
	{
		return items.get(index).sprite;
	}

	public List<Item> getItems()
	{
		return items;
	}

	public void addItem(String text, Sprite sprite, int xOffset, int yOffset, float red, float green, float blue)
	{
		items.add(new Item(text, sprite, xOffset, yOffset, red, green, blue));
	}
	
	public void addItem(String text, Sprite sprite, int color)
	{
		float[] cs = Screen.getColors(color);
		addItem(text, sprite, 2, 0, cs[0], cs[1], cs[2]);
	}
	
	public void addItem(String text, int color)
	{
		float[] cs = Screen.getColors(color);
		addItem(text, null, 2, 0, cs[0], cs[1], cs[2]);
	}
	
	public void addItem(String text, Sprite sprite, int xOffset, int yOffset, int color)
	{
		float[] cs = Screen.getColors(color);
		addItem(text, sprite, xOffset, yOffset, cs[0], cs[1], cs[2]);
	}
	
	public void addItem(String text, int xOffset, int yOffset, int color)
	{
		float[] cs = Screen.getColors(color);
		addItem(text, null, xOffset, yOffset, cs[0], cs[1], cs[2]);
	}
	
	public void addItem(String text, Sprite sprite, float red, float green, float blue)
	{
		addItem(text, sprite, 2, 0, red, green, blue);
	}
	
	public void addItem(String text, float red, float green, float blue)
	{
		addItem(text, null, 2, 0, red, green, blue);
	}

	public void addItem(String text, Sprite sprite, int xOffset, int yOffset)
	{
		addItem(text, sprite, xOffset, yOffset, 1f, 1f, 1f);
	}

	public void addItem(String text, int xOffset, int yOffset)
	{
		addItem(text, null, xOffset, yOffset, 1f, 1f, 1f);
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
		if (index <= items.size())
			items.remove(index);
	}

	public void clear()
	{
		items.clear();
	}

	public void setSelectedItem(int index)
	{
		selected = index < items.size() ? index : items.size() - 1;
	}

	public void setFontSize(int size)
	{
		this.fontSize = size;
	}

	public void setVisibleItems(int i)
	{
		this.visibleItems = i;
	}

	public int getVisibleItems()
	{
		return visibleItems;
	}

	public String getSelectedItem()
	{
		return items.get(selected).text;
	}

	public int getSelectedIndex()
	{
		return selected;
	}

	public void addChangeEvent(ChangeEvent ce)
	{
		changeEvents.add(ce);
	}

	public void fireChangeEvent()
	{
		for (ChangeEvent ce : changeEvents)
		{
			ce.change();
		}
	}

	public class Item
	{
		protected String text = "";
		protected Sprite sprite;
		int xOffset = 2;
		int yOffset = 0;
		float red, green, blue;
		
		private Item(String text, Sprite sprite, int xOffset, int yOffset, float red, float green, float blue)
		{
			this.text = text;
			this.sprite = sprite;
			this.xOffset = xOffset;
			this.yOffset = yOffset;
			this.red = red;
			this.green = green;
			this.blue = blue;
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
		
		public int getxOffset()
		{
			return xOffset;
		}
		
		public int getyOffset()
		{
			return yOffset;
		}
	}

}
