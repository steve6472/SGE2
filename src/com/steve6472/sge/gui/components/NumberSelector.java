package com.steve6472.sge.gui.components;

@Deprecated

public class NumberSelector// extends Component
{/*
	private static final long serialVersionUID = 6097537167658644896L;
	private int value = 0, max = 16, min = -16;
	private List<ChangeEvent> changeEvents = new ArrayList<>();
	private boolean enabled = true;
	
	private boolean addHovered = false, removeHovered = false, round = false;
	private Vector2f addCenter = new Vector2f(), removeCenter = new Vector2f();
	
	@Override
	public void init(MainApp game)
	{
//		setSize(150, 40);
	}

	@Event
	public void mouseEvent(MouseEvent event)
	{
		if (enabled && event.getAction() == KeyList.PRESS)
		{
			if (removeHovered)
			{
				if (isKeyPressed(KeyList.L_ALT))
				{
					if (getMouseHandler().getButton() == 3)
						removeValue(1000);
					else
						removeValue(100);
				} else if (isKeyPressed(KeyList.L_SHIFT))
				{
					removeValue(10);
				} else if (isKeyPressed(KeyList.L_CONTROL))
				{
					removeValue(5);
				} else
				{
					removeValue();
				}
				for (ChangeEvent ce : changeEvents)
				{
					ce.change();
				}
			}

			if (addHovered)
			{
				if (isKeyPressed(KeyList.L_ALT))
				{
					if (getMouseHandler().getButton() == 3)
						addValue(1000);
					else
						addValue(100);
				} else if (isKeyPressed(KeyList.L_SHIFT))
				{
					addValue(10);
				} else if (isKeyPressed(KeyList.L_CONTROL))
				{
					addValue(5);
				} else
				{
					addValue();
				}
				for (ChangeEvent ce : changeEvents)
				{
					ce.change();
				}
			}
		}
	}

	@Override
	public void tick()
	{
		removeHovered = isCursorInComponent(getX(), getY(), getWidth() / 4, getHeight());

		addHovered = isCursorInComponent(getX() + getWidth() / 4 * 3, getY(), getWidth() / 4, getHeight());
	}

	@Override
	public void render()
	{
		Render.fillRect(getX(), getY(), getWidth(), getHeight(), 0xffff00ff);
		
		UIHelper.renderSingleBorder(getX() + getWidth() / 4, getY(), getWidth() / 2, getHeight(), 0xff7f7f7f, 0xff000000);
		
		UIHelper.renderButton(getX(), getY(), getWidth() / 4, getHeight(), enabled, removeHovered);
		Font.render("-", (int) removeCenter.x(), (int) removeCenter.y());
		
		UIHelper.renderButton(getX() + getWidth() / 4 * 3, getY(), getWidth() / 4, getHeight(), enabled, addHovered);
		Font.render("+", (int) addCenter.x(), (int) addCenter.y());
		
		Font.render("" + value, getX() + getWidth() / 2 - ("" + value).length() * 4, getY() + getHeight () / 2 - 4);
	}

	/*
	 * Operators
	 *//*

	public void addValue(int i)
	{
		value += i;
		if (value > max)
			if (round)
				value = min;
			else
				value = max;
	}
	
	public void addChangeEvent(ChangeEvent ce)
	{
		changeEvents.add(ce);
	}

	public void removeValue(int i)
	{
		value -= i;
		if (value < min)
			if (round)
				value = max;
			else
				value = min;
	}

	public void addValue()
	{
		addValue(1);
	}

	public void removeValue()
	{
		removeValue(1);
	}
	
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		Font.stringCenter(x, y, width / 4, height, " ", 1, removeCenter);
		Font.stringCenter(x + (width / 4 * 3), y, width / 4, height, " ", 1, addCenter);
	}
	
	/*
	 * Setters
	 *//*

	public void setValue(int value)
	{
		this.value = value;
	}
	
	public void setMaxValue(int max)
	{
		this.max = max;
	}
	
	public void setMinValue(int min)
	{
		this.min = min;
	}

	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
		Font.stringCenter(x, y, width / 4, height, " ", 1, removeCenter);
		Font.stringCenter(x + (width / 4 * 3), y, width / 4, height, " ", 1, addCenter);
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public void setRound(boolean round)
	{
		this.round = round;
	}
	
	/*
	 * Getters
	 *//*

	public int getValue()
	{
		return value;
	}
	
	public int getMaxValue()
	{
		return max;
	}
	
	public int getMinValue()
	{
		return min;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public boolean isRound()
	{
		return round;
	}*/
}
