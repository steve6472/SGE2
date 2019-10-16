package com.steve6472.sge.gui.components;

/**
 * This class is older than your mother!
 * Please update it!
 * Don't use since it is broken and doesn't renderSprite sprites!
 * Every method crashes the whole program by desing so nobody uses this!
 */
@Deprecated
public class ItemGridList// extends Component
{
	/*
	private static final long serialVersionUID = 3003404793344194226L;
	List<Item> items = new ArrayList<>();
	private List<ChangeEvent> changeEvents = new ArrayList<>();
	int fontSize = 1;
	int visibleItemsX = 4;
	int visibleItemsY = 4;
	int scroll = 0;
	int hovered = 0;
	int selected = 0;
	boolean textureRender = false;
	
	private boolean upEnabled = true, downEnabled = true, upHovered = false, downHovered = false;
	
	/*
	 * If this component is removed the call will stay... fix it please... someday... someone
	 *//*
	@Override
	public void init(MainApp game)
	{
		throw new IllegalCallerException("Don't use ItemGridList!");
	}

	@Event
	public void mouseEvent(MouseEvent event)
	{
		if (event.getAction() == KeyList.PRESS && event.getButton() == KeyList.LMB)
		{
			if (upEnabled && upHovered)
			{
				scroll -= visibleItemsX;
				for (ChangeEvent ce : changeEvents)
				{
					ce.change();
				}
			}
			if (downEnabled && downHovered)
			{
				scroll += visibleItemsX;
				getMouseHandler().setTrigger(true);
				for (ChangeEvent ce : changeEvents)
				{
					ce.change();
				}
			}
		}
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	@Override
	public void renderSprite()
	{
		for (int i = 0; i < visibleItemsX; i++)
		{
			for (int j = 0; j < visibleItemsY; j++)
			{
				int y = i + j * visibleItemsX;
				
				if (!((y + scroll) > (items.size() - 1)))
				{
					renderItem(i, j, y);
				}
			}
		}
		
		int width = getWidth() * getVisibleItemsX();
		// Right "slider"
		UIHelper.renderSingleBorder(getX() + width, getY() + 14, 22, getHeight() * visibleItemsX - 14 * 2, 0xff7f7f7f, 0xff000000);

		UIHelper.renderDoubleBorder(getX() + width, getY(), 22, 14, upEnabled, upHovered);
		
		UIHelper.renderDoubleBorder(getX() + width, getY() + getHeight() * getVisibleItemsY() - 14, 22, 14, downEnabled, downHovered);

		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public void renderItem(int i, int j, int y)
	{
//		getGame().panelList.getPanelById(0).renderSprite(getX() + i * getWindowWidth(), getY() + j * getWindowHeight(), getWindowWidth(), getWindowHeight());
		UIHelper.renderSingleBorder(getX() + i * getHeight(), getY() + j * getHeight(), getWidth(), getHeight(), 0xff3f3f3f, 0xffbfbfbf);

		
		Item item = items.get(i + scroll);
		
		Sprite sprite = item.sprite;

		if (sprite != null)
			if (textureRender)
			{
				glPushMatrix();
				glPushAttrib(GL_CURRENT_BIT);
				glBegin(GL_POINTS);
				Sprite s = items.get((y + scroll)).sprite;
				for (int x = 0; x < s.getWidth(); x++)
				{
					for (int Y = 0; Y < s.getHeight(); Y++)
					{
						color(s.getPixels()[x + Y * s.getWidth()]);
						glVertex2i(x + getX() + 2 + i * getWidth(),
								Y + getY() + j * getHeight() + (getHeight() / 2 - s.getHeight() / 2) + 1);
					}
				}
				glEnd();
				glPopAttrib();
				glPopMatrix();
			} else
			{
//				Screen.drawSprite(getX() + 2 + i * getWidth(),
//						getY() + j * getHeight() + (getHeight() / 2 - items.get(y + scroll).sprite.getHeight() / 2), items.get((y + scroll)).sprite,
//						Screen.getColor(item.getRed(), item.getGreen(), item.getBlue(), item.getAlpha()));
			}

		if (hovered == y)
			Screen.fillRect(getX() + i * getWidth(), getY() + j * getHeight(), getWidth(), getHeight(), Util.HOVERED_OVERLAY);

		if (selected == y + scroll)
			Screen.fillRect(getX() + i * getWidth(), getY() + j * getHeight(), getWidth(), getHeight(), Util.SELECTED_OVERLAY);

		throw new IllegalCallerException("Don't use ItemGridList!");
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
						onMousePressed(KeyList.LMB, (c) ->
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
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public void setSize(int width, int height)
	{
		super.setSize(width / getVisibleItemsX(), height / getVisibleItemsY());
		throw new IllegalCallerException("Don't use ItemGridList!");
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
		if (items.size() == 0)
			return null;
		return items.get(selected);
	}
	
	public void setSelected(int selected)
	{
		this.selected = selected;
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public void setScroll(int scroll)
	{
		this.scroll = scroll;
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public int getScroll()
	{
		return scroll;
	}

	/*
	 * text will be used as tooltip (can be set if you want or don't want the tooltip to show)
	 *//*
	public void addItem(String text, Sprite sprite, int xOffset, int yOffset, float red, float green, float blue, float alpha)
	{
		items.add(new Item(text, sprite, xOffset, yOffset, red, green, blue, alpha));
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public void addItem(String text, Sprite sprite, int color)
	{
		float[] cs = getColors(color);
		addItem(text, sprite, 2, 0, cs[0], cs[1], cs[2], cs[3]);
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public void addItem(String text, int color)
	{
		float[] cs = getColors(color);
		addItem(text, null, 2, 0, cs[0], cs[1], cs[2], cs[3]);
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public void addItem(String text, Sprite sprite, int xOffset, int yOffset, int color)
	{
		float[] cs = getColors(color);
		addItem(text, sprite, xOffset, yOffset, cs[0], cs[1], cs[2], cs[3]);
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public void addItem(String text, int xOffset, int yOffset, int color)
	{
		float[] cs = getColors(color);
		addItem(text, null, xOffset, yOffset, cs[0], cs[1], cs[2], cs[3]);
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public void addItem(String text, Sprite sprite, float red, float green, float blue, float alpha)
	{
		addItem(text, sprite, 2, 0, red, green, blue, alpha);
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public void addItem(String text, float red, float green, float blue, float alpha)
	{
		addItem(text, null, 2, 0, red, green, blue, alpha);
		throw new IllegalCallerException("Don't use ItemGridList!");
	}

	public void addItem(String text, Sprite sprite, int xOffset, int yOffset)
	{
		addItem(text, sprite, xOffset, yOffset, 1f, 1f, 1f, 1f);
		throw new IllegalCallerException("Don't use ItemGridList!");
	}

	public void addItem(String text, int xOffset, int yOffset)
	{
		addItem(text, null, xOffset, yOffset, 1f, 1f, 1f, 1f);
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public void addItem(String text, Sprite sprite)
	{
		addItem(text, sprite, 2, 0);
		throw new IllegalCallerException("Don't use ItemGridList!");
	}

	public void addItem(String text)
	{
		addItem(text, null);
		throw new IllegalCallerException("Don't use ItemGridList!");
	}

	public void addItem(Sprite sprite)
	{
		addItem("", sprite);
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public void removeItem(int index)
	{
		if (index < items.size())
			items.remove(index);
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public void removeAllItems()
	{
		items.clear();
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public void setFontSize(int size)
	{
		this.fontSize = size;
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public void setVisibleItems(int x, int y)
	{
		this.visibleItemsX = x;
		this.visibleItemsY = y;
		throw new IllegalCallerException("Don't use ItemGridList!");
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
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	/**
	 * Renders pixel by pixel... Laggy as hell!
	 * @param textureRender
	 *//*
	public void setTextureRender(boolean textureRender)
	{
		this.textureRender = textureRender;
		throw new IllegalCallerException("Don't use ItemGridList!");
	}
	
	public List<Item> getItems()
	{
		return items;
	}*/
}
