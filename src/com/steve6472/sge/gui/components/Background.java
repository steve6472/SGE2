/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.main.MainApplication;

public class Background
{
	
	public static void renderFrame(Screen screen, MainApplication app)
	{
		screen.drawRect(0, 0, app.getCurrentWidth(), app.getCurrentHeight(), 2, 0xff333333);
		screen.drawRect(2, 2, app.getCurrentWidth() - 4, app.getCurrentHeight() - 4, 2, 0xff7f7f7f);
		screen.fillRect(4, 4, app.getCurrentWidth() - 8, app.getCurrentHeight() - 8, 0xfff2f2f2);
	}

}
