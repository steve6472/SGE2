package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.SpriteRender;
import com.steve6472.sge.gfx.font.Font;
import com.steve6472.sge.gui.components.schemes.IScheme;
import com.steve6472.sge.gui.components.schemes.SchemeListItemButton;
import com.steve6472.sge.main.KeyList;
import com.steve6472.sge.main.MainApp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ListItem implements IScheme<SchemeListItemButton>
{
	private SchemeListItemButton scheme;
	String text;
	private boolean hovered;
	private boolean selected;
	private boolean flag;
	private int fontSize;

	public ListItem()
	{
		scheme = MainApp.getSchemeRegistry().copyDefaultScheme(SchemeListItemButton.class);
	}

	public ListItem(String text)
	{
		this.text = text;
		scheme = MainApp.getSchemeRegistry().copyDefaultScheme(SchemeListItemButton.class);
		fontSize = 1;
	}

	public void tick(ItemList itemList, int posInList)
	{
		int mx = itemList.getMouseHandler().getMouseX();
		int my = itemList.getMouseHandler().getMouseY();

		int ih = itemList.getHeight() / itemList.scrollBar.visi;
		int ix = itemList.getX();
		int iy = itemList.getY() + posInList * ih;
		int iw = itemList.getWidth() - 20;

		hovered = (mx >= ix && mx <= iw + ix) && (my >= iy && my < ih + iy);

		if (hovered && itemList.getMouseHandler().isMouseHolded())
			runHoldEvents();

		if (hovered)
		{
			if (itemList.getMouseHandler().getButton() == KeyList.LMB)
			{
				if (!flag)
				{
					if (itemList.multiselect)
					{
						if (selected)
						{
							if (!itemList.getKeyHandler().isKeyPressed(KeyList.L_SHIFT))
							{
								itemList.items.forEach(c -> c.selected = false);
							}
							selected = false;
							itemList.runChangeEvents();
							runClickEvents();
							runIfClickEvents();
						} else
						{
							if (!itemList.getKeyHandler().isKeyPressed(KeyList.L_SHIFT))
							{
								itemList.items.forEach(c -> c.selected = false);
							}
							selected = true;
							itemList.runChangeEvents();
							runClickEvents();
							runIfClickEvents();
						}
					} else
					{
						itemList.items.forEach(c -> c.selected = false);
						selected = !selected;
						itemList.runChangeEvents();
						runClickEvents();
						runIfClickEvents();
					}
					flag = true;
				}
			} else if (itemList.getMouseHandler().getButton() == KeyList.RMB && itemList.multiselect)
			{
				if (!flag)
				{
					flag = true;
					selected = !selected;
					itemList.runChangeEvents();
					runClickEvents();
					runIfClickEvents();
				}
			} else if (itemList.getMouseHandler().getButton() == KeyList.RMB && !itemList.multiselect)
			{
				if (!flag && selected)
				{
					flag = true;
					selected = false;
					itemList.runChangeEvents();
					runClickEvents();
					runIfClickEvents();
				}
			} else
			{
				flag = false;
			}
		} else
		{
			flag = false;
		}
	}

	public void render(ItemList itemList, int posInList)
	{
		int ih = itemList.getHeight() / itemList.scrollBar.visi;
		int ix = itemList.getX();
		int iy = itemList.getY() + posInList * ih;
		int iw = itemList.getWidth() - 20;

		renderButton(itemList, ix, iy, iw, ih);

		if (selected)
			renderFrame(ix, iy, iw, ih);

		if (text != null)
			renderText(itemList, ix, iy, iw, ih);
	}

	public void renderButton(ItemList itemList, int ix, int iy, int iw, int ih)
	{
		if (itemList.enabled && !hovered)
			SpriteRender.renderDoubleBorder(ix, iy, iw, ih, scheme.enabledOutsideBorder, scheme.enabledInsideBorder, scheme.enabledFill);

		if (!itemList.enabled)
			SpriteRender.renderDoubleBorder(ix, iy, iw, ih, scheme.disabledOutsideBorder, scheme.disabledInsideBorder, scheme.disabledFill);

		if (itemList.enabled && hovered)
			SpriteRender.renderDoubleBorder(ix, iy, iw, ih, scheme.hoveredOutsideBorder, scheme.hoveredInsideBorder, scheme.hoveredFill);
	}

	public void renderText(ItemList itemList, int ix, int iy, int iw, int ih)
	{
		int fontWidth = Font.getTextWidth(text, fontSize) / 2;
		int fontHeight = ((8 * fontSize)) / 2;

		if (itemList.enabled && !hovered)
			Font.render(text, ix + iw / 2 - fontWidth, iy + ih / 2 - fontHeight, fontSize, getScheme().enabled);

		if (!itemList.enabled)
			Font.render(text, ix + iw / 2 - fontWidth, iy + ih / 2 - fontHeight, fontSize, getScheme().disabled);

		if (itemList.enabled && (hovered || selected))
			Font.render(text, ix + iw / 2 - fontWidth, iy + ih / 2 - fontHeight, fontSize, getScheme().hovered);
	}

	public void renderFrame(int ix, int iy, int iw, int ih)
	{
//		SpriteRender.renderSingleBorder(ix + 2, iy + 2, iw - 4, ih - 4, scheme.selectedOutline.x, scheme.selectedOutline.y, scheme.selectedOutline.z, scheme.selectedOutline.w, 0, 0, 0, 0);

		SpriteRender.fillRect(ix + 2, iy + 2, iw - 4, ih - 4, scheme.selectedOutline);

		/* ComponentRender pretty checkerboard frame */
		/*
		Render.startPoints();
		Render.color(scheme.selectedOutline.x, scheme.selectedOutline.y, scheme.selectedOutline.z, scheme.selectedOutline.w);
		{
			for (int i = 0; i < iw / 4 - 2; i++)
			{

				Render.point(ix + i * 4 + 4, iy + 5);
				Render.point(ix + i * 4 + 5, iy + 5);
				Render.point(ix + i * 4 + 5, iy + 6);
				Render.point(ix + i * 4 + 4, iy + 6);

				Render.point(ix + i * 4 + 4, iy - 5 + ih);
				Render.point(ix + i * 4 + 5, iy - 5 + ih);
				Render.point(ix + i * 4 + 5, iy - 4 + ih);
				Render.point(ix + i * 4 + 4, iy - 4 + ih);
			}
			for (int i = 0; i < ih / 4 - 2; i++)
			{
				Render.point(ix - 6 + iw, i * 4 + iy + 7);
				Render.point(ix - 5 + iw, i * 4 + iy + 7);
				Render.point(ix - 5 + iw, i * 4 + iy + 8);
				Render.point(ix - 6 + iw, i * 4 + iy + 8);
			}
			for (int i = 0; i < ih / 4 - 3; i++)
			{
				Render.point(ix + 5, i * 4 + iy + 9);
				Render.point(ix + 4, i * 4 + iy + 9);
				Render.point(ix + 4, i * 4 + iy + 10);
				Render.point(ix + 5, i * 4 + iy + 10);
			}
		}
		Render.endPoints();*/
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public int getFontSize()
	{
		return fontSize;
	}

	public void setFontSize(int fontSize)
	{
		this.fontSize = fontSize;
	}

	@Override
	public SchemeListItemButton getScheme()
	{
		return scheme;
	}

	@Override
	public void setScheme(SchemeListItemButton scheme)
	{
		this.scheme = scheme;
	}

	/* Events */

	private List<Consumer<ListItem>> clickEvents = new ArrayList<>();
	private List<IfClickEvent> ifClickEvents = new ArrayList<>();
	private List<Consumer<ListItem>> holdEvents = new ArrayList<>();

	/* Click Event */

	public void addClickEvent(Consumer<ListItem> c)
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
	public void addIfClickEvent(Function<ListItem, Boolean> f, Consumer<ListItem> c)
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

	public void addHoldEvent(Consumer<ListItem> c)
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
		Function<ListItem, Boolean> f;
		Consumer<ListItem> c;

		private IfClickEvent(Function<ListItem, Boolean> f, Consumer<ListItem> c)
		{
			this.f = f;
			this.c = c;
		}
	}
}