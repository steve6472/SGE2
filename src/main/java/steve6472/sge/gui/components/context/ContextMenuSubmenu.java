package steve6472.sge.gui.components.context;

import steve6472.sge.gfx.font.Font;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 16.03.2019
 * Project: SGE2
 *
 ***********************/
public class ContextMenuSubmenu extends ContextMenuItem
{
	private ContextMenu submenu;

	private boolean isChildActive = false;
	private boolean isActive = false;

	private byte out;
	private byte timeOut = 15;

	@Override
	public void tick(int itemY)
	{
		if (submenu != null)
		{
			isChildActive = false;

			for (ContextMenuItem i : submenu.menuItems)
			{
				if (i instanceof ContextMenuSubmenu)
				{
					if (((ContextMenuSubmenu) i).isActive())
						isChildActive = true;
				}
			}

			if (isCursorInItem(itemY) || submenu.isCursorInComponent() || isChildActive())
			{
				out = timeOut;
			} else
			{
				if (out > 0)
					out--;
			}

			if (isCursorInItem(itemY))
			{
				submenu.show(contextMenu.getX() + contextMenu.getWidth(), contextMenu.getY() + itemY,
					contextMenu.getX() + contextMenu.getWidth(), contextMenu.getY() + itemY);

				isActive = true;
			}
			else if (!submenu.isCursorInComponent() && out == 0)
			{
				submenu.hide();

				isActive = false;
			}

			submenu.fullTick();

			contextMenu.spoofCursor = contextMenu.spoofCursor || submenu.isCursorInComponent();
			contextMenu.canClose = !(isActive || isChildActive);
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

	public byte getTimeOut()
	{
		return timeOut;
	}

	public void setTimeOut(byte timeOut)
	{
		this.timeOut = timeOut;
	}

	public byte getOut()
	{
		return out;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public boolean isChildActive()
	{
		return isChildActive;
	}
}
