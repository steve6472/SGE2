/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 9. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.Util;

public class ToggleButton extends Button
{
	private static final long serialVersionUID = -6577484150617733324L;
	private boolean toggled = false;

	public ToggleButton(String text)
	{
		super(text);
	}

	public ToggleButton()
	{
	}

	@Override
	protected void renderText(Screen screen)
	{
		if (toggled)
			Screen.fillRect(getX(), getY(), getWidth(), getHeight(), Util.SELECTED_OVERLAY);
		super.renderText(screen);
	}
	
	@Override
	public void init(MainApplication game)
	{
		super.init(game);
		
		addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				toggled = !toggled;
			}
		});
	}
	
	public boolean isToggled()
	{
		return toggled;
	}
	
	public void setToggled(boolean toggled)
	{
		this.toggled = toggled;
	}

}
