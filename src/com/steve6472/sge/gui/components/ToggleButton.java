/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 9. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.Render;
import com.steve6472.sge.gfx.font.CustomChar;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ToggleButton extends Button
{
	private static final long serialVersionUID = -6577484150617733324L;
	private boolean toggled;

	public ToggleButton(String text)
	{
		super(text);
		toggled = false;
		addClickEvent(c -> setToggled(!toggled));
	}

	public ToggleButton(CustomChar text)
	{
		super(text);
		toggled = false;
		addClickEvent(c -> setToggled(!toggled));
	}

	public ToggleButton()
	{
		toggled = false;
		addClickEvent(c -> setToggled(!toggled));
	}

	@Override
	protected void renderText()
	{
		if (toggled)
			Render.fillRect(getX(), getY(), getWidth(), getHeight(), scheme.hoveredFill.x, scheme.hoveredFill.y, scheme.hoveredFill.z, 0.5f);
		super.renderText();
	}
	
	public boolean isToggled()
	{
		return toggled;
	}
	
	public void setToggled(boolean toggled)
	{
		this.toggled = toggled;
		runChangeEvents();
	}

	private List<Consumer<ToggleButton>> changeEvent = new ArrayList<>();

	public void addChangeEvent(Consumer<ToggleButton> event)
	{
		this.changeEvent.add(event);
	}

	protected void runChangeEvents()
	{
		changeEvent.forEach(c -> c.accept(this));
	}

}
