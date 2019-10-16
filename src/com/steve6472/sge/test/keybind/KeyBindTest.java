/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 7. 7. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test.keybind;

import com.steve6472.sge.main.KeyList;
import com.steve6472.sge.main.MainApp;
import com.steve6472.sge.main.MainFlags;

public class KeyBindTest extends MainApp implements KeyList
{
	public KeyBindTest()
	{
	}

	@Override
	public void init()
	{
		KeyBindGui kbg = new KeyBindGui(this);
		kbg.addKeyEntry(new KeyEntry(W, "Forward", EntryType.KEYBOARD));
		kbg.addKeyEntry(new KeyEntry(S, "Backward", EntryType.KEYBOARD));
		kbg.addKeyEntry(new KeyEntry(A, "Left", EntryType.KEYBOARD));
		kbg.addKeyEntry(new KeyEntry(D, "Right", EntryType.KEYBOARD));
		kbg.addKeyEntry(new KeyEntry(E, "Open Inventory", EntryType.KEYBOARD));
		kbg.addKeyEntry(new KeyEntry(LMB, "Attack", EntryType.MOUSE));
		kbg.addKeyEntry(new KeyEntry(RMB, "Place", EntryType.MOUSE));
	}

	@Override
	public void tick()
	{
		tickGui();
	}

	@Override
	public void render()
	{
		renderGui();
	}

	@Override
	public void setWindowHints()
	{
	}

	@Override
	public int getWindowWidth()
	{
		return 16 * 70;
	}

	@Override
	public int getWindowHeight()
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
	
	public static void main(String[] args)
	{
		new KeyBindTest();
	}

	@Override
	protected int[] getFlags()
	{
		return new int[] {
				MainFlags.ADD_BASIC_ORTHO,
				MainFlags.ENABLE_EXIT_KEY
		};
	}

}
