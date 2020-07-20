package steve6472.sge.gui.components.dialog;

import steve6472.sge.gfx.font.Font;
import steve6472.sge.main.MainApp;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 17.03.2019
 * Project: SGE2
 *
 ***********************/
public class MessageDialog extends AdvancedMoveableDialog
{
	private String title;
	private long closeDelay, opened;
	private String[] message;
	private boolean custom;

	/**
	 *
	 * @param message Message to be shown
	 * @param title The title of the Dialog Window
	 * @param closeDelay Close Delay in Milliseconds
	 */
	public MessageDialog(String message, String title, long closeDelay)
	{
		if (message == null || message.isEmpty())
		{
			close();
			return;
		}

		this.message = message.split("\n");
		this.title = title;
		for (String s : this.message)
		{
			width = Math.max(Font.getTextWidth(s, 2) + 32, width);
		}
		height = this.message.length * 16 + 40;
		opened = System.currentTimeMillis();
		this.closeDelay = closeDelay;
	}

	public MessageDialog(String[] message, String title, long closeDelay)
	{
		if (message == null)
		{
			close();
			return;
		}

		custom = true;
		this.message = message;
		this.title = title;
		for (String s : this.message)
		{
			width = Math.max(Font.getTextWidth(s, 2) + 32, width);
			if (!(s.startsWith("[") && s.endsWith("]")))
				height += 16;
		}
		height += 40;
		opened = System.currentTimeMillis();
		this.closeDelay = closeDelay;
	}

	@Override
	public void init(MainApp main)
	{
		initCloseButton();
	}

	@Override
	public void tick()
	{
		if (getMouseHandler().getButton() != -1 && !isCursorInComponent() && closeDelay != -1 && (opened + closeDelay) <= System.currentTimeMillis() && !staticFlag && !dialogContainer.isCursorInAnyDialog)
			close();
	}

	@Override
	public void render()
	{
		renderTitle(title);

		if (message != null)
		{
			if (custom)
			{
				Font.renderCustom(x + 10, y + 28, 2, (Object[]) message);
			} else
			{
				int i = 0;
				for (String s : message)
				{
					if (!s.isEmpty())
					{
						int fontWidth = Font.getTextWidth(s, 2) / 2;
						//					int fontHeight = ((16 * 2)) / 2;

						Font.render(s, x + width / 2 - fontWidth, y + 28 + i * 16, 2);
					}
					i++;
				}
			}
		}
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

	@Override
	protected boolean keepInWindow()
	{
		return true;
	}
}
