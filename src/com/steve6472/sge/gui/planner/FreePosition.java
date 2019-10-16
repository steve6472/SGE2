package com.steve6472.sge.gui.planner;

import com.steve6472.sge.gfx.SpriteRender;
import com.steve6472.sge.gfx.font.CustomChar;
import com.steve6472.sge.gfx.font.Font;
import com.steve6472.sge.gui.components.dialog.Dialog;
import com.steve6472.sge.main.KeyList;
import com.steve6472.sge.main.MainApp;
import com.steve6472.sge.main.Util;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 05.06.2019
 * Project: SJP
 *
 ***********************/
public class FreePosition extends Dialog
{
	protected Planner planner;

	private Planner.Box box;

	private int px, py;
	private boolean moving;

	private int dx, dy, ow, oh;
	private boolean resize;

	public FreePosition(Planner.Box box, Planner planner, BoxOptionsDialog bod)
	{
		this.box = box;
		this.planner = planner;
	}

	@Override
	public void init(MainApp main)
	{
	}

	@Override
	public void tick()
	{
		if (getMain().isKeyPressed(KeyList.ENTER) || getMain().isKeyPressed(KeyList.KP_ENTER))
		{
			close();
			planner.inDialog = false;
			return;
		}

		boolean b = Util.isCursorInRectangle(getMain(), moveX(), moveY(), 20, 20);

		if (getMain().isLMBHolded() && b && !resize && !moving)
		{
			resize = true;
			dx = getMain().getMouseX();
			dy = getMain().getMouseY();
			ow = box.w;
			oh = box.h;
		}

		if (!getMain().isLMBHolded())
		{
			resize = false;
			moving = false;
		}

		if (resize)
		{
			box.w = getMain().getMouseX() - dx + ow;
			box.h = getMain().getMouseY() - dy + oh;
			if (box.w < 0) box.w = 1;
			if (box.h < 0) box.h = 1;
			if (box.x + box.w > getMain().getWidth()) box.w = getMain().getWidth() - box.x;
			if (box.y + box.h > getMain().getHeight()) box.h = getMain().getHeight() - box.y;
		}

		if (getMain().isLMBHolded() && !resize)
		{
			moving = true;
			if (px == -1 && py == -1)
			{
				px = box.x - getMain().getMouseX();
				py = box.y - getMain().getMouseY();
			} else
			{
				box.x = getMain().getMouseX() + px;
				box.y = getMain().getMouseY() + py;
			}
		} else
		{
			px = -1;
			py = -1;
		}

		if (box.x < 0) box.x = 0;
		if (box.y < 0) box.y = 0;
		if (box.x + box.w > getMain().getWidth()) box.x = getMain().getWidth() - box.w;
		if (box.y + box.h > getMain().getHeight()) box.y = getMain().getHeight() - box.h;
	}

	@Override
	public void render()
	{
		SpriteRender.fillRect(0, 0, getMain().getWidth(), getMain().getHeight(), 0.01f, 0.01f, 0.01f, 0.25f);
		SpriteRender.fillRect(box.x, box.y, box.w, box.h, box.r, box.g, box.b, box.a);
		planner.cr.render(box);
		SpriteRender.fillRect(moveX(), moveY(), 20, 20, 0.6f, 0.6f, 0.6f, 0.2f);

		Font.renderCustom(moveX() + 1, moveY() + 1, 2, CustomChar.ARROW_BOTTOM_RIGHT);
		Font.renderCustom(box.x + 3, box.y + 3, 1, box.name);
		Font.renderCustom(box.x + 3, box.y + 13, 1, "[#FF6B68]", box.x, "[#ffffff]", "/", "[#6Bff68]", box.y);
		Font.renderCustom(box.x + 3, box.y + 23, 1, "[#686BFF]", box.w, "[#ffffff]", "/", "[#FF6aFF]", box.h);
	}

	private int moveX()
	{
		return Math.min(box.x + box.w + 10, getMain().getWidth() - 20);
	}

	private int moveY()
	{
		return Math.min(box.y + box.h + 10, getMain().getHeight() - 20);
	}

	@Override
	public boolean freezeGui()
	{
		return true;
	}

	@Override
	public boolean disableEvents()
	{
		return true;
	}
}
