/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 10. 7. 2018
* Project: SGE2
*
***********************/

package steve6472.sge.gui.components;

import steve6472.sge.gfx.SpriteRender;
import steve6472.sge.gui.Component;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.MouseHandler;
import steve6472.sge.main.events.Event;
import steve6472.sge.main.events.ScrollEvent;
import steve6472.sge.test.Fex;

public class ScrollBar extends Component
{
	public ScrollBar()
	{
	}

	@Override
	public void init(MainApp game)
	{
	}

	int xSliderRelX = 0;
	int xSliderOldX = 0;
	boolean holded = false;
	
	public int used;
	public int visi;
	public int scrl;

	public boolean specialScroll;
	
	@Override
	public void render()
	{
		double used = this.used;
		double fulX = height - 22;
		double hiddenX = used - visi;
		int hei = Math.max((int) ((visi / used) * fulX), 20);

		SpriteRender.manualStart();

		SpriteRender.drawRect(x + width - 20, y, 20, height - 18, Fex.H02, Fex.H02, Fex.H02, 1);
		SpriteRender.fillRect(x + width - 18, y + 2, 16, height - 22, Fex.H15, Fex.H14, Fex.H15, 1);

		if (hiddenX > 0)
			SpriteRender.fillRect(x + width - 18, y + 2 + xSliderRelX, 16, hei, Fex.H8d, Fex.H8c, Fex.H8d, 1);
		else
			SpriteRender.fillRect(x + width - 18, y + 2, 16, (int) fulX, Fex.H8d, Fex.H8c, Fex.H8d, 1);
		if (hiddenX > 0 && isCursorInRectangle(getMouseHandler(), x + width - 18, y + 2 + xSliderRelX, 16, hei))
		{
			SpriteRender.fillRect(x + width - 18, y + 2 + xSliderRelX, 16, hei, Fex.Hb1, Fex.Hb0, Fex.Hb1, 1);
		}

		SpriteRender.manualEnd();
	}

	private static boolean isCursorInRectangle(MouseHandler m, int x, int y, int w, int h)
	{
		return ( m.getMouseX() >= x && m.getMouseX() <= w + x)   // check if X is within range
				&& ( m.getMouseY() >= y && m.getMouseY()<= h + y);
	}

	@Override
	public void tick()
	{
		double usedX = this.used;
		double fulX = height - 22;
		int hei = Math.max((int) ((visi / usedX) * fulX), 20);
		
		if (isCursorInRectangle(getMouseHandler(), x + width - 18, y + 2 + xSliderRelX, 16, hei))
		{
			if (isLMBHolded() && !holded)
			{
				xSliderOldX = xSliderRelX - getMouseY();
				holded = true;
			}
		}
		
		if (isLMBHolded() && holded)
			xSliderRelX = getMouseY() + xSliderOldX;
		
		
		if (xSliderRelX > (fulX - hei))
		{
			xSliderRelX = (int) (fulX - hei);
		}
		
		if (xSliderRelX < 0)
		{
			xSliderRelX = 0;
		}
		
		if (!isLMBHolded())
		{
			holded = false;
			xSliderOldX = x;
		}
		
		double p = (1d - (visi / usedX));
		
		scrl = (int) ((double) xSliderRelX / ((p * fulX) / (usedX - visi)));
	}

	@Event
	public void scroll(ScrollEvent event)
	{
		if (isCursorInComponent() || specialScroll)
			scroll((int) event.getyOffset());
	}

	public void scroll(int j)
	{
		xSliderRelX -= j * 10;
		tick();
	}

}
