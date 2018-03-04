package com.steve6472.sge.gui.components;

import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.RenderHelper;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.MainApplication;

public class CheckBox extends Component
{
	private static final long serialVersionUID = 7880397321121670923L;
	private boolean isChecked = false, isEnabled = true, isHovered = false;
	private int type = 0;
//	private short idleMouseTime = 0;

	private static final Sprite[] TYPES =
	{ new Sprite("components/checkBox/checkTypes/type_0.png"), new Sprite("components/checkBox/checkTypes/type_1.png") };

	protected List<ChangeEvent> changeEvent = new ArrayList<ChangeEvent>();
	
	@Override
	public void init(MainApplication game)
	{
		super.setSize(40, 40);
	}

	@Override
	public void tick()
	{
		isHovered = isCursorInComponent(x, y, 40, 40);
		
		if (isHovered && getMouseHandler().isMouseHolded() && !getMouseHandler().isMouseTriggered() && isEnabled())
		{
			toggle();
			getMouseHandler().setTrigger(true);
		}
	}

	@Override
	public void render(Screen screen)
	{
		if (isEnabled)
		{
			if (isHovered)
			{
				RenderHelper.renderDoubleBorderComponent(screen, this, 0xff000000, 0xffbdc6ff, 0xff7d87be);
			} else
			{
				RenderHelper.renderDoubleBorderComponent(screen, this, 0xff000000, 0xffa8a8a8, 0xff6f6f6f);
			}
		} else
		{
			RenderHelper.renderDoubleBorderComponent(screen, this, 0xff000000, 0xff2b2b2b, 0xff2b2b2b);
		}

		if (isChecked)
		{
			screen.drawSprite(x, y, TYPES[getType()]);
		}
	}

	/*
	 * Operators
	 */

	public void enable()
	{
		isEnabled = true;
	}

	public void disable()
	{
		isEnabled = false;
	}

	public void check()
	{
		isChecked = true;
	}

	public void uncheck()
	{
		isChecked = false;
	}

	public void toggle()
	{
		isChecked = !isChecked;
		changeEvent.forEach((e) -> e.change());
	}
	
	public void addChangeEvent(ChangeEvent event)
	{
		this.changeEvent.add(event);
	}
	
	/*
	 * Setters
	 */

	public void setChecked(boolean b)
	{
		isChecked = b;
	}

	public void setEnabled(boolean b)
	{
		this.isEnabled = b;
	}

	public void setType(int b)
	{
		this.type = b;
	}
	
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override @Deprecated
	public void setSize(int width, int height)
	{
	}
	
	/*
	 * Getters
	 */

	public int getType()
	{
		return type;
	}

	public boolean isEnabled()
	{
		return isEnabled;
	}

	public boolean isChecked()
	{
		return isChecked;
	}
}
