package com.steve6472.sge.gui.components;

import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.RenderHelper;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.MainApplication;

public class Slider extends Component
{
	private static final long serialVersionUID = 4164297008969991704L;
	
	private int value = 0, maxValue = 100, minValue = 0, privateX;

	protected List<ChangeEvent> changeEvent = new ArrayList<ChangeEvent>();
	
	@Override
	public void init(MainApplication game)
	{
		recalculate();
	}

	@Override
	public void render(Screen screen)
	{
		RenderHelper.renderDoubleBorderComponent(this, 0xff000000, 0xff606060, 0xff3f3f3f);

		RenderHelper.renderButton(moveX, getY() - 8, 32, 48, true, selected);
		
	}

//	protected void sliderChange()
//	{
//		privateX = getX() - getMouseHandler().getMouseX(); //mouse pos relativly to component
//		
//		changeEvent.forEach((ce) -> ce.change());
//	}
	
	private boolean setted = false;
	private int oldValue = 0;
	private int moveX = 0;
	private boolean selected = false;
	private boolean flag0 = false;
	
	@Override
	public void tick()
	{
		tickComponents();
		
		if (!getMouseHandler().isMouseHolded() && flag0)
		{
			flag0 = false;
			selected = false;
		}
		
		if (getMouseHandler().isMouseHolded())
		{
			if (!flag0)
			{
				flag0 = true;
				selected = isCursorInComponent(getX(), getY(), getWidth(), getHeight()) || isCursorInComponent(moveX, getY() - 8, 32, 48);
			}
		}

		if (!selected)
			return;
		
		if (!setted)
		{
			if (getMouseHandler().isMouseHolded())
				privateX = getX() - getMouseHandler().getMouseX();
		} else
		{
			setted = false;
		}
		
		float max = (float) getMaxValue();

		int valueBuffer = -((int) ((privateX * max) / getWidth())); //Some weird shit
		
//		int oldValue = getValue();
		
		if (valueBuffer > getMaxValue())
		{
			value = getMaxValue();
		} else if (valueBuffer < 0)
		{
			value = 0;
		} else
		{
			value = valueBuffer;
		}
		
//		if (oldValue != getValue())
//		{
//			slider.setToolTipText("Value:" + getValue());
//		}
		
		moveX = getX() - 16 - privateX; //Center to the slider

		if (privateX + 8 > 0)
			moveX = getX() - 8;

		if (privateX - 8 < (-getWidth()))
			moveX = getX() + getWidth() - 24;

		if (this.oldValue != value)
		{
			this.oldValue = value;
			changeEvent.forEach(ce -> ce.change());
		}
	}
	
	public void recalculate()
	{
		privateX = -((int) ((value * getWidth()) / getMaxValue()));
		
		float max = (float) getMaxValue();

		int valueBuffer = -((int) ((privateX * max) / getWidth())); //Some weird shit
		
//		int oldValue = getValue();
		
		if (valueBuffer > getMaxValue())
		{
			value = getMaxValue();
		} else if (valueBuffer < 0)
		{
			value = 0;
		} else
		{
			value = valueBuffer;
		}
		
		moveX = getX() - 16 - privateX; //Center to the slider

		if (privateX + 8 > 0)
			moveX = getX() - 8;

		if (privateX - 8 < (-getWidth()))
			moveX = getX() + getWidth() - 24;
	}
	
	/*
	 * Operators
	 */
	
	/*
	 * Setters
	 */
	
	public void setValue(int value)
	{
		this.value = value;
		setted = true;
		recalculate();
	}

	public void setMaxValue(int maxValue)
	{
		this.maxValue = maxValue;
		
		if (value > maxValue)
			setValue(maxValue);
	}

	public void setMinValue(int minValue)
	{
		this.minValue = minValue;
	}
	
	public void setSize(int width)
	{
		if (width < 64)
			width = 64;
		super.setSize(width, 32);
	}
	
	@Override
	public void setSize(int width, int height)
	{
		setSize(width);
		recalculate();
	}
	
	@Override
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
		recalculate();
	}
	
	public void addChangeEvent(ChangeEvent ce)
	{
		changeEvent.add(ce);
	}
	
	/*
	 * Getters
	 */
	
	public int getValue()
	{
		return value;
	}
	
	public int getMaxValue()
	{
		return maxValue;
	}
	
	public int getMinValue()
	{
		return minValue;
	}
	
	@Override
	protected int getMinWidth()
	{
		return 64;
	}
	
	@Override
	protected int getMinHeight()
	{
		return 32;
	}
}


