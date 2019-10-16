package com.steve6472.sge.gui.components.context;

import com.steve6472.sge.gfx.font.Font;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 16.03.2019
 * Project: SGE2
 *
 ***********************/
public class ContextMenuSubmenu extends ContextMenuItem
{
	private ContextMenu submenu;

	@Override
	public void tick(int itemY)
	{
		if (submenu != null)
		{
			if (isCursorInItem(itemY))
				submenu.show(contextMenu.getX() + contextMenu.getWidth(), contextMenu.getY() + itemY, contextMenu.getX() + contextMenu.getWidth(), contextMenu.getY() + itemY);
			else if (!submenu.isCursorInComponent())
				submenu.hide();

			submenu.fullTick();

			contextMenu.spoofCursor = contextMenu.spoofCursor || submenu.isCursorInComponent();
			contextMenu.canClose = !submenu.isCursorInComponent() && !contextMenu.isCursorInComponent();
		}
	}

	@Override
	public void render(int itemY)
	{
		if (isCursorInItem(itemY))
			renderHighlight(itemY);
		renderName(itemY);

		renderImage(itemY);

		Font.render(">", contextMenu.getX() + contextMenu.getWidth() - 16, contextMenu.getY() + 8 + itemY);

		if (submenu != null)
			submenu.fullRender();
	}

	public void setSubmenu(ContextMenu submenu)
	{
		this.submenu = submenu;
		submenu.preInit(contextMenu.getMain());
		submenu.init(contextMenu.getMain());
		submenu.getMain().getEventHandler().register(submenu);
		submenu.darkenGui = false;
		submenu.canClose = false;
	}

	@Override
	public int getHeight()
	{
		return 21;
	}
}
