/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 7. 7. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test.keybind;

import org.lwjgl.glfw.GLFW;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Gui;
import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.SGArray;
import com.steve6472.sge.main.events.Event;
import com.steve6472.sge.main.events.KeyEvent;
import com.steve6472.sge.main.events.MouseEvent;

public class KeyBindGui extends Gui
{
	private static final long serialVersionUID = 6338357277999055291L;
	SGArray<KeyEntry> keys;
	int scroll;

	public KeyBindGui(MainApplication mainApp)
	{
		super(mainApp);
		keys = new SGArray<KeyEntry>();
	}
	
	public void addKeyEntry(KeyEntry entry)
	{
		keys.add(entry);
	}

	@Override
	public void createGui()
	{
	}

	@Override
	public void guiTick()
	{
	}

	@Override
	public void render(Screen screen)
	{
	}
	
	@Event
	public void mouseClick(MouseEvent event)
	{
		System.out.println(event.getX() + " " + event.getY());
	}
	
	@Event
	public void keyEvent(KeyEvent event)
	{
		if (event.getAction() == GLFW.GLFW_PRESS)
			System.out.println(event.getKey());
	}

}
