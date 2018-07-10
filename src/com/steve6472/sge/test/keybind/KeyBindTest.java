/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 7. 7. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test.keybind;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.main.KeyList;
import com.steve6472.sge.main.MainApplication;

public class KeyBindTest extends MainApplication implements KeyList
{

	public KeyBindTest()
	{
	}

	@Override
	public void init()
	{
		KeyBindGui kbg = new KeyBindGui(this);
		kbg.addKeyEntry(new KeyEntry(W, "Forward"));
	}

	@Override
	public void tick()
	{
		tickGui();
	}

	@Override
	public void render(Screen screen)
	{
		renderGui();
	}

	@Override
	public void setWindowHints()
	{
	}

	@Override
	public int getWidth()
	{
		return 16 * 70;
	}

	@Override
	public int getHeight()
	{
		return 9 * 70;
	}

	@Override
	public void exit()
	{
	}

	@Override
	public String getTitle()
	{
		return "Key bind";
	}
	
	@Override
	protected boolean disableGlDepthTest()
	{
		return true;
	}

	public static void main(String[] args)
	{
		new KeyBindTest();
	}

}
