/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 29. 6. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gui.components;

import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.MainApp;

public class ComponentRender extends Component
{
	private static final long serialVersionUID = 3941323655020960510L;
	
	@FunctionalInterface
	public interface IRender
	{
		void render();
	}
	
	IRender render;

	public ComponentRender(IRender render)
	{
		this.render = render;
	}

	@Override
	public void init(MainApp game)
	{
	}

	@Override
	public void render()
	{
		render.render();
	}

	@Override
	public void tick()
	{
	}

}
