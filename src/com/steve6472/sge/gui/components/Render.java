/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 29. 6. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.MainApplication;

public class Render extends Component
{
	private static final long serialVersionUID = 3941323655020960510L;
	
	@FunctionalInterface
	public interface IRender
	{
		public void render();
	}
	
	IRender render;

	public Render(IRender render)
	{
		this.render = render;
	}

	@Override
	public void init(MainApplication game)
	{
	}

	@Override
	public void render(Screen screen)
	{
		render.render();
	}

	@Override
	public void tick()
	{
	}

}
