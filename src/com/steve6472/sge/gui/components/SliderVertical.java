package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.SpriteRender;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.schemes.Scheme;
import com.steve6472.sge.gui.components.schemes.SchemeSlider;
import com.steve6472.sge.main.MainApp;
import com.steve6472.sge.main.events.Event;
import com.steve6472.sge.main.events.ScrollEvent;

import java.util.ArrayList;
import java.util.List;

public class SliderVertical extends Component
{
	private static final long serialVersionUID = 4164297008969991704L;
	
	private double value = 0, maxValue = 100, minValue = 0, privateY;
	public boolean autoInt = false;
	private int bWidth = 32, bHeight = 48;

	protected List<ChangeEvent> changeEvent = new ArrayList<>();

	public SchemeSlider scheme;

	private boolean setted = false;
	private double oldValue = 0;
	private int moveY = 0;
	protected boolean hovered = false;
	protected boolean enabled = true;
	private boolean flag0 = false;
	
	@Override
	public void init(MainApp main)
	{
		recalculate();
		if (scheme == null)
			setScheme(main.getSchemeRegistry().getCurrentScheme("slider"));
	}

	public void setScheme(Scheme scheme)
	{
		this.scheme = (SchemeSlider) scheme;
	}

	@Override
	public void render()
	{
		renderBar();

		if (enabled && !hovered)
			SpriteRender.renderDoubleBorder(getX() + ((width - bWidth) / 2), moveY, bWidth, bHeight, scheme.buttonEnabledOutsideBorder, scheme.buttonEnabledInsideBorder, scheme.buttonEnabledFill);

		if (!enabled)
			SpriteRender.renderDoubleBorder(getX() + ((width - bWidth) / 2), moveY, bWidth, bHeight, scheme.buttonDisabledOutsideBorder, scheme.buttonDisabledInsideBorder, scheme.buttonDisabledFill);

		if (enabled && hovered)
			SpriteRender.renderDoubleBorder(getX() + ((width - bWidth) / 2), moveY, bWidth, bHeight, scheme.buttonHoveredOutsideBorder, scheme.buttonHoveredInsideBorder, scheme.buttonHoveredFill);


	}

	protected void renderBar()
	{
		SpriteRender.renderDoubleBorder(x, y, width, height, scheme.sliderOutsideBorder, scheme.sliderInsideBorder, scheme.sliderFill);
	}

//	protected void sliderChange()
//	{
//		privateX = getX() - getMouseHandler().getMouseX(); //mouse pos relativly to component
//		
//		changeEvent.forEach((ce) -> ce.change());
//	}
	
	@Override
	public void tick()
	{
		tickComponents();
		
		if (!isLMBHolded() && flag0)
		{
			flag0 = false;
			hovered = false;
		}
		
		if (isLMBHolded())
		{
			if (!flag0)
			{
				flag0 = true;
				hovered = isCursorInComponent(getX(), getY(), getWidth(), getHeight()) || isCursorInComponent(getX() + ((width - bWidth) / 2), moveY, bWidth, bHeight);
			}
		}

		if (!hovered)
			return;
		
		if (!setted)
		{
			if (isLMBHolded())
				privateY = getY() - getMouseY();
		} else
		{
			setted = false;
		}

		double max = getMaxValue();

		double valueBuffer = -((privateY * max) / (double) getHeight()); //Some weird shit
		
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
		
		moveY = (int) (getY() - bHeight / 2D - privateY); //Center to the slider

		if (privateY + (bHeight / 4) > 0)
			moveY = getY() - (bHeight / 4);

		if (privateY - (bHeight / 4) < (-getHeight()))
			moveY = getY() + getHeight() - (bHeight - (bHeight / 4));

		if (this.oldValue != value)
		{
			this.oldValue = value;
			changeEvent.forEach(ChangeEvent::change);
		}
	}
	
	public void recalculate()
	{
		if (getMaxValue() == 0)
		{
			privateY = -getHeight();
			return;
		}
		
		privateY = -((int) ((value * getHeight()) / getMaxValue()));
		
		float max = (float) getMaxValue();

		double valueBuffer = -((privateY * max) / getHeight()); //Some weird overlays#*t
		
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
		
		moveY = (int) (getX() - bHeight / 2D - privateY); //Center to the slider

		if (privateY + (bHeight / 4) > 0)
			moveY = getX() - (bHeight / 4);

		if (privateY - (bHeight / 4) < (-getHeight()))
			moveY = getY() + getHeight() - (bHeight - (bHeight / 4));
	}

	@Event
	public void scroll(ScrollEvent e)
	{
		if (isEnabled() && isCursorInComponent() || isCursorInComponent(getX() + ((width - bWidth) / 2), moveY, bWidth, bHeight))
			setValue(getValue() - e.getyOffset());
	}
	
	/*
	 * Operators
	 */
	
	/*
	 * Setters
	 */
	
	public void setValue(double value)
	{
		this.value = value;
		setted = true;
		recalculate();
	}

	public void setMaxValue(double maxValue)
	{
		this.maxValue = maxValue;
		
		if (value > maxValue)
			setValue(maxValue);
	}

	public void setMinValue(double minValue)
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
		super.setSize(width, height);
		recalculate();
	}

	public void setButtonSize(int width, int height)
	{
		this.bWidth = width;
		this.bHeight = height;
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
	
	public double getValue()
	{
		return autoInt ? (int) value : value;
	}
	
	public int getIValue()
	{
		return (int) value;
	}
	
	public double getMaxValue()
	{
		return maxValue;
	}
	
	public double getMinValue()
	{
		return minValue;
	}

	public boolean isHovered()
	{
		return hovered;
	}

	public boolean isEnabled()
	{
		return enabled;
	}
}
