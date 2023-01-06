package steve6472.sge.gui.components.dialog;

import steve6472.sge.main.KeyList;

/**********************
 * Created by steve6472
 * On date: 17.03.2019
 * Project: SGE2
 *
 ***********************/
public abstract class MoveableDialog extends Dialog
{
	private int px, py;
	private boolean flag = false;
	protected static boolean staticFlag = false;

	public void dragTick()
	{
		if (getMouseHandler().getButton() == KeyList.LMB && canMove() && !staticFlag)
		{
			flag = true;
			staticFlag = true;
		}

		if (getMouseHandler().getButton() != KeyList.LMB)
		{
			flag = false;
			staticFlag = false;
		}

		if (flag)
		{
			if (px == -1 && py == -1)
			{
				px = x - getMain().getMouseX();
				py = y - getMain().getMouseY();
			} else
			{
				x = getMain().getMouseX() + px;
				y = getMain().getMouseY() + py;
			}
		} else
		{
			px = -1;
			py = -1;
		}

		if (keepInWindow())
		{
			if (x < 0) x = 0;
			if (y < 0) y = 0;
			if (x + width > getMain().getWidth()) x = getMain().getWidth() - width;
			if (y + height > getMain().getHeight()) y = getMain().getHeight() - height;
		}

		setLocation(x, y);
	}

	protected abstract boolean canMove();

	protected boolean keepInWindow()
	{
		return true;
	}
}
