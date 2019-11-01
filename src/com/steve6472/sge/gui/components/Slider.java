package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.SpriteRender;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.schemes.IScheme;
import com.steve6472.sge.gui.components.schemes.SchemeSlider;
import com.steve6472.sge.main.MainApp;
import com.steve6472.sge.main.events.Event;
import com.steve6472.sge.main.events.ScrollEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Slider extends Component implements IScheme<SchemeSlider>
{
	private double value = 0, maxValue = 100, minValue = 0, privateX;
	public boolean autoInt = false;
	protected int bWidth = 32, bHeight = 48;
	public boolean snap = false;

	public SchemeSlider scheme;

	private boolean setted = false;
	private double oldValue = 0;
	private int moveX = 0;
	protected boolean hovered = false;
	protected boolean enabled = true;
	private boolean flag0 = false;

	public Slider()
	{
		setScheme(MainApp.getSchemeRegistry().copyDefaultScheme(SchemeSlider.class));
	}

	@Override
	public void init(MainApp main)
	{
		recalculate();
	}

	@Override
	public void render()
	{
		renderBar();

		int x = snap ? ((int) (getIValue() * (getWidth() / getMaxValue())) + getX() - (bWidth / 2)) : moveX;

		if (enabled && !hovered)
			SpriteRender.renderDoubleBorder(x, getY() + ((height - bHeight) / 2), bWidth, bHeight, scheme.buttonEnabledOutsideBorder, scheme.buttonEnabledInsideBorder, scheme.buttonEnabledFill);

		if (!enabled)
			SpriteRender.renderDoubleBorder(x, getY() + ((height - bHeight) / 2), bWidth, bHeight, scheme.buttonDisabledOutsideBorder, scheme.buttonDisabledInsideBorder, scheme.buttonDisabledFill);

		if (enabled && hovered)
			SpriteRender.renderDoubleBorder(x, getY() + ((height - bHeight) / 2), bWidth, bHeight, scheme.buttonHoveredOutsideBorder, scheme.buttonHoveredInsideBorder, scheme.buttonHoveredFill);
	}

	protected void renderBar()
	{
		SpriteRender.renderDoubleBorder(x, y, width, height, scheme.sliderOutsideBorder, scheme.sliderInsideBorder, scheme.sliderFill);
	}

	@Override
	public void tick()
	{
		tickComponents();

		if (!enabled)
			return;
		
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
				hovered = isCursorInComponent(getX(), getY(), getWidth(), getHeight()) || isCursorInComponent(moveX, getY() + ((height - bHeight) / 2), bWidth, bHeight);
			}
		}

		if (!hovered)
			return;
		
		if (!setted)
		{
			if (isLMBHolded())
				privateX = getX() - getMouseX();
		} else
		{
			setted = false;
		}

		double max = getMaxValue();

		double valueBuffer = -((privateX * max) / (double) getWidth()); //Some weird shit

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

		moveX = (int) (getX() - bWidth / 2D - privateX); //Center to the slider

		if (privateX + (bWidth / 4) > 0)
			moveX = getX() - (bWidth / 4);

		if (privateX - (bWidth / 4) < (-getWidth()))
			moveX = getX() + getWidth() - (bWidth - (bWidth / 4));

		if (this.oldValue != value)
		{
			this.oldValue = value;
			changeEvent.forEach(c -> c.accept(this));
		}
	}
	
	public void recalculate()
	{
		if (getMaxValue() == 0)
		{
			privateX = -width;
			return;
		}
		
		privateX = -((value * getWidth()) / getMaxValue());
		
		float max = (float) getMaxValue();

		double valueBuffer = -((privateX * max) / (double) width); //Some weird overlays#*t
		
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

		moveX = (int) (x - bWidth / 2D - privateX); //Center to the slider

		if (privateX + (bWidth / 4) > 0)
			moveX = x - (bWidth / 4);

		if (privateX - (bWidth / 4) < (-width))
			moveX = x + width - (bWidth - (bWidth / 4));
	}

	@Event
	public void scroll(ScrollEvent e)
	{
		if (isEnabled() && (isCursorInComponent() || isCursorInComponent(moveX, getY() + ((height - bHeight) / 2), bWidth, bHeight)))
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

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	@Override
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
		recalculate();
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
		return (int) Math.floor(value);
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

	@Override
	public SchemeSlider getScheme()
	{
		return scheme;
	}

	@Override
	public void setScheme(SchemeSlider scheme)
	{
		this.scheme = scheme;
	}

	/* Events */

	protected List<Consumer<Slider>> changeEvent = new ArrayList<>();

	public void addChangeEvent(Consumer<Slider> ce)
	{
		changeEvent.add(ce);
	}
}
