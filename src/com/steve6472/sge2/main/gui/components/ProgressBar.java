package com.steve6472.sge2.main.gui.components;

import com.steve6472.sge2.main.MainApplication;
import com.steve6472.sge2.main.gfx.Screen;
import com.steve6472.sge2.main.gui.Component;

public class ProgressBar extends Component
{
	private static final long serialVersionUID = 5988633394949666887L;

	private int value = 0, maxValue = 100, minValue = 0;
	
	/**
	 * The rendered value (Used for smooth animation)
	 */
	private int phantomValue = 0;
	
	/**
	 * Time of animation
	 */
	int time = 0;
//	PanelBase normal;
//	PanelBase filled;
	
	@Override
	public void init(MainApplication game)
	{
//		normal = new ProgressBarBack(getScreen());
//		filled = new ProgressBarType1(getScreen());
//		filled.setMaxRender(getX() + (int) ((0 / 100) * getWidth()), getY() + getWidth(), getX(), getY());
	}

	@Override
	public void render(Screen screen)
	{
//		normal.render(x, y, width, height);
//		filled.render(x, y, width, height);
	}

	@Override
	public void tick()
	{
		if (updateFilled && filled != null)
		{
			updateFilled();
			updateFilled = false;
		}
		
		phantomValue = (int) ((value * Math.max(getWidth(), 1)) / Math.max(getMaxValue(), 1));
		if (phantomValue == value)
			return;
		
		double vP = ((value * getMaxValue()) / getWidth());
		double vPercentage = (vP / (double) getMaxValue()) * (double) getWidth();
		
		double pP = (double) ((double) phantomValue * 100d) / (double) getWidth();
		
//		int add = (int) (Math.cos(Math.toRadians(time)) * 5);
		int add = 1;

		if ((int) pP < (int) vPercentage)
		{
			time++;
			phantomValue += add;
			
			updateFilled();
			repaint();
		}

		if ((int) pP > (int) vPercentage)
		{
			time++;
			phantomValue -= add;
			
			updateFilled();
			repaint();
		}
		
		if ((int) pP == (int) vPercentage)
			time = 0;
		
//		System.out.println((int) pP + " " + (int) vPercentage);

//		double pPercentage = pP;
//		System.out.println(pPercentage + " PV: " + phantomValue + " VP: " + vPercentage);
//		System.out.println((double) ((double) phantomValue * 100d) / (double) getWidth());
	}
	
	int oldValue = 0;
	private boolean updateFilled = false;

	protected void update()
	{
		if (oldValue == value)
			return;
		
//		if (value < oldValue)
//		{
//			phantomValue = (int) ((value * getWidth()) / getMaxValue());
//		}
		
		oldValue = value;

		if (filled == null)
		{
			updateFilled = true;
		} else
		{
			updateFilled();
		}
	}
	
	private void updateFilled()
	{	
//		double max = getMaxValue();
//		double max = getWidth();

//		int percent = (int) ((value * max) / getMaxValue());
//		int percent = (int) ((phantomValue * max) / getMaxValue());
		
//		filled.setMaxRender(getX() + (int) ((percent / max) * getWidth()), getY() + getWidth(), getX(), getY());
		
		phantomValue = Math.min(getWidth(), phantomValue);
		
//		filled.setMaxRender(getX() + phantomValue, getY() + getWidth(), getX(), getY());
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
		update();
	}
	
	public void setMaxValue(int maxValue)
	{
		this.maxValue = maxValue;
		update();
	}
	
	public void setMinValue(int minValue)
	{
		this.minValue = minValue;
		update();
	}
	
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		update();
	}
	
	@Override
	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
		update();
	}
	
	public void forceRepaint()
	{
		updateFilled();
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
	protected int getMinHeight()
	{
		return 10;
	}
	
	@Override
	protected int getMinWidth()
	{
		return 10;
	}

}
