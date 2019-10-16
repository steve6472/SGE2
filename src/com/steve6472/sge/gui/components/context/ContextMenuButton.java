package com.steve6472.sge.gui.components.context;

import com.steve6472.sge.main.KeyList;

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
public class ContextMenuButton extends ContextMenuItem
{
	@Override
	public void tick(int itemY)
	{
		onMouseClicked(itemY, KeyList.LMB, c -> doClick());
	}

	@Override
	public void render(int itemY)
	{
		if (isCursorInItem(itemY))
			renderHighlight(itemY);
		renderName(itemY);

		renderImage(itemY);
	}

	@Override
	public int getHeight()
	{
		return 21;
	}

	/* Events */

	private List<Consumer<ContextMenuButton>> clickEvents = new ArrayList<>();
	private List<IfClickEvent> ifClickEvents = new ArrayList<>();
	private List<Consumer<ContextMenuButton>> holdEvents = new ArrayList<>();

	/* Click Event */

	public void addClickEvent(Consumer<ContextMenuButton> c)
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
	public void addIfClickEvent(Function<ContextMenuButton, Boolean> f, Consumer<ContextMenuButton> c)
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

	public void addHoldEvent(Consumer<ContextMenuButton> c)
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
		Function<ContextMenuButton, Boolean> f;
		Consumer<ContextMenuButton> c;

		private IfClickEvent(Function<ContextMenuButton, Boolean> f, Consumer<ContextMenuButton> c)
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
