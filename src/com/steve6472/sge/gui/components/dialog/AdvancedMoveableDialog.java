package com.steve6472.sge.gui.components.dialog;

import com.steve6472.sge.gfx.SpriteRender;
import com.steve6472.sge.gfx.font.Font;
import com.steve6472.sge.gui.components.Button;
import com.steve6472.sge.test.Fex;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 18.04.2019
 * Project: SJP
 *
 ***********************/
public abstract class AdvancedMoveableDialog extends MoveableDialog
{
	/**
	 * Creates 'X' button in top right corner of the dialog
	 * Should be called only after setting the size
	 */
	protected Button initCloseButton()
	{
		Button close = new Button("\u000c");
		close.setRelativeLocation(width - 20, 0);
		close.setSize(20, 20);
		close.addClickEvent(c -> close());
		addComponent(close);
		return close;
	}

	/**
	 * @param title Title of the dialog window
	 */
	protected void renderTitle(String title)
	{
		SpriteRender.renderSingleBorder(x, y + 18, width, height - 18, 0, 0, 0, 1, Fex.H70, Fex.H70, Fex.H70, Fex.Hff);
		SpriteRender.renderSingleBorder(x, y, width, 20, 0, 0, 0, 1, Fex.H35, Fex.H35, Fex.H35, Fex.Hff);

		if (title != null && !title.isEmpty())
			Font.render(title, x + 4, y + 6);
	}

	@Override
	protected boolean canMove()
	{
		return isCursorInComponent(x, y, width, 19);
	}

	protected boolean isCursorInComponent(int x, int y, int w, int h)
	{
		return (getMouseHandler().getMouseX() >= x && getMouseHandler().getMouseX() < w + x) && (getMouseHandler().getMouseY() >= y && getMouseHandler().getMouseY() <= h + y);
	}
}
