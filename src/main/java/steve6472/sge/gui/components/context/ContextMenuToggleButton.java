package steve6472.sge.gui.components.context;

import steve6472.sge.main.KeyList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 16.03.2019
 * Project: SGE2
 *
 ***********************/
public class ContextMenuToggleButton extends ContextMenuItem
{
	private boolean toggled = false;
	private boolean isRadio = false;

	public ContextMenuToggleButton()
	{
		addClickEvent(c -> toggled = !toggled);
		setImage(getChar());
	}

	@Override
	public void tick(int itemY)
	{
		onMouseClicked(itemY, KeyList.LMB, c ->
		{
			doClick();
			setImage(getChar());
		});
	}

	private char getChar()
	{
		if (isRadio)
		{
			if (toggled)
				return '\u0018';
			else
				return '\u0017';
		} else
		{
			if (toggled)
				return '\u0016';
			else
				return '\u0015';
		}
	}

	@Override
	public void render(int itemY)
	{
		if (isCursorInItem(itemY))
			renderHighlight(itemY);

//		if (toggled)
//			Screen.fillRect(contextMenu.getX() + 2, contextMenu.getY() + itemY + 2, contextMenu.getWidth() - 4, 20,
//					ColorUtil.getColor(
//							ColorUtil.getRed(0xff7d87be),
//							ColorUtil.getGreen(0xff7d87be),
//							ColorUtil.getBlue(0xff7d87be),
//							127));

		renderName(itemY);

		renderImage(itemY);
	}

	@Override
	public int getHeight()
	{
		return 21;
	}

	public boolean isToggled()
	{
		return toggled;
	}

	public void setToggled(boolean toggled)
	{
		this.toggled = toggled;
		setImage(getChar());
	}

	/**
	 * Should be used only by RadioGroup
	 */
	public void allowRadio()
	{
		isRadio = true;
		setImage(getChar());
	}

	/* Events */

	private List<Consumer<ContextMenuToggleButton>> clickEvents = new ArrayList<>();
	private List<IfClickEvent> ifClickEvents = new ArrayList<>();
	private List<Consumer<ContextMenuToggleButton>> holdEvents = new ArrayList<>();

	/* Click Event */

	public void addClickEvent(Consumer<ContextMenuToggleButton> c)
	{
		clickEvents.add(c);
	}

	private void runClickEvents()
	{
		clickEvents.forEach(c -> c.accept(this));
	}

	/* If Click Event */

	/**
	 * Runs only if condition in <b> f </b> is met
	 * @param f Condition
	 * @param c Event
	 */
	public void addIfClickEvent(Function<ContextMenuToggleButton, Boolean> f, Consumer<ContextMenuToggleButton> c)
	{
		ifClickEvents.add(new IfClickEvent(f, c));
	}

	private void runIfClickEvents()
	{
		ifClickEvents.forEach(c -> {
			if (c.f.apply(this))
				c.c.accept(this);
		});
	}

	/* Hold Event */

	public void addHoldEvent(Consumer<ContextMenuToggleButton> c)
	{
		holdEvents.add(c);
	}

	private void runHoldEvents()
	{
		holdEvents.forEach(c -> c.accept(this));
	}

	/* Class required for events */

	private class IfClickEvent
	{
		Function<ContextMenuToggleButton, Boolean> f;
		Consumer<ContextMenuToggleButton> c;

		private IfClickEvent(Function<ContextMenuToggleButton, Boolean> f, Consumer<ContextMenuToggleButton> c)
		{
			this.f = f;
			this.c = c;
		}
	}

	public void doClick()
	{
		if (isEnabled())
		{
			forceDoClick();
		}
	}

	/**
	 * Ignores {@code isEnabled()} and runs all ButtonEvents
	 */
	public void forceDoClick()
	{
		runClickEvents();
		runIfClickEvents();
	}
}
