package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.SpriteRender;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.MainApp;
import com.steve6472.sge.main.events.Event;
import com.steve6472.sge.main.events.ScrollEvent;
import com.steve6472.sge.test.Fex;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItemList extends Component
{
	protected List<ListItem> items;
	public ScrollBar scrollBar;
	boolean enabled;
	boolean multiselect;

	public ItemList(int visibleItems)
	{
		items = new ArrayList<>();
		scrollBar = new ScrollBar();
		scrollBar.visi = visibleItems;
		enabled = true;
		multiselect = true;
	}

	@Override
	public void init(MainApp main)
	{
		addComponent(scrollBar);
	}

	public void setScroll(int scroll)
	{
		scrollBar.scrl = scroll;
	}

	public ListItem addItem(String text)
	{
		ListItem item = new ListItem(getMain(), text);
		items.add(item);
		scrollBar.used = items.size();
		return item;
	}

	public void removeItem(String text)
	{
		items.removeIf(c -> c.text.equals(text));
		scrollBar.used = items.size();
	}

	public List<ListItem> getItems()
	{
		return items;
	}

	public List<ListItem> getSelectedItems()
	{
		List<ListItem> list = new ArrayList<>();
		items.forEach(c -> {
			if (c.isSelected())
				list.add(c);
		});
		return list;
	}

	public List<Integer> getSelectedItemsIndices()
	{
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < items.size(); i++)
		{
			if (items.get(i).isSelected())
				list.add(i);
		}
		return list;
	}

	public void unselectAll()
	{
		items.forEach(c -> c.setSelected(false));
	}

	public void select(int index)
	{
		items.get(index).setSelected(true);
	}

	public void clear()
	{
		items.clear();
	}

	@Override
	public void tick()
	{
		for (int i = scrollBar.scrl; i < scrollBar.visi + scrollBar.scrl; i++)
		{
			if (i >= items.size())
				break;
			items.get(i).tick(this, i - scrollBar.scrl);
		}
	}

	@Override
	public void render()
	{
		SpriteRender.renderSingleBorderComponent(this, 0, 0, 0, 1, Fex.H80, Fex.H80, Fex.H80, Fex.Hff);

		for (int i = scrollBar.scrl; i < scrollBar.visi + scrollBar.scrl; i++)
		{
			if (i >= items.size())
				break;
			items.get(i).render(this, i - scrollBar.scrl);
		}
	}

	@Override
	public void setVisible(boolean b)
	{
		super.setVisible(b);
		setChildVisibility(b);
	}

	public void setMultiselect(boolean multiselect)
	{
		this.multiselect = multiselect;
	}

	public boolean isMultiselect()
	{
		return multiselect;
	}

	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		scrollBar.setSize(20, height + 18);
		scrollBar.setLocation(x + width - 20, y);
	}

	@Override
	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
		scrollBar.setLocation(x + width - 20, y);
	}

	@Event
	public void scroll(ScrollEvent event)
	{
		if (isCursorInComponent(getX(), getY(), getWidth() - 20, getHeight()))
			scrollBar.scroll((int) event.getyOffset());
	}

	public void setVisibleItems(int visibleItems)
	{
		scrollBar.visi = visibleItems;
	}

	/* Events */

	private List<Consumer<ItemList>> events = new ArrayList<>();

	public void addChangeEvent(Consumer<ItemList> c)
	{
		events.add(c);
	}

	void runChangeEvents()
	{
		events.forEach(c -> c.accept(this));
	}
}