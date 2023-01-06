/**********************
* Created by steve6472
* On date: 9. 7. 2018
* Project: SGE2
*
***********************/

package steve6472.sge.main.events;

public class KeyEvent extends AbstractEvent
{
	private final int key, scancode, action, mods;

	public KeyEvent(int key, int scancode, int action, int mods)
	{
		this.key = key;
		this.scancode = scancode;
		this.action = action;
		this.mods = mods;
	}

	public int getAction()
	{
		return action;
	}

	public int getKey()
	{
		return key;
	}

	public int getMods()
	{
		return mods;
	}

	public int getScancode()
	{
		return scancode;
	}

}
