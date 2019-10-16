/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.SpriteRender;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.Gui;
import com.steve6472.sge.gui.components.schemes.SchemeBackground;
import com.steve6472.sge.main.MainApp;

public class Background
{
	public static void renderFrame(MainApp app)
	{
		SchemeBackground scheme = ((SchemeBackground) app.getSchemeRegistry().getCurrentScheme("background"));
		SpriteRender.renderDoubleBorder(0, 0, app.getWidth(), app.getHeight(), scheme.outsideBorder, scheme.insideBorder, scheme.fill);
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
	public void init(MainApp game)
	{
	}

	@Override
	public void render()
	{
		Background.renderFrame(getMain());
	}

	@Override
	public void tick()
	{
	}
	
}