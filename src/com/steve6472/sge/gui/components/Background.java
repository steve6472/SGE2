/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.Gui;
import com.steve6472.sge.main.MainApplication;

public class Background
{
	
	public static void renderFrame(MainApplication app)
	{
		Screen.drawRect(0, 0, app.getCurrentWidth(), app.getCurrentHeight(), 2, 0xff333333);
		Screen.drawRect(2, 2, app.getCurrentWidth() - 4, app.getCurrentHeight() - 4, 2, 0xff7f7f7f);
		Screen.fillRect(4, 4, app.getCurrentWidth() - 8, app.getCurrentHeight() - 8, 0xfff2f2f2);
	}
	
	public static void createComponent(Gui gui)
	{
		gui.addComponent(new BackComp());
	}

}

class BackComp extends Component
{
	private static final long serialVersionUID = 1786536281333133003L;

	@Override
	public void init(MainApplication game)
	{
	}

	@Override
	public void render(Screen screen)
	{
		Background.renderFrame(getMainApp());
	}

	@Override
	public void tick()
	{
	}
	
}